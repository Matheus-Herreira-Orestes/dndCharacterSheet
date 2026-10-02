package com.matheusorestes.dndCharacterSheet.integration.dto;

import java.util.List;

public class DndApiListDto {
    private List<ApiReferenceDto> results;

    public List<ApiReferenceDto> getResults() { return results; }
    public void setResults(List<ApiReferenceDto> results) { this.results = results; }
}
