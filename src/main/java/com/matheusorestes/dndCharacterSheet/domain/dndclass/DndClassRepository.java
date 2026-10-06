package com.matheusorestes.dndCharacterSheet.domain.dndclass;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;

public interface DndClassRepository extends JpaRepository<DndClass, String> {
    boolean existsBySource(CatalogSource source);
    Optional<DndClass> findByExternalIndex(String externalIndex);
}
