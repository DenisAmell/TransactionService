package org.example.api.controllers;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.LimitsDto;
import org.example.api.factories.LimitsDtoFactory;
import org.example.services.TransactionService;
import org.example.storage.entities.LimitsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@RestController
public class LimitsController {

    @Autowired
    private final TransactionService transactionService;

    private final LimitsDtoFactory limitsDtoFactory;


    @GetMapping("api/limits")
    public List<LimitsDto> getListLimits() {

        List<LimitsEntity> transactionEntityList = transactionService.getAllLimits();

        return transactionEntityList.stream().map(limitsDtoFactory::makeLimitsDto).collect(Collectors.toList());
    }

    @PostMapping("api/limits/{limits_id}")
    public LimitsDto createLimits(
            @RequestParam("user_id") long userId,
            @RequestParam("sum_limit") double sumLimit,
            @RequestParam("category") String categoryName,
            @PathVariable("limits_id") long limits_id) {

        return limitsDtoFactory.makeLimitsDto(transactionService.setNewLimit(limits_id, userId, categoryName, sumLimit));
    }
}
