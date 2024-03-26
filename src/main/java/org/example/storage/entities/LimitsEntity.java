package org.example.storage.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "limits")
public class LimitsEntity {

    @Id
    private Long id;

    private Long userId;

    @Column(name = "sum_limit")
    private Double sumLimit;

    @Builder.Default
    private Date date = Date.from(Instant.now());

    @ManyToOne
    @JoinColumn(name = "fk_category_id", referencedColumnName = "id")
    private CategoryEntity categoryId;

    public LimitsEntity() {

    }
}
