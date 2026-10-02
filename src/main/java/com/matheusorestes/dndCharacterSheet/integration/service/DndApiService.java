package com.matheusorestes.dndCharacterSheet.integration.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.matheusorestes.dndCharacterSheet.integration.dto.ClassDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.ClassLevelDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.DndApiListDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.EquipmentCategoryDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.SpellDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.WeaponDto;

@Service
public class DndApiService {

    private final WebClient webClient;

    public DndApiService(WebClient dndWebClient) {
        this.webClient = dndWebClient;
    }

    public SpellDto getSpell(String index) {
        return webClient.get()
                .uri("/api/2014/spells/{index}", index)
                .retrieve()
                .bodyToMono(SpellDto.class)
                .block();
    }

    public List<String> getSpellIndexes() {
        DndApiListDto response = webClient.get()
                .uri("/api/2014/spells")
                .retrieve()
                .bodyToMono(DndApiListDto.class)
                .block();

        if (response == null || response.getResults() == null) {
            return List.of();
        }

        return response.getResults().stream()
                .map(reference -> reference.getIndex())
                .toList();
    }

    public List<String> getWeaponIndexes() {
        EquipmentCategoryDto response = webClient.get()
                .uri("/api/2014/equipment-categories/weapon")
                .retrieve()
                .bodyToMono(EquipmentCategoryDto.class)
                .block();

        if (response == null || response.getEquipment() == null) {
            return List.of();
        }

        return response.getEquipment().stream()
                .map(reference -> reference.getIndex())
                .toList();
    }

    public WeaponDto getWeapon(String index) {
        return webClient.get()
                .uri("/api/2014/equipment/{index}", index)
                .retrieve()
                .bodyToMono(WeaponDto.class)
                .block();
    }

    public ClassDto getClass(String index) {
        return webClient.get()
                .uri("/api/2014/classes/{index}", index)
                .retrieve()
                .bodyToMono(ClassDto.class)
                .block();
    }

    public List<ClassLevelDto> getClassLevels(String index) {
        return webClient.get()
                .uri("/api/2014/classes/{index}/levels", index)
                .retrieve()
                .bodyToFlux(ClassLevelDto.class)
                .collectList()
                .block();
    }
}
