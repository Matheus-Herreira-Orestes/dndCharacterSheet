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

@Entity
@Table(name = "character_weapon")
public class CharacterWeapon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    // Used for official/API weapons, e.g. "longsword".
    private String apiWeaponIndex;

    // Used for homebrew weapons only.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homebrew_weapon_id")
    private Weapon homebrewWeapon;

    private boolean equipped;
    private int quantity = 1;

    public CharacterWeapon() {
    }

    public CharacterWeapon(Character character, String apiWeaponIndex, Weapon homebrewWeapon, boolean equipped, int quantity) {
        this.character = character;
        this.apiWeaponIndex = apiWeaponIndex;
        this.homebrewWeapon = homebrewWeapon;
        this.equipped = equipped;
        this.quantity = quantity;
        validateState();
    }


    public String getId() {
        return id;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public String getApiWeaponIndex() {
        return apiWeaponIndex;
    }

    public void setApiWeaponIndex(String apiWeaponIndex) {
        this.apiWeaponIndex = apiWeaponIndex;
    }

    public Weapon getHomebrewWeapon() {
        return homebrewWeapon;
    }

    public void setHomebrewWeapon(Weapon homebrewWeapon) {
        this.homebrewWeapon = homebrewWeapon;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @PrePersist
    @PreUpdate
    private void validateState() {
        boolean hasApiWeapon = apiWeaponIndex != null && !apiWeaponIndex.isBlank();
        boolean hasHomebrewWeapon = homebrewWeapon != null;

        if (hasApiWeapon == hasHomebrewWeapon) {
            throw new IllegalStateException(
                "A character weapon must reference exactly one API or homebrew weapon."
            );
        }

        if (quantity < 1) {
            throw new IllegalStateException("A character weapon quantity must be at least 1.");
        }
    }
}
