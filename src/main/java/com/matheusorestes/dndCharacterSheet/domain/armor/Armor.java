package com.matheusorestes.dndCharacterSheet.domain.armor;

import com.matheusorestes.dndCharacterSheet.domain.rules.AbilityType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "armor")
public class Armor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(value = EnumType.STRING)
    private ArmorType armorType;

    private int armorClass;

    @Enumerated (value = EnumType.STRING)
    private AbilityType modifier;

    private int maxBonus;

    private int strengthRequirement;

    private boolean stealthDisadvantage;

    private String weight;//String to add more freedon to homebrew users

    private String cost;//String to add more freedon to homebrew users

    private boolean ispublic;
}
