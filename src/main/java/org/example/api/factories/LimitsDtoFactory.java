package org.example.api.factories;

import org.example.api.dto.LimitsDto;
import org.example.storage.entities.LimitsEntity;
import org.springframework.stereotype.Component;

@Component
public class LimitsDtoFactory {
    public LimitsDto makeLimitsDto(LimitsEntity entity) {
        return LimitsDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .limit(entity.getSumLimit())
                .date(entity.getDate())
                .categoryName(entity.getCategoryId().getNameCategory())
                .build();
    }
}
