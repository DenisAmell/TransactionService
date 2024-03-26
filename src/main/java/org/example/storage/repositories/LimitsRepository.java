package org.example.storage.repositories;

import org.example.storage.entities.CategoryEntity;
import org.example.storage.entities.LimitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LimitsRepository extends JpaRepository<LimitsEntity, Long> {
    List<LimitsEntity> findAllByUserIdAndCategoryId(Long userId, CategoryEntity category);
}
