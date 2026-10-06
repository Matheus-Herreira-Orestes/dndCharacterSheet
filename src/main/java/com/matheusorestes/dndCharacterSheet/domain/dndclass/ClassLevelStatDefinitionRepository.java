package com.matheusorestes.dndCharacterSheet.domain.dndclass;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassLevelStatDefinitionRepository extends JpaRepository<ClassLevelStatDefinition, String> {
    Optional<ClassLevelStatDefinition> findByCharacterClassAndKey(DndClass characterClass, String key);
}
