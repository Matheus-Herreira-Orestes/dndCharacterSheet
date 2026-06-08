package com.matheusorestes.dndCharacterSheet.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiReferenceDto {

    private String index;
    private String name;
    private String url;

    @JsonProperty("updated_at")
    private String updatedAt;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}