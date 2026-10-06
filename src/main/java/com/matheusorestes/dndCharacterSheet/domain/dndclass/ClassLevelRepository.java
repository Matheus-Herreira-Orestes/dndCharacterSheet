package com.matheusorestes.dndCharacterSheet.domain.dndclass;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassLevelRepository extends JpaRepository<ClassLevel, String> {
    boolean existsByCharacterClass(DndClass characterClass);
    List<ClassLevel> findByCharacterClassOrderByLevel(DndClass characterClass);
}
