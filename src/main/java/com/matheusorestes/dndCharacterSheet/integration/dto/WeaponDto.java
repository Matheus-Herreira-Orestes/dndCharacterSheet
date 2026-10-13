package com.matheusorestes.dndCharacterSheet.integration.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeaponDto {
    private String index;
    private String name;
    private BigDecimal weight;
    private CostDto cost;
    private WeaponDamageDto damage;
    private List<ApiReferenceDto> properties;

    @JsonProperty("weapon_category")
    private String weaponCategory;

    public String getIndex() { return index; }
    public void setIndex(String index) { this.index = index; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
    public CostDto getCost() { return cost; }
    public void setCost(CostDto cost) { this.cost = cost; }
    public WeaponDamageDto getDamage() { return damage; }
    public void setDamage(WeaponDamageDto damage) { this.damage = damage; }
    public List<ApiReferenceDto> getProperties() { return properties; }
    public void setProperties(List<ApiReferenceDto> properties) { this.properties = properties; }
    public String getWeaponCategory() { return weaponCategory; }
    public void setWeaponCategory(String weaponCategory) { this.weaponCategory = weaponCategory; }
}
