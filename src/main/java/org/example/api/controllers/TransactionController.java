package org.example.api.controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.TransactionDto;
import org.example.api.factories.CoursesDtoFactory;
import org.example.api.factories.TransactionDtoFactory;
import org.example.services.TransactionService;
import org.example.storage.entities.CoursesEntity;
import org.example.storage.entities.CurrencyEntity;
import org.example.storage.entities.LimitsEntity;
import org.example.storage.repositories.CategoryRepository;
import org.example.storage.repositories.CoursesRepository;
import org.example.storage.repositories.CurrencyRepository;
import org.example.storage.repositories.LimitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;




@RequiredArgsConstructor
@Transactional
@RestController
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    private final CoursesRepository coursesRepository;

    private final CurrencyRepository currencyRepository;

    private final CategoryRepository categoryRepository;

    private final LimitsRepository limitsRepository;

    private final CoursesDtoFactory coursesDtoFactory;

    private final TransactionDtoFactory transactionDtoFactory;


    @GetMapping("api/transactions")
    public List<Object[]> getListTransactions() {
        return transactionService.getTransactionsExceedingLimit();
    }


    private CoursesEntity requestCourse(
            String symbol,
            String interval,
            long outputSize,
            String apiKey) {

        var restGetRequest = new RestTemplate();

        String result = restGetRequest.getForObject("https://api.twelvedata.com/time_series?symbol=USD/" + symbol + "&interval=" + interval + "&outputsize=" + outputSize + "&apikey=" + apiKey, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode valuesNode = null;
        try {
            JsonNode rootNode = objectMapper.readTree(result);
            valuesNode = rootNode.get("values");
        } catch (Exception e) {
            e.printStackTrace();
        }


        assert valuesNode != null;
        CoursesEntity coursesEntity = coursesRepository.saveAndFlush(
                CoursesEntity.builder()
                        .coefficient(Double.parseDouble(valuesNode.get("close").asText()))
                        .currencyId(currencyRepository.getByCurrencyName(symbol))
                        .build()
        );

        coursesDtoFactory.makeCoursesDto(coursesEntity);

        return coursesEntity;

    }

    @PostMapping("api/transactions")
    public TransactionDto createTransaction(
            @RequestParam("user_id") long user_id,
            @RequestParam("amount_from") long amount_from,
            @RequestParam("amount_to") long amount_to,
            @RequestParam("sum_transaction") double sum_transaction,
            @RequestParam("currency") String currency_shortname,
            @RequestParam("category") String category_name) {

        LocalDate currentDate = LocalDate.now();

        CurrencyEntity currency = currencyRepository.findByCurrencyName(currency_shortname)
                .orElseThrow(() -> new EntityNotFoundException("Currency with name " + currency_shortname + " not found"));

        CoursesEntity coursesEntity = coursesRepository.findByDateAndCurrencyId(currentDate, currency);

        if (coursesEntity == null) {
            coursesEntity = requestCourse(currency_shortname, "1day", 1, "546c3b99b94a48e0afa440dd3e42946a");
        }

        double sumAllLimits = limitsRepository.findAllByUserIdAndCategoryId(user_id, categoryRepository.getByNameCategory(category_name)).stream().mapToDouble(LimitsEntity::getSumLimit).sum();

        double coef = currency_shortname.equals("USD") ?  1 : coursesEntity.getCoefficient();

        boolean limitsExceeded = sumAllLimits * coef < sum_transaction;

        return transactionDtoFactory
                .makeTransactionDto(transactionService
                        .receiveTransaction(
                                user_id,
                                amount_from,
                                amount_to,
                                sum_transaction,
                                currency,
                                category_name,
                                limitsExceeded));
    }

}
