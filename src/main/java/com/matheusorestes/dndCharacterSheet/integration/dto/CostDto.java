package com.matheusorestes.dndCharacterSheet.integration.dto;

import java.math.BigDecimal;

/** Monetary value returned by the D&D 5e API. */
public class CostDto {
    private BigDecimal quantity;
    private String unit;

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
