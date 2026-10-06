package com.matheusorestes.dndCharacterSheet.integration.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.Map;
import java.util.LinkedHashMap;

public class ClassSpecificDto {

    private Map<String, Object> data = new LinkedHashMap<>();

    @JsonAnySetter
    public void addProperty(String key, Object value) {
        data.put(key, value);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
