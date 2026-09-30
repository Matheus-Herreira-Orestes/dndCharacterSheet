package com.matheusorestes.dndCharacterSheet.domain.character;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, String> {
    Optional<Character> findByIdAndCreatedById(String id, String userId);
}
