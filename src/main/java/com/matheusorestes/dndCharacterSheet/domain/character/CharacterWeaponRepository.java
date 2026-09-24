package com.matheusorestes.dndCharacterSheet.domain.character;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterWeaponRepository extends JpaRepository<CharacterWeapon, String> {
    
}
