package com.matheusorestes.dndCharacterSheet.integration.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpellDto {

    private String index;
    private String name;
    private int level;
    private List<String> desc;
    private String range;
    private List<String> components;
    private String material;
    private boolean ritual;
    private String duration;
    private boolean concentration;
    @JsonProperty("casting_time")
    private String castingTime;
    private ApiReferenceDto school;

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
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public List<String> getDesc() {
        return desc;
    }
    public void setDesc(List<String> desc) {
        this.desc = desc;
    }
    public String getRange() { return range; }
    public void setRange(String range) { this.range = range; }
    public List<String> getComponents() { return components; }
    public void setComponents(List<String> components) { this.components = components; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public boolean isRitual() { return ritual; }
    public void setRitual(boolean ritual) { this.ritual = ritual; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public boolean isConcentration() { return concentration; }
    public void setConcentration(boolean concentration) { this.concentration = concentration; }
    public String getCastingTime() { return castingTime; }
    public void setCastingTime(String castingTime) { this.castingTime = castingTime; }
    public ApiReferenceDto getSchool() { return school; }
    public void setSchool(ApiReferenceDto school) { this.school = school; }

}
