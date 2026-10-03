package com.matheusorestes.dndCharacterSheet.domain.characterclass;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;

public interface SubclassRepository extends JpaRepository<Subclass, String> {
    boolean existsBySource(CatalogSource source);
}
