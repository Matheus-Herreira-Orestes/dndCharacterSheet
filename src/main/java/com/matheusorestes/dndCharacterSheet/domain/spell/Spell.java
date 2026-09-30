package com.matheusorestes.dndCharacterSheet.domain.spell;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "spell")
public class Spell {
    private Long id;
    private String name;
}
