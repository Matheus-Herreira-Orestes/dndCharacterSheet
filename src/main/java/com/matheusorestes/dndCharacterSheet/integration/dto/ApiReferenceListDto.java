package com.matheusorestes.dndCharacterSheet.integration.dto;

import java.util.List;

public class ApiReferenceListDto {

    private String optionType;

    private List<ApiReferenceDto> options;

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public List<ApiReferenceDto> getOptions() {
        return options;
    }

    public void setOptions(List<ApiReferenceDto> options) {
        this.options = options;
    }

}