package org.example.storage.repositories;

import org.example.storage.entities.CoursesEntity;
import org.example.storage.entities.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CoursesRepository extends JpaRepository<CoursesEntity, Long> {
   CoursesEntity findByDateAndCurrencyId(LocalDate date, CurrencyEntity currencyId);

   CoursesEntity findByCurrencyId(CurrencyEntity currencyEntity);

}
