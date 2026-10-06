package com.matheusorestes.dndCharacterSheet.domain.dndclass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/** Value of one configurable progression column at a particular level. */
@Entity
@Table(name = "class_level_stat_value", uniqueConstraints =
        @UniqueConstraint(name = "uk_level_stat", columnNames = { "class_level_id", "stat_definition_id" }))
public class ClassLevelStatValue {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_level_id", nullable = false)
    private ClassLevel classLevel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stat_definition_id", nullable = false)
    private ClassLevelStatDefinition definition;

    @Column(name = "stat_value", nullable = false, columnDefinition = "TEXT")
    private String value;

    protected ClassLevelStatValue() { }

    public ClassLevelStatValue(ClassLevel classLevel, ClassLevelStatDefinition definition, String value) {
        this.classLevel = classLevel;
        this.definition = definition;
        this.value = value;
    }

    public String getId() { return id; }
    public ClassLevel getClassLevel() { return classLevel; }
    public ClassLevelStatDefinition getDefinition() { return definition; }
    public String getValue() { return value; }
}
