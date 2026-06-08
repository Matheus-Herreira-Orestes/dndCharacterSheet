package com.matheusorestes.dndCharacterSheet.integration.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SpellcastingDto {

    private Integer level;

    private List<SpellcastingInfoDto> info;

    @JsonProperty("spellcasting_ability")
    private ApiReferenceDto spellcastingAbility;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<SpellcastingInfoDto> getInfo() {
        return info;
    }

    public void setInfo(List<SpellcastingInfoDto> info) {
        this.info = info;
    }

    public ApiReferenceDto getSpellcastingAbility() {
        return spellcastingAbility;
    }

    public void setSpellcastingAbility(ApiReferenceDto spellcastingAbility) {
        this.spellcastingAbility = spellcastingAbility;
    }
}