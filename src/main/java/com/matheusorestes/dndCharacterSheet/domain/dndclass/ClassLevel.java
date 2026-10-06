package com.matheusorestes.dndCharacterSheet.domain.dndclass;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/** Shared information for one level; optional columns live in ClassLevelStatValue. */
@Entity
@Table(name = "class_level", uniqueConstraints =
        @UniqueConstraint(name = "uk_class_level", columnNames = { "character_class_id", "level" }))
public class ClassLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_class_id", nullable = false)
    private DndClass characterClass;

    @Column(nullable = false)
    private int level;

    @Column(name = "proficiency_bonus", nullable = false)
    private int proficiencyBonus;

    @ElementCollection
    @CollectionTable(name = "class_level_feature", joinColumns = @JoinColumn(name = "class_level_id"))
    @OrderColumn(name = "feature_order")
    @Column(name = "feature_name", nullable = false)
    private List<String> features = new ArrayList<>();

    protected ClassLevel() { }

    public ClassLevel(DndClass characterClass, int level, int proficiencyBonus, List<String> features) {
        if (level < 1 || level > 20) {
            throw new IllegalArgumentException("Class level must be between 1 and 20");
        }
        this.characterClass = characterClass;
        this.level = level;
        this.proficiencyBonus = proficiencyBonus;
        if (features != null) {
            this.features.addAll(features);
        }
    }

    public String getId() { return id; }
    public DndClass getCharacterClass() { return characterClass; }
    public int getLevel() { return level; }
    public int getProficiencyBonus() { return proficiencyBonus; }
    public List<String> getFeatures() { return List.copyOf(features); }
}
