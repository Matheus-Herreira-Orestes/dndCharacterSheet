package com.matheusorestes.dndCharacterSheet.domain.armor;

public enum ArmorType {
    LIGHT("1 minute"),
    MEDIUM("1 minute"),
    HEAVY("10 minutes"),
    SHIELD("1 action");

    private final String timeToWear;

    ArmorType(String timeToWear) {
        this.timeToWear = timeToWear;
    }

    public String getTimeToWear() {
        return timeToWear;
    }
}
