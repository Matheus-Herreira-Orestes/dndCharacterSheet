package com.matheusorestes.dndCharacterSheet.integration.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.matheusorestes.dndCharacterSheet.integration.dto.ClassDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.ClassLevelDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.SpellDto;

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