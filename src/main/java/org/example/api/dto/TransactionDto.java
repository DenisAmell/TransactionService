package org.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    @JsonProperty("transaction_id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("amount_from")
    private Long amountFrom;

    @JsonProperty("amount_to")
    private Long amountTo;

    @JsonProperty("sum_transaction")
    private Double sumTransaction;

    @JsonProperty("currency_shortname")
    private String currencyShortname;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("limits_exceeded")
    private Boolean limitsExceeded;

    @Builder.Default
    @JsonProperty("transaction_datetime")
    private Instant transactionDateTime = Instant.now();
}
