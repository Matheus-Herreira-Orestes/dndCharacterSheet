package com.matheusorestes.dndCharacterSheet.domain.characterclass;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;

public interface CharacterClassRepository extends JpaRepository<CharacterClass, String> {
    boolean existsBySource(CatalogSource source);
    Optional<CharacterClass> findByExternalIndex(String externalIndex);
}
