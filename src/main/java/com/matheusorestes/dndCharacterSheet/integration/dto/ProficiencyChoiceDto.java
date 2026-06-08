package com.matheusorestes.dndCharacterSheet.integration.dto;

public class ProficiencyChoiceDto {

    private String desc;
    private Integer choose;
    private String type;

    private ApiReferenceListDto from;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getChoose() {
        return choose;
    }

    public void setChoose(Integer choose) {
        this.choose = choose;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ApiReferenceListDto getFrom() {
        return from;
    }

    public void setFrom(ApiReferenceListDto from) {
        this.from = from;
    }

}