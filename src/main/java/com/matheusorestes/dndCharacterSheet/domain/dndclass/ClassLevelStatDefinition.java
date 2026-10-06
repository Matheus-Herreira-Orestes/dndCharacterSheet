package com.matheusorestes.dndCharacterSheet.domain.dndclass;

import jakarta.persistence.Column;
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
import jakarta.persistence.UniqueConstraint;

/** Defines one configurable column in a class's level-progression table. */
@Entity
@Table(name = "class_level_stat_definition", uniqueConstraints =
        @UniqueConstraint(name = "uk_class_level_stat_key", columnNames = { "character_class_id", "stat_key" }))
public class ClassLevelStatDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_class_id", nullable = false)
    private DndClass characterClass;

    @Column(name = "stat_key", nullable = false, updatable = false)
    private String key;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassLevelStatDataType dataType;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    protected ClassLevelStatDefinition() { }

    public ClassLevelStatDefinition(DndClass characterClass, String key, String displayName,
            ClassLevelStatDataType dataType, int displayOrder) {
        this.characterClass = characterClass;
        this.key = key;
        this.displayName = displayName;
        this.dataType = dataType;
        this.displayOrder = displayOrder;
    }

    public String getId() { return id; }
    public DndClass getCharacterClass() { return characterClass; }
    public String getKey() { return key; }
    public String getDisplayName() { return displayName; }
    public ClassLevelStatDataType getDataType() { return dataType; }
    public int getDisplayOrder() { return displayOrder; }
}
