package org.example.api.factories;

import org.example.api.dto.CurrencyDto;
import org.example.storage.entities.CurrencyEntity;


public class CurrencyDtoFactory {
    public CurrencyDto makeCurrencyDto(CurrencyEntity entity) {
        return CurrencyDto.builder()
                .id(entity.getId())
                .currencyName(entity.getCurrencyName())
                .code(entity.getCode())
                .build();
    }
}
