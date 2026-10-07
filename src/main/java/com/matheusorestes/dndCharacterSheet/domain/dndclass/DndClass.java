package com.matheusorestes.dndCharacterSheet.domain.dndclass;

import java.util.ArrayList;
import java.util.List;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;
import com.matheusorestes.dndCharacterSheet.domain.dice.DiceTerm;
import com.matheusorestes.dndCharacterSheet.domain.dice.DiceType;
import com.matheusorestes.dndCharacterSheet.domain.rules.AbilityType;
import com.matheusorestes.dndCharacterSheet.domain.rules.ArmorProficiency;
import com.matheusorestes.dndCharacterSheet.domain.rules.Skill;
import com.matheusorestes.dndCharacterSheet.domain.rules.ToolProficiency;
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
@Table(name = "character_class")
public class DndClass {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /** The D&D 5e API index. Null for a homebrew class. */
    @Column(name = "external_index", unique = true)
    private String externalIndex;

    @Column(nullable = false)
    private String name;

    private DiceTerm hitDie;
    private String hitPointsAtFirstLevel; //Strings to give more flexibility to homebrew content, e.g. "1d8 + Constitution modifier"
    private String hitPointsAtHigherLevels; //Strings to give more flexibility to homebrew content, e.g. "1d8 + Constitution modifier"
    private String spellcastingAbility;
    private boolean ispublic;

    //PROFICIENCIES
    @ElementCollection
    @CollectionTable(name = "class_armor_proficiencies", joinColumns = @JoinColumn(name = "class_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "armor_proficiency", nullable = false)
    private List<ArmorProficiency> armorProficiencies = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "class_weapon_proficiencies", joinColumns = @JoinColumn(name = "class_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "weapon_proficiency", nullable = false)
    private List<WeaponProficiency> weaponProficiencies = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "class_tool_proficiencies", joinColumns = @JoinColumn(name = "class_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "tool_proficiency", nullable = false)
    private List<ToolProficiency> toolProficiencies = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "class_saving_throw_proficiencies", joinColumns = @JoinColumn(name = "class_id"))
    @Column(name = "saving_throw_proficiency")
    private List<AbilityType> savingThrowProficiencies = new ArrayList<>();

    @ElementCollection 
    @CollectionTable (name = "class_skill_proficiencies", joinColumns = @JoinColumn(name = "class_id"))
    @Column(name = "skill_proficiency")
    private List<Skill> skillProficiencies = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "class_starting_equipment", joinColumns = @JoinColumn(name = "class_id"))
    @Column(name = "starting_equipment")
    private List<String> startingEquipment = new ArrayList<>();//String to give more flexibility to homebrew content, e.g. "1d8 + Constitution modifier"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CatalogSource source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    protected DndClass() {
        // Required by JPA.
    }

    public DndClass(String name, DiceTerm hitDie, String spellcastingAbility,
            User createdBy, boolean ispublic) {
        this(name, hitDie, spellcastingAbility, createdBy, ispublic, List.of(), List.of(), List.of());
    }

    public DndClass(String name, DiceTerm hitDie, String spellcastingAbility,
            User createdBy, boolean ispublic,
            List<ArmorProficiency> armorProficiencies,
            List<WeaponProficiency> weaponProficiencies,
            List<ToolProficiency> toolProficiencies) {
        this.name = name;
        this.hitDie = hitDie;
        this.spellcastingAbility = spellcastingAbility;
        this.createdBy = createdBy;
        this.ispublic = ispublic;
        setArmorProficiencies(armorProficiencies);
        setWeaponProficiencies(weaponProficiencies);
        setToolProficiencies(toolProficiencies);
        this.source = CatalogSource.HOMEBREW;
    }

    public static DndClass official(String externalIndex, String name, Integer hitDie,
            String spellcastingAbility) {
        DndClass characterClass = new DndClass(name, toHitDie(hitDie), spellcastingAbility, null, true);
        characterClass.externalIndex = externalIndex;
        characterClass.source = CatalogSource.OFFICIAL;
        return characterClass;
    }

    private static DiceTerm toHitDie(Integer sides) {
        if (sides == null) {
            return null;
        }
        return new DiceTerm(1, DiceType.valueOf("D" + sides), null);
    }

    public String getId() { return id; }
    public String getExternalIndex() { return externalIndex; }
    public String getName() { return name; }
    public DiceTerm getHitDie() { return hitDie; }
    public String getSpellcastingAbility() { return spellcastingAbility; }
    public boolean isIspublic() { return ispublic; }
    public CatalogSource getSource() { return source; }
    public User getCreatedBy() { return createdBy; }
    public List<ArmorProficiency> getArmorProficiencies() { return List.copyOf(armorProficiencies); }
    public List<WeaponProficiency> getWeaponProficiencies() { return List.copyOf(weaponProficiencies); }
    public List<ToolProficiency> getToolProficiencies() { return List.copyOf(toolProficiencies); }

    public void setArmorProficiencies(List<ArmorProficiency> armorProficiencies) {
        this.armorProficiencies = armorProficiencies == null ? new ArrayList<>() : new ArrayList<>(armorProficiencies);
    }

    public void setWeaponProficiencies(List<WeaponProficiency> weaponProficiencies) {
        this.weaponProficiencies = weaponProficiencies == null ? new ArrayList<>() : new ArrayList<>(weaponProficiencies);
    }

    public void setToolProficiencies(List<ToolProficiency> toolProficiencies) {
        this.toolProficiencies = toolProficiencies == null ? new ArrayList<>() : new ArrayList<>(toolProficiencies);
    }
}
