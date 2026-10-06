package com.matheusorestes.dndCharacterSheet.domain.subclass;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;

public interface SubClassRepository extends JpaRepository<SubClass, String> {
    boolean existsBySource(CatalogSource source);
}
