package org.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LimitsDto {

    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    private Double limit;

    @Builder.Default
    private Date date = Date.from(Instant.now());

    @JsonProperty("category_name")
    private String categoryName;

}
