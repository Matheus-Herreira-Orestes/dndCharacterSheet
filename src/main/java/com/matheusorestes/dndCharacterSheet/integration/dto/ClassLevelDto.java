package com.matheusorestes.dndCharacterSheet.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ClassLevelDto {

    private String index;
    private String url;

    private Integer level;

    @JsonProperty("ability_score_bonuses")
    private Integer abilityScoreBonuses;

    @JsonProperty("prof_bonus")
    private Integer profBonus;

    private List<ApiReferenceDto> features;

    private SpellcastingLevelDto spellcasting;

    @JsonProperty("class_specific")
    private ClassSpecificDto classSpecific;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getAbilityScoreBonuses() {
        return abilityScoreBonuses;
    }

    public void setAbilityScoreBonuses(Integer abilityScoreBonuses) {
        this.abilityScoreBonuses = abilityScoreBonuses;
    }

    public Integer getProfBonus() {
        return profBonus;
    }

    public void setProfBonus(Integer profBonus) {
        this.profBonus = profBonus;
    }

    public List<ApiReferenceDto> getFeatures() {
        return features;
    }

    public void setFeatures(List<ApiReferenceDto> features) {
        this.features = features;
    }

    public SpellcastingLevelDto getSpellcasting() {
        return spellcasting;
    }

    public void setSpellcasting(SpellcastingLevelDto spellcasting) {
        this.spellcasting = spellcasting;
    }

    public ClassSpecificDto getClassSpecific() {
        return classSpecific;
    }

    public void setClassSpecific(ClassSpecificDto classSpecific) {
        this.classSpecific = classSpecific;
    }

}