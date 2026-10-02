package com.matheusorestes.dndCharacterSheet.integration.dto;

import java.util.List;

public class EquipmentCategoryDto {
    private List<ApiReferenceDto> equipment;

    public List<ApiReferenceDto> getEquipment() { return equipment; }
    public void setEquipment(List<ApiReferenceDto> equipment) { this.equipment = equipment; }
}
