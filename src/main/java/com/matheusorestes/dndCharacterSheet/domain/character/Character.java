package com.matheusorestes.dndCharacterSheet.domain.character;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.matheusorestes.dndCharacterSheet.domain.dice.HitDice;
import com.matheusorestes.dndCharacterSheet.domain.rules.AbilityType;
import com.matheusorestes.dndCharacterSheet.domain.rules.Skill;
import com.matheusorestes.dndCharacterSheet.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="character")
public class Character {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Basic Information
    private String name;

    private String race;

    private String characterClass;

    private String background;

    private String alignment;

    private int level;

    private int profeciencyBonus;

    /**
     * Server-generated key of the image in the photo storage. Image bytes never
     * belong in this table.
     */
    @Column(name = "character_photo_key", length = 512)
    private String characterPhotoKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    //combat stats
    private int armorClass;

    private int initiative;

    private int speed;

    private int hitPoints;

    private int currentHitPoints;

    private int temporaryHitPoints;

    @ElementCollection 
    @CollectionTable(
        name = "character_hit_dice",
        joinColumns = @JoinColumn(name = "character_id")
    )
    private List<HitDice> hitDice = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
        name = "character_death_saves",
        joinColumns = @JoinColumn(name = "character_id")
    )
    private List<Boolean> deathSaves = new ArrayList<>();

    // Ability scores, for example STRENGTH -> 16.
    @ElementCollection
    @CollectionTable(
        name = "character_ability_scores",
        joinColumns = @JoinColumn(name = "character_id")
    )
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "ability")
    @Column(name = "score", nullable = false)
    private Map<AbilityType, Integer> abilityScores = createDefaultAbilityScores();

    // The abilities for which this character is proficient in saving throws.
    @ElementCollection
    @CollectionTable(
        name = "character_saving_throw_proficiencies",
        joinColumns = @JoinColumn(name = "character_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "ability", nullable = false)
    private Set<AbilityType> proficientSavingThrows = new HashSet<>();

    // Skills in which this character is proficient.
    @ElementCollection
    @CollectionTable(
        name = "character_skill_proficiencies",
        joinColumns = @JoinColumn(name = "character_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "skill", nullable = false)
    private Set<Skill> proficientSkills = new HashSet<>();

    //personality traits, ideals, bonds, and flaws
    private String personalityTraits;
    private String ideals;
    private String bonds;
    private String flaws;

    //Features and traits
    @ElementCollection
    @CollectionTable(
        name = "character_features_and_traits",
        joinColumns = @JoinColumn(name = "character_id")
    )
    @MapKeyColumn(name = "feature_name")
    @Column(name = "feature_description", nullable = false)
    private Map<String, String> featuresAndTraits = new HashMap<>();

    //Money and equipment
    private double copperPieces;
    private double silverPieces;
    private double electrumPieces;
    private double goldPieces;
    private double platinumPieces;


    @ElementCollection
    @CollectionTable(
        name = "character_equipment",
        joinColumns = @JoinColumn(name = "character_id")
    )
    @Column(name = "equipment_item", nullable = false)
    private List<String> equipment = new ArrayList<>();

    //Other profeciencies and languages
    @ElementCollection
    @CollectionTable(
        name = "character_other_proficiencies",
        joinColumns = @JoinColumn(name = "character_id")
    )
    @Column(name = "proficiency", nullable = false)
    private List<String> otherProficiencies = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
        name = "character_languages",
        joinColumns = @JoinColumn(name = "character_id")
    )
    @Column(name = "language", nullable = false)
    private List<String> languages = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
        name = "Other_notes",
        joinColumns = @JoinColumn(name = "character_id")
    )
    @Column(name = "note", nullable = false)
    private List<String> otherNotes = new ArrayList<>();


    //Backstory, other traits and habilitys, treasure, allies, and enemies, and other information can be added as needed.
    private String backstory;
    @ElementCollection
    @CollectionTable(
        name = "character_other_traits_and_abilities",
        joinColumns = @JoinColumn(name = "character_id")
    )
    @MapKeyColumn(name = "trait_name")
    @Column(name = "trait_description", nullable = false)
    private HashMap<String, String> otherTraitsAndAbilities = new HashMap<>();

    @ElementCollection
    @CollectionTable(
        name = "character_treasure",
        joinColumns = @JoinColumn(name = "character_id")
    )
    @Column(name = "treasure_item", nullable = false)
    private List<String> treasure = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
        name = "character_allies_and_enemies",
        joinColumns = @JoinColumn(name = "character_id")
    )
    @MapKeyColumn(name = "name")
    @Column(name = "description", nullable = false)
    private HashMap<String, String> alliesAndEnemies = new HashMap<>();

    // Constructors, getters, and setters omitted for brevity.
    private static Map<AbilityType, Integer> createDefaultAbilityScores() {
        Map<AbilityType, Integer> scores = new EnumMap<>(AbilityType.class);
        for (AbilityType ability : AbilityType.values()) {
            scores.put(ability, 10);
        }
        return scores;
    }

    //MAGICCCCC
    //KEY HABILITIES, CD DO TR, ATTACK BONUS
    @Enumerated(EnumType.STRING)
    private AbilityType keyAbility;

    private int spellSaveDC;

    private int spellAttackBonus;

    //SPELLS

    public int getAbilityScore(AbilityType abilityType) {
        return abilityScores.get(abilityType);
    }

    public String getId() {
        return id;
    }

    public String getCharacterPhotoKey() {
        return characterPhotoKey;
    }

    public void setCharacterPhotoKey(String characterPhotoKey) {
        this.characterPhotoKey = characterPhotoKey;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setAbilityScore(AbilityType abilityType, int score) {
        Objects.requireNonNull(abilityType, "An ability type is required.");
        if (score < 1) {
            throw new IllegalArgumentException("An ability score must be at least 1.");
        }
        abilityScores.put(abilityType, score);
    }

    public int getAbilityModifier(AbilityType abilityType) {
        return Math.floorDiv(getAbilityScore(abilityType) - 10, 2);
    }

    public int getSkillModifier(Skill skill) {
        Objects.requireNonNull(skill, "A skill is required.");
        int modifier = getAbilityModifier(skill.getAbilityType());
        return proficientSkills.contains(skill) ? modifier + profeciencyBonus : modifier;
    }

    public void addSkillProficiency(Skill skill) {
        proficientSkills.add(Objects.requireNonNull(skill, "A skill is required."));
    }

    public void removeSkillProficiency(Skill skill) {
        proficientSkills.remove(skill);
    }

    public void addSavingThrowProficiency(AbilityType abilityType) {
        proficientSavingThrows.add(
            Objects.requireNonNull(abilityType, "An ability type is required.")
        );
    }

    public int getSavingThrowModifier(AbilityType abilityType) {
        int modifier = getAbilityModifier(abilityType);
        return proficientSavingThrows.contains(abilityType)
            ? modifier + profeciencyBonus
            : modifier;
    }
}
