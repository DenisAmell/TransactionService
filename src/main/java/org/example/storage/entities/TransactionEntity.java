package org.example.storage.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@IdClass(TransactionId.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Id
    private Long userId;


    private Long amountFrom;


    private Long amountTo;

    @ManyToOne
    @JoinColumn(name = "fk_transaction_currency", referencedColumnName = "id")
    private CurrencyEntity currencyShortname;

    @ManyToOne
    @JoinColumn(name = "fk_transaction_category", referencedColumnName = "id")
    private CategoryEntity typeTransaction;

    private Double sumTransaction;

    private Boolean limitsExceeded;

    @Builder.Default
    private Instant transactionDateTime = Instant.now();

    public TransactionEntity() {

    }
}
