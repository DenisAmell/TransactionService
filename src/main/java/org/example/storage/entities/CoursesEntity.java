package org.example.storage.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class CoursesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long coursesId;

    @Builder.Default
    private LocalDate date = LocalDate.now();

    private Double coefficient;

    @ManyToOne
    @JoinColumn(name = "fk_currency_id", referencedColumnName = "id")
    private CurrencyEntity currencyId;



}
