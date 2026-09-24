package com.matheusorestes.dndCharacterSheet.domain.dice;

import com.matheusorestes.dndCharacterSheet.domain.damage.DamageType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DiceTermDTO(
    @Positive int quantity,
    @NotNull DiceType diceType,
    @NotNull DamageType damageType
) {}
