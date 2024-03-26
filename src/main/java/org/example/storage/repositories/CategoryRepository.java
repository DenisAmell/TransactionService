package org.example.storage.repositories;

import org.example.storage.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity getByNameCategory(String categoryName);
    Optional<CategoryEntity> findByNameCategory(String categoryName);
}
