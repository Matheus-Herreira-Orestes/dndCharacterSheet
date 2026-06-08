package com.matheusorestes.dndCharacterSheet.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpellcastingLevelDto {

    @JsonProperty("cantrips_known")
    private Integer cantripsKnown;

    @JsonProperty("spells_known")
    private Integer spellsKnown;

    @JsonProperty("spell_slots_level_1")
    private Integer spellSlotsLevel1;

    @JsonProperty("spell_slots_level_2")
    private Integer spellSlotsLevel2;

    @JsonProperty("spell_slots_level_3")
    private Integer spellSlotsLevel3;

    @JsonProperty("spell_slots_level_4")
    private Integer spellSlotsLevel4;

    @JsonProperty("spell_slots_level_5")
    private Integer spellSlotsLevel5;

    @JsonProperty("spell_slots_level_6")
    private Integer spellSlotsLevel6;

    @JsonProperty("spell_slots_level_7")
    private Integer spellSlotsLevel7;

    @JsonProperty("spell_slots_level_8")
    private Integer spellSlotsLevel8;

    @JsonProperty("spell_slots_level_9")
    private Integer spellSlotsLevel9;

    public Integer getCantripsKnown() {
        return cantripsKnown;
    }

    public void setCantripsKnown(Integer cantripsKnown) {
        this.cantripsKnown = cantripsKnown;
    }

    public Integer getSpellsKnown() {
        return spellsKnown;
    }

    public void setSpellsKnown(Integer spellsKnown) {
        this.spellsKnown = spellsKnown;
    }

    public Integer getSpellSlotsLevel1() {
        return spellSlotsLevel1;
    }

    public void setSpellSlotsLevel1(Integer spellSlotsLevel1) {
        this.spellSlotsLevel1 = spellSlotsLevel1;
    }

    public Integer getSpellSlotsLevel2() {
        return spellSlotsLevel2;
    }

    public void setSpellSlotsLevel2(Integer spellSlotsLevel2) {
        this.spellSlotsLevel2 = spellSlotsLevel2;
    }

    public Integer getSpellSlotsLevel3() {
        return spellSlotsLevel3;
    }

    public void setSpellSlotsLevel3(Integer spellSlotsLevel3) {
        this.spellSlotsLevel3 = spellSlotsLevel3;
    }

    public Integer getSpellSlotsLevel4() {
        return spellSlotsLevel4;
    }

    public void setSpellSlotsLevel4(Integer spellSlotsLevel4) {
        this.spellSlotsLevel4 = spellSlotsLevel4;
    }

    public Integer getSpellSlotsLevel5() {
        return spellSlotsLevel5;
    }

    public void setSpellSlotsLevel5(Integer spellSlotsLevel5) {
        this.spellSlotsLevel5 = spellSlotsLevel5;
    }

    public Integer getSpellSlotsLevel6() {
        return spellSlotsLevel6;
    }

    public void setSpellSlotsLevel6(Integer spellSlotsLevel6) {
        this.spellSlotsLevel6 = spellSlotsLevel6;
    }

    public Integer getSpellSlotsLevel7() {
        return spellSlotsLevel7;
    }

    public void setSpellSlotsLevel7(Integer spellSlotsLevel7) {
        this.spellSlotsLevel7 = spellSlotsLevel7;
    }

    public Integer getSpellSlotsLevel8() {
        return spellSlotsLevel8;
    }

    public void setSpellSlotsLevel8(Integer spellSlotsLevel8) {
        this.spellSlotsLevel8 = spellSlotsLevel8;
    }

    public Integer getSpellSlotsLevel9() {
        return spellSlotsLevel9;
    }

    public void setSpellSlotsLevel9(Integer spellSlotsLevel9) {
        this.spellSlotsLevel9 = spellSlotsLevel9;
    }

    
}