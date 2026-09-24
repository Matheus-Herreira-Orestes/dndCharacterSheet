package com.matheusorestes.dndCharacterSheet.domain.weapon;

import java.util.ArrayList;
import java.util.List;

import com.matheusorestes.dndCharacterSheet.domain.dice.DiceTerm;
import com.matheusorestes.dndCharacterSheet.domain.user.User;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "weapon")
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    @ElementCollection 
    @CollectionTable(
        name = "weapon_properties",
        joinColumns = @JoinColumn(name = "weapon_id")
    )
    private List<String> properties = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
        name = "weapon_damage",
        joinColumns = @JoinColumn(name = "weapon_id")
    )
    private List<DiceTerm> WeaponDamage = new ArrayList<>();

    private boolean ispublic;

    public Weapon() {
    }

    public Weapon(
            String name, 
            User createdBy,
            boolean ispublic, 
            List<String> properties, 
            List<DiceTerm> WeaponDamage
        ) {
        this.name = name;
        this.createdBy = createdBy;
        this.ispublic = ispublic;
        this.properties = properties == null ? new ArrayList<>() : new ArrayList<>(properties);
        this.WeaponDamage = new ArrayList<>(WeaponDamage);
    }


    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isIspublic() {
        return ispublic;
    }

    public void setIspublic(boolean ispublic) {
        this.ispublic = ispublic;
    }
    
    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties == null ? new ArrayList<>() : new ArrayList<>(properties);
    }

    public List<DiceTerm> getWeaponDamage() {
        return WeaponDamage;
    }

    public void setWeaponDamage(List<DiceTerm> weaponDamage) {
        WeaponDamage = new ArrayList<>(weaponDamage);
    }
}
