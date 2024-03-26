package org.example.api.factories;

import org.example.api.dto.TransactionDto;
import org.example.storage.entities.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoFactory {

    public TransactionDto makeTransactionDto(TransactionEntity entity) {
        return TransactionDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .amountTo(entity.getAmountTo())
                .amountFrom(entity.getAmountFrom())
                .sumTransaction(entity.getSumTransaction())
                .currencyShortname(entity.getCurrencyShortname().getCurrencyName())
                .categoryName(entity.getTypeTransaction().getNameCategory())
                .limitsExceeded(entity.getLimitsExceeded())
                .build();
    }
}
