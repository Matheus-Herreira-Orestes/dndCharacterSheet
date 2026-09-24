package com.matheusorestes.dndCharacterSheet.domain.dice;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * A pool of hit dice of one type, such as a level-three fighter's 3d10.
 * It records both the character's maximum dice and the dice still available
 * to spend during a short rest.
 */
@Embeddable
public class HitDice {

    @Column(nullable = false)
    private int maximum;

    @Column(nullable = false)
    private int available;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiceType diceType;

    protected HitDice() {
        // Required by JPA when reading this value from the database.
    }

    public HitDice(int maximum, int available, DiceType diceType) {
        if (maximum < 0) {
            throw new IllegalArgumentException("Maximum hit dice cannot be negative.");
        }
        if (available < 0 || available > maximum) {
            throw new IllegalArgumentException("Available hit dice must be between zero and the maximum.");
        }
        if (diceType == null) {
            throw new IllegalArgumentException("A hit-die type is required.");
        }

        this.maximum = maximum;
        this.available = available;
        this.diceType = diceType;
    }

    public int getMaximum() {
        return maximum;
    }

    public int getAvailable() {
        return available;
    }

    public DiceType getDiceType() {
        return diceType;
    }
}
