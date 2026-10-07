package com.matheusorestes.dndCharacterSheet.domain.weapon;

import java.util.List;

import com.matheusorestes.dndCharacterSheet.domain.dice.DiceTermDTO;
import com.matheusorestes.dndCharacterSheet.domain.rules.WeaponProficiency;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * CreateWeaponDTO
 */
public record CreateWeaponDTO(
    @NotBlank String name,
    boolean isPublic,
    List<WeaponProperty> properties,
    List<WeaponProficiency> proficiencies,
    @NotNull @Size(min = 1) List<@Valid DiceTermDTO> weaponDamage
) {}
