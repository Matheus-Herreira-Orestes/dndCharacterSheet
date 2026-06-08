package com.matheusorestes.dndCharacterSheet.integration.dto;

import java.util.List;

public class SpellcastingInfoDto {

    private String name;
    private List<String> desc;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getDesc() {
        return desc;
    }
    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    

}