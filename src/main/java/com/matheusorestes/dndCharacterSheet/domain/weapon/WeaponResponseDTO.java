package com.matheusorestes.dndCharacterSheet.domain.weapon;

import java.util.List;

import com.matheusorestes.dndCharacterSheet.domain.dice.DiceTermDTO;

public record WeaponResponseDTO(
    String id,
    String name,
    boolean isPublic,
    String createdByLogin,
    List<String> properties,
    List<DiceTermDTO> weaponDamage
) {
    public static WeaponResponseDTO from(Weapon weapon) {
        return new WeaponResponseDTO(
            weapon.getId(),
            weapon.getName(),
            weapon.isIspublic(),
            weapon.getCreatedBy() == null ? null : weapon.getCreatedBy().getLogin(),
            weapon.getProperties(),
            weapon.getWeaponDamage().stream()
                .map(diceTerm -> diceTerm.toDTO())
                .toList()
        );
    }
}
