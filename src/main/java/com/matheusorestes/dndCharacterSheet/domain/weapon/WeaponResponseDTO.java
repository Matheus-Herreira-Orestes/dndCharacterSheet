package com.matheusorestes.dndCharacterSheet.domain.weapon;

import java.util.List;

import com.matheusorestes.dndCharacterSheet.domain.dice.DiceTermDTO;
import com.matheusorestes.dndCharacterSheet.domain.rules.WeaponProficiency;

public record WeaponResponseDTO(
    String id,
    String name,
    String weight,
    String cost,
    boolean isPublic,
    String createdByLogin,
    List<WeaponProperty> properties,
    List<WeaponProficiency> proficiencies,
    List<DiceTermDTO> weaponDamage
) {
    public static WeaponResponseDTO from(Weapon weapon) {
        return new WeaponResponseDTO(
            weapon.getId(),
            weapon.getName(),
            weapon.getWeight(),
            weapon.getCost(),
            weapon.isIspublic(),
            weapon.getCreatedBy() == null ? null : weapon.getCreatedBy().getLogin(),
            weapon.getProperties(),
            weapon.getProficiencies(),
            weapon.getWeaponDamage().stream()
                .map(diceTerm -> diceTerm.toDTO())
                .toList()
        );
    }
}
