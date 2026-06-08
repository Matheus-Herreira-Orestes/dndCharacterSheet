package com.matheusorestes.dndCharacterSheet.integration.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusorestes.dndCharacterSheet.integration.dto.ClassDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.ClassLevelDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.SpellDto;
import com.matheusorestes.dndCharacterSheet.integration.service.DndApiService;

@RestController
@RequestMapping("/api/dnd")
public class DndController {

    private final DndApiService service;

    public DndController(DndApiService service) {
        this.service = service;
    }

    @GetMapping("/spells/{index}")
    public SpellDto getSpell(@PathVariable String index) {
        return service.getSpell(index);
    }

    @GetMapping("/classes/{index}")
    public ClassDto getClass(@PathVariable String index) {
        return service.getClass(index);
    }

    @GetMapping("/classes/{index}/levels")
    public List<ClassLevelDto> getClassLevels(@PathVariable String index) {
        return service.getClassLevels(index);
    }
}