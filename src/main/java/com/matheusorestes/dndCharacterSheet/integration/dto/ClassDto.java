package com.matheusorestes.dndCharacterSheet.integration.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassDto {

    private String index;
    private String name;

    @JsonProperty("hit_die")
    private Integer hitDie;

    private SpellcastingDto spellcasting;

    @JsonProperty("starting_equipment")
    private List<StartingEquipmentDto> startingEquipment;

    @JsonProperty("proficiency_choices")
    private List<ProficiencyChoiceDto> proficiencyChoices;

    private List<ApiReferenceDto> proficiencies;

    @JsonProperty("saving_throws")
    private List<ApiReferenceDto> savingThrows;

    private List<ApiReferenceDto> subclasses;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHitDie() {
        return hitDie;
    }

    public void setHitDie(Integer hitDie) {
        this.hitDie = hitDie;
    }

    public SpellcastingDto getSpellcasting() {
        return spellcasting;
    }

    public void setSpellcasting(SpellcastingDto spellcasting) {
        this.spellcasting = spellcasting;
    }

    public List<StartingEquipmentDto> getStartingEquipment() {
        return startingEquipment;
    }

    public void setStartingEquipment(List<StartingEquipmentDto> startingEquipment) {
        this.startingEquipment = startingEquipment;
    }

    public List<ProficiencyChoiceDto> getProficiencyChoices() {
        return proficiencyChoices;
    }

    public void setProficiencyChoices(List<ProficiencyChoiceDto> proficiencyChoices) {
        this.proficiencyChoices = proficiencyChoices;
    }

    public List<ApiReferenceDto> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(List<ApiReferenceDto> proficiencies) {
        this.proficiencies = proficiencies;
    }

    public List<ApiReferenceDto> getSavingThrows() {
        return savingThrows;
    }

    public void setSavingThrows(List<ApiReferenceDto> savingThrows) {
        this.savingThrows = savingThrows;
    }

    public List<ApiReferenceDto> getSubclasses() {
        return subclasses;
    }

    public void setSubclasses(List<ApiReferenceDto> subclasses) {
        this.subclasses = subclasses;
    }

}