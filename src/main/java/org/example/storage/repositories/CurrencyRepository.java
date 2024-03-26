package org.example.storage.repositories;

import org.example.storage.entities.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

    Optional<CurrencyEntity> findByCurrencyName(String currencyName);

    CurrencyEntity getByCurrencyName(String currencyName);
}
