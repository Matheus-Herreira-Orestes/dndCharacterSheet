package com.matheusorestes.dndCharacterSheet.domain.character;

import com.matheusorestes.dndCharacterSheet.domain.weapon.Weapon;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/** A weapon owned by a character, whether the weapon is official or homebrew. */
@Entity
@Table(name = "character_weapon")
public class CharacterWeapon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weapon_id", nullable = false)
    private Weapon weapon;

    private boolean equipped;
    private int quantity = 1;

    protected CharacterWeapon() {
        // Required by JPA.
    }

    public CharacterWeapon(Character character, Weapon weapon, boolean equipped, int quantity) {
        this.character = character;
        this.weapon = weapon;
        this.equipped = equipped;
        this.quantity = quantity;
        validateState();
    }

    public String getId() { return id; }
    public Character getCharacter() { return character; }
    public void setCharacter(Character character) { this.character = character; }
    public Weapon getWeapon() { return weapon; }
    public void setWeapon(Weapon weapon) { this.weapon = weapon; }
    public boolean isEquipped() { return equipped; }
    public void setEquipped(boolean equipped) { this.equipped = equipped; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @PrePersist
    @PreUpdate
    private void validateState() {
        if (character == null) {
            throw new IllegalStateException("A character weapon must belong to a character.");
        }
        if (weapon == null) {
            throw new IllegalStateException("A character weapon must reference a local weapon.");
        }
        if (quantity < 1) {
            throw new IllegalStateException("A character weapon quantity must be at least 1.");
        }
    }
}
