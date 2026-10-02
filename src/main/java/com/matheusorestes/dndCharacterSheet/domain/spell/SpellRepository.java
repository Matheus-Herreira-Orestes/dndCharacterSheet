package com.matheusorestes.dndCharacterSheet.domain.spell;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;

public interface SpellRepository extends JpaRepository<Spell, String> {
    boolean existsBySource(CatalogSource source);
}
