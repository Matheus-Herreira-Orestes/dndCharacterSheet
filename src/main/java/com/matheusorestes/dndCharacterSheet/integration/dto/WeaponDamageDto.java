package com.matheusorestes.dndCharacterSheet.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeaponDamageDto {
    @JsonProperty("damage_dice")
    private String damageDice;
    @JsonProperty("damage_type")
    private ApiReferenceDto damageType;

    public String getDamageDice() { return damageDice; }
    public void setDamageDice(String damageDice) { this.damageDice = damageDice; }
    public ApiReferenceDto getDamageType() { return damageType; }
    public void setDamageType(ApiReferenceDto damageType) { this.damageType = damageType; }
}
