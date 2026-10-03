package com.matheusorestes.dndCharacterSheet.domain.characterclass;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;
import com.matheusorestes.dndCharacterSheet.domain.user.User;

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

@Entity
@Table(name = "subclass")
public class Subclass {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /** The D&D 5e API index. Null for a homebrew subclass. */
    @Column(name = "external_index", unique = true)
    private String externalIndex;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String flavor;
    private boolean ispublic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CatalogSource source;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_class_id", nullable = false)
    private CharacterClass characterClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    protected Subclass() {
        // Required by JPA.
    }

    public Subclass(String name, String description, String flavor, CharacterClass characterClass,
            User createdBy, boolean ispublic) {
        this.name = name;
        this.description = description;
        this.flavor = flavor;
        this.characterClass = characterClass;
        this.createdBy = createdBy;
        this.ispublic = ispublic;
        this.source = CatalogSource.HOMEBREW;
    }

    public static Subclass official(String externalIndex, String name, String description, String flavor,
            CharacterClass characterClass) {
        Subclass subclass = new Subclass(name, description, flavor, characterClass, null, true);
        subclass.externalIndex = externalIndex;
        subclass.source = CatalogSource.OFFICIAL;
        return subclass;
    }

    public String getId() { return id; }
    public String getExternalIndex() { return externalIndex; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getFlavor() { return flavor; }
    public boolean isIspublic() { return ispublic; }
    public CatalogSource getSource() { return source; }
    public CharacterClass getCharacterClass() { return characterClass; }
    public User getCreatedBy() { return createdBy; }
}
