package com.matheusorestes.dndCharacterSheet.integration.dto;


public class StartingEquipmentDto {

    private Integer quantity;

    private ApiReferenceDto equipment;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ApiReferenceDto getEquipment() {
        return equipment;
    }

    public void setEquipment(ApiReferenceDto equipment) {
        this.equipment = equipment;
    }

    
}