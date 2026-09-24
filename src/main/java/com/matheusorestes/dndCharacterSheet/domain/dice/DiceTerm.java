package com.matheusorestes.dndCharacterSheet.domain.dice;

import com.matheusorestes.dndCharacterSheet.domain.damage.DamageType;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class DiceTerm {

    private int quantity;

    @Enumerated(EnumType.STRING)
    private DiceType diceType;

    @Enumerated(EnumType.STRING)
    private DamageType damageType;


    protected DiceTerm() {
        // Required by JPA
    }

    public DiceTerm(int quantity, DiceType diceType, DamageType damageType) {
        this.quantity = quantity;
        this.diceType = diceType;
        this.damageType = damageType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DiceType getDiceType() {
        return diceType;
    }

    public void setDiceType(DiceType diceType) {
        this.diceType = diceType;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public DiceTermDTO toDTO() {
        return new DiceTermDTO(quantity, diceType, damageType);
    }


}