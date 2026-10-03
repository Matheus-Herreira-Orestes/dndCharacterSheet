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
@Table(name = "character_class")
public class CharacterClass {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /** The D&D 5e API index. Null for a homebrew class. */
    @Column(name = "external_index", unique = true)
    private String externalIndex;

    @Column(nullable = false)
    private String name;

    private Integer hitDie;
    private String spellcastingAbility;
    private boolean ispublic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CatalogSource source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    protected CharacterClass() {
        // Required by JPA.
    }

    public CharacterClass(String name, Integer hitDie, String spellcastingAbility,
            User createdBy, boolean ispublic) {
        this.name = name;
        this.hitDie = hitDie;
        this.spellcastingAbility = spellcastingAbility;
        this.createdBy = createdBy;
        this.ispublic = ispublic;
        this.source = CatalogSource.HOMEBREW;
    }

    public static CharacterClass official(String externalIndex, String name, Integer hitDie,
            String spellcastingAbility) {
        CharacterClass characterClass = new CharacterClass(name, hitDie, spellcastingAbility, null, true);
        characterClass.externalIndex = externalIndex;
        characterClass.source = CatalogSource.OFFICIAL;
        return characterClass;
    }

    public String getId() { return id; }
    public String getExternalIndex() { return externalIndex; }
    public String getName() { return name; }
    public Integer getHitDie() { return hitDie; }
    public String getSpellcastingAbility() { return spellcastingAbility; }
    public boolean isIspublic() { return ispublic; }
    public CatalogSource getSource() { return source; }
    public User getCreatedBy() { return createdBy; }
}
