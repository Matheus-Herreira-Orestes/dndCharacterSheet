package com.matheusorestes.dndCharacterSheet.domain.weapon;

import java.util.ArrayList;
import java.util.List;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;
import com.matheusorestes.dndCharacterSheet.domain.dice.DiceTerm;
import com.matheusorestes.dndCharacterSheet.domain.rules.WeaponProficiency;
import com.matheusorestes.dndCharacterSheet.domain.user.User;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    /** The D&D 5e API index. Null for homebrew weapons. */
    @Column(name = "external_index", unique = true)
    private String externalIndex;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CatalogSource source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    @ElementCollection 
    @CollectionTable(
        name = "weapon_properties",
        joinColumns = @JoinColumn(name = "weapon_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "weapon_property", nullable = false)
    private List<WeaponProperty> properties = new ArrayList<>();

    /** Proficiencies that allow this weapon, such as SIMPLE_WEAPONS and JAVELIN. */
    @ElementCollection
    @CollectionTable(
        name = "weapon_proficiencies",
        joinColumns = @JoinColumn(name = "weapon_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "weapon_proficiency", nullable = false)
    private List<WeaponProficiency> proficiencies = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
        name = "weapon_damage",
        joinColumns = @JoinColumn(name = "weapon_id")
    )
    private List<DiceTerm> weaponDamage = new ArrayList<>();

    private boolean ispublic;

    protected Weapon() {
    }

    public Weapon(
            String name, 
            User createdBy,
            boolean ispublic, 
            List<WeaponProperty> properties,
            List<WeaponProficiency> proficiencies,
            List<DiceTerm> weaponDamage
        ) {
        this.name = name;
        this.createdBy = createdBy;
        this.ispublic = ispublic;
        this.properties = properties == null ? new ArrayList<>() : new ArrayList<>(properties);
        this.proficiencies = proficiencies == null ? new ArrayList<>() : new ArrayList<>(proficiencies);
        this.weaponDamage = weaponDamage == null ? new ArrayList<>() : new ArrayList<>(weaponDamage);
        this.source = CatalogSource.HOMEBREW;
    }

    public static Weapon official(String externalIndex, String name, List<WeaponProperty> properties,
            List<WeaponProficiency> proficiencies, List<DiceTerm> weaponDamage) {
        Weapon weapon = new Weapon(name, null, true, properties, proficiencies, weaponDamage);
        weapon.externalIndex = externalIndex;
        weapon.source = CatalogSource.OFFICIAL;
        return weapon;
    }


    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getExternalIndex() {
        return externalIndex;
    }

    public CatalogSource getSource() {
        return source;
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
    
    public List<WeaponProperty> getProperties() {
        return List.copyOf(properties);
    }

    public void setProperties(List<WeaponProperty> properties) {
        this.properties = properties == null ? new ArrayList<>() : new ArrayList<>(properties);
    }

    public List<WeaponProficiency> getProficiencies() {
        return List.copyOf(proficiencies);
    }

    public void setProficiencies(List<WeaponProficiency> proficiencies) {
        this.proficiencies = proficiencies == null ? new ArrayList<>() : new ArrayList<>(proficiencies);
    }

    public List<DiceTerm> getWeaponDamage() {
        return weaponDamage;
    }

    public void setWeaponDamage(List<DiceTerm> weaponDamage) {
        this.weaponDamage = weaponDamage == null ? new ArrayList<>() : new ArrayList<>(weaponDamage);
    }
}
