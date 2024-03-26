package org.example.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.example.storage.entities.CategoryEntity;
import org.example.storage.entities.CurrencyEntity;
import org.example.storage.entities.LimitsEntity;
import org.example.storage.entities.TransactionEntity;
import org.example.storage.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LimitsRepository limitsRepository;

    public TransactionService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public TransactionEntity receiveTransaction(
            long user_id,
            long amountFrom,
            long amountTo,
            double sumTransaction,
            CurrencyEntity currency,
            String categoryName,
            boolean limitsExceeded) {

        TransactionEntity transaction = transactionRepository.saveAndFlush(
                TransactionEntity.builder()
                        .userId(user_id)
                        .amountFrom(amountFrom)
                        .amountTo(amountTo)
                        .sumTransaction(sumTransaction)
                        .limitsExceeded(limitsExceeded)
                        .currencyShortname(currency)
                        .typeTransaction(categoryRepository.getByNameCategory(categoryName))
                        .build()
        );

        return transaction;
    }

    @Autowired
    private final EntityManager entityManager;

    public List<Object[]> getTransactionsExceedingLimit() {

        String sqlQuery = "select t.user_id, t.transaction_date_time, t.sum_transaction, t.limits_exceeded,  l.sum_limit, l.date, l.fk_category_id\n" +
                "    from limits l\n" +
                "    join(\n" +
                "        select transaction.limits_exceeded, transaction.sum_transaction, transaction.user_id, transaction.transaction_date_time\n" +
                "            from transaction\n" +
                "            where transaction.limits_exceeded = TRUE\n" +
                "    ) t ON  t.sum_transaction < l.sum_limit\n" +
                "group by t.user_id, t.transaction_date_time, l.sum_limit, l.date, l.fk_category_id, t.sum_transaction, t.limits_exceeded;";

        return entityManager.createNativeQuery(sqlQuery).getResultList();
    }

    public LimitsEntity setNewLimit(long id, long user_id, String categoryName, Double sumLimit) {
        CategoryEntity category = categoryRepository.findByNameCategory(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Currency with name " + categoryName + " not found"));

        return limitsRepository.saveAndFlush(
                LimitsEntity.builder()
                        .id(id)
                        .userId(user_id)
                        .sumLimit(sumLimit)
                        .categoryId(category)
                        .build()
        );
    }

    public List<LimitsEntity> getAllLimits() {
        return limitsRepository.findAll();
    }

}
