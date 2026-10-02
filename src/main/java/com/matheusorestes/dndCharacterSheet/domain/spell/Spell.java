package com.matheusorestes.dndCharacterSheet.domain.spell;

import java.util.ArrayList;
import java.util.List;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;
import com.matheusorestes.dndCharacterSheet.domain.dice.DiceTerm;
import com.matheusorestes.dndCharacterSheet.domain.rules.MagicSchools;
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
@Table(name = "spell")
public class Spell {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /** The D&D 5e API index. Null for homebrew spells. */
    @Column(name = "external_index", unique = true)
    private String externalIndex;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private int level;
    private String castingTime;
    private String spellRange;
    private String duration;
    private String material;
    private boolean ritual;
    private boolean concentration;
    private boolean ispublic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CatalogSource source;

    @Enumerated(EnumType.STRING)
    private MagicSchools magicSchool;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    @ElementCollection
    @CollectionTable(name = "spell_components", joinColumns = @JoinColumn(name = "spell_id"))
    @Column(name = "component")
    private List<String> components = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "spell_damage", joinColumns = @JoinColumn(name = "spell_id"))
    private List<DiceTerm> spellDamage = new ArrayList<>();

    protected Spell() {
        // Required by JPA.
    }

    public Spell(String name, String description, int level, MagicSchools magicSchool,
            String castingTime, String spellRange, String duration, String material,
            boolean ritual, boolean concentration, List<String> components,
            List<DiceTerm> spellDamage, User createdBy, boolean ispublic) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.magicSchool = magicSchool;
        this.castingTime = castingTime;
        this.spellRange = spellRange;
        this.duration = duration;
        this.material = material;
        this.ritual = ritual;
        this.concentration = concentration;
        this.components = components == null ? new ArrayList<>() : new ArrayList<>(components);
        this.spellDamage = spellDamage == null ? new ArrayList<>() : new ArrayList<>(spellDamage);
        this.createdBy = createdBy;
        this.ispublic = ispublic;
        this.source = CatalogSource.HOMEBREW;
    }

    public static Spell official(String externalIndex, String name, String description, int level,
            MagicSchools magicSchool, String castingTime, String spellRange, String duration,
            String material, boolean ritual, boolean concentration, List<String> components) {
        Spell spell = new Spell(name, description, level, magicSchool, castingTime, spellRange,
                duration, material, ritual, concentration, components, List.of(), null, true);
        spell.externalIndex = externalIndex;
        spell.source = CatalogSource.OFFICIAL;
        return spell;
    }

    public String getId() { return id; }
    public String getExternalIndex() { return externalIndex; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getLevel() { return level; }
    public String getCastingTime() { return castingTime; }
    public String getSpellRange() { return spellRange; }
    public String getDuration() { return duration; }
    public String getMaterial() { return material; }
    public boolean isRitual() { return ritual; }
    public boolean isConcentration() { return concentration; }
    public boolean isIspublic() { return ispublic; }
    public CatalogSource getSource() { return source; }
    public MagicSchools getMagicSchool() { return magicSchool; }
    public User getCreatedBy() { return createdBy; }
    public List<String> getComponents() { return components; }
    public List<DiceTerm> getSpellDamage() { return spellDamage; }
}
