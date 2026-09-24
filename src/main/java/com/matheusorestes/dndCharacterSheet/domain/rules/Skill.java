package com.matheusorestes.dndCharacterSheet.domain.rules;

/** Standard D&D 5e skills and the ability each skill uses by default. */
public enum Skill {
    ACROBATICS(AbilityType.DEXTERITY),
    ANIMAL_HANDLING(AbilityType.WISDOM),
    ARCANA(AbilityType.INTELLIGENCE),
    ATHLETICS(AbilityType.STRENGTH),
    DECEPTION(AbilityType.CHARISMA),
    HISTORY(AbilityType.INTELLIGENCE),
    INSIGHT(AbilityType.WISDOM),
    INTIMIDATION(AbilityType.CHARISMA),
    INVESTIGATION(AbilityType.INTELLIGENCE),
    MEDICINE(AbilityType.WISDOM),
    NATURE(AbilityType.INTELLIGENCE),
    PERCEPTION(AbilityType.WISDOM),
    PERFORMANCE(AbilityType.CHARISMA),
    PERSUASION(AbilityType.CHARISMA),
    RELIGION(AbilityType.INTELLIGENCE),
    SLEIGHT_OF_HAND(AbilityType.DEXTERITY),
    STEALTH(AbilityType.DEXTERITY),
    SURVIVAL(AbilityType.WISDOM);

    private final AbilityType abilityType;

    Skill(AbilityType abilityType) {
        this.abilityType = abilityType;
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }
}
