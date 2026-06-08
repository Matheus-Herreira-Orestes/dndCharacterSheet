package com.matheusorestes.dndCharacterSheet.integration.dto;

import java.util.List;

public class SpellDto {

    private String index;
    private String name;
    private int level;
    private List<String> desc;

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

}