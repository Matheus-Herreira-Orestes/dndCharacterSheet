package com.matheusorestes.dndCharacterSheet.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubclassDto {
    private String index;
    private String name;
    private String desc;
    @JsonProperty("subclass_flavor")
    private String subclassFlavor;

    public String getIndex() { return index; }
    public void setIndex(String index) { this.index = index; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }
    public String getSubclassFlavor() { return subclassFlavor; }
    public void setSubclassFlavor(String subclassFlavor) { this.subclassFlavor = subclassFlavor; }
}
