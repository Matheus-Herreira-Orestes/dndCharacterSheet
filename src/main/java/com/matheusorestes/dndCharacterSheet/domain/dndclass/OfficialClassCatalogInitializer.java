package com.matheusorestes.dndCharacterSheet.domain.dndclass;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;
import com.matheusorestes.dndCharacterSheet.domain.subclass.SubClass;
import com.matheusorestes.dndCharacterSheet.domain.subclass.SubClassRepository;
import com.matheusorestes.dndCharacterSheet.integration.dto.ClassDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.ClassLevelDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.SpellcastingLevelDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.SubclassDto;
import com.matheusorestes.dndCharacterSheet.integration.service.DndApiService;

/** Seeds local official classes and their subclasses after Hibernate creates the tables. */
@Component
public class OfficialClassCatalogInitializer implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(OfficialClassCatalogInitializer.class);

    private final DndApiService dndApiService;
    private final DndClassRepository classRepository;
    private final SubClassRepository subClassRepository;
    private final ClassLevelRepository classLevelRepository;
    private final ClassLevelStatDefinitionRepository statDefinitionRepository;
    private final ClassLevelStatValueRepository statValueRepository;
    private final ObjectMapper objectMapper;

    public OfficialClassCatalogInitializer(DndApiService dndApiService,
            DndClassRepository classRepository, SubClassRepository subClassRepository,
            ClassLevelRepository classLevelRepository,
            ClassLevelStatDefinitionRepository statDefinitionRepository,
            ClassLevelStatValueRepository statValueRepository, ObjectMapper objectMapper) {
        this.dndApiService = dndApiService;
        this.classRepository = classRepository;
        this.subClassRepository = subClassRepository;
        this.classLevelRepository = classLevelRepository;
        this.statDefinitionRepository = statDefinitionRepository;
        this.statValueRepository = statValueRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (classRepository.existsBySource(CatalogSource.OFFICIAL)) {
            importMissingOfficialLevels();
            return;
        }

        try {
            List<ClassDto> classes = dndApiService.getClassIndexes().stream()
                    .map(dndApiService::getClass)
                    .toList();

            classes.stream()
                    .map(this::toOfficialClass)
                    .forEach(classRepository::save);

            int subclasses = classes.stream()
                    .mapToInt(this::importSubclasses)
                    .sum();
            int levels = classes.stream().mapToInt(this::importLevels).sum();
            log.info("Imported {} official classes, {} subclasses and {} class levels into the local catalog",
                    classes.size(), subclasses, levels);
        } catch (RuntimeException exception) {
            log.warn("Could not seed the official class catalog; it will be retried on the next startup", exception);
        }
    }

    private void importMissingOfficialLevels() {
        try {
            int levels = dndApiService.getClassIndexes().stream()
                    .map(dndApiService::getClass)
                    .mapToInt(this::importLevels)
                    .sum();
            if (levels > 0) {
                log.info("Imported {} missing official class levels into the local catalog", levels);
            }
        } catch (RuntimeException exception) {
            log.warn("Could not seed missing official class levels; it will be retried on the next startup", exception);
        }
    }

    private DndClass toOfficialClass(ClassDto dto) {
        String spellcastingAbility = dto.getSpellcasting() == null
                || dto.getSpellcasting().getSpellcastingAbility() == null
                ? null
                : dto.getSpellcasting().getSpellcastingAbility().getName();
        return DndClass.official(dto.getIndex(), dto.getName(), dto.getHitDie(), spellcastingAbility);
    }

    private int importSubclasses(ClassDto dto) {
        if (dto.getSubclasses() == null) {
            return 0;
        }

        DndClass characterClass = classRepository.findByExternalIndex(dto.getIndex())
                .orElseThrow(() -> new IllegalStateException("Imported class was not found: " + dto.getIndex()));
        return dto.getSubclasses().stream()
                .map(reference -> dndApiService.getSubclass(reference.getIndex()))
                .map(subclass -> toOfficialSubClass(subclass, characterClass))
                .map(subClassRepository::save)
                .toList()
                .size();
    }

    private SubClass toOfficialSubClass(SubclassDto dto, DndClass characterClass) {
        return SubClass.official(dto.getIndex(), dto.getName(), dto.getDesc(), dto.getSubclassFlavor(), characterClass);
    }

    private int importLevels(ClassDto dto) {
        DndClass characterClass = classRepository.findByExternalIndex(dto.getIndex())
                .orElseThrow(() -> new IllegalStateException("Imported class was not found: " + dto.getIndex()));
        if (classLevelRepository.existsByCharacterClass(characterClass)) {
            return 0;
        }

        List<ClassLevelDto> levels = dndApiService.getClassLevels(dto.getIndex());
        if (levels == null) {
            return 0;
        }
        for (ClassLevelDto dtoLevel : levels) {
            if (dtoLevel.getLevel() == null || dtoLevel.getProfBonus() == null) {
                continue;
            }
            List<String> features = dtoLevel.getFeatures() == null ? List.of() : dtoLevel.getFeatures().stream()
                    .map(feature -> feature.getName() == null ? feature.getIndex() : feature.getName())
                    .toList();
            ClassLevel level = classLevelRepository.save(new ClassLevel(characterClass, dtoLevel.getLevel(),
                    dtoLevel.getProfBonus(), features));
            progressionValues(dtoLevel).forEach((key, value) -> saveStatValue(characterClass, level, key, value));
        }
        return levels.size();
    }

    private Map<String, Object> progressionValues(ClassLevelDto level) {
        Map<String, Object> values = new LinkedHashMap<>();
        if (level.getClassSpecific() != null && level.getClassSpecific().getData() != null) {
            values.putAll(level.getClassSpecific().getData());
        }
        SpellcastingLevelDto spellcasting = level.getSpellcasting();
        if (spellcasting != null) {
            putWhenPresent(values, "cantrips_known", spellcasting.getCantripsKnown());
            putWhenPresent(values, "spells_known", spellcasting.getSpellsKnown());
            putWhenPresent(values, "spell_slots_level_1", spellcasting.getSpellSlotsLevel1());
            putWhenPresent(values, "spell_slots_level_2", spellcasting.getSpellSlotsLevel2());
            putWhenPresent(values, "spell_slots_level_3", spellcasting.getSpellSlotsLevel3());
            putWhenPresent(values, "spell_slots_level_4", spellcasting.getSpellSlotsLevel4());
            putWhenPresent(values, "spell_slots_level_5", spellcasting.getSpellSlotsLevel5());
            putWhenPresent(values, "spell_slots_level_6", spellcasting.getSpellSlotsLevel6());
            putWhenPresent(values, "spell_slots_level_7", spellcasting.getSpellSlotsLevel7());
            putWhenPresent(values, "spell_slots_level_8", spellcasting.getSpellSlotsLevel8());
            putWhenPresent(values, "spell_slots_level_9", spellcasting.getSpellSlotsLevel9());
        }
        return values;
    }

    private void putWhenPresent(Map<String, Object> values, String key, Object value) {
        if (value != null) {
            values.put(key, value);
        }
    }

    private void saveStatValue(DndClass characterClass, ClassLevel level, String key, Object rawValue) {
        ClassLevelStatDataType type = dataTypeFor(rawValue);
        ClassLevelStatDefinition definition = statDefinitionRepository.findByCharacterClassAndKey(characterClass, key)
                .orElseGet(() -> statDefinitionRepository.save(new ClassLevelStatDefinition(characterClass, key,
                        displayNameFor(key), type, (int) statDefinitionRepository.count())));
        statValueRepository.save(new ClassLevelStatValue(level, definition, serialize(rawValue, type)));
    }

    private ClassLevelStatDataType dataTypeFor(Object value) {
        if (value instanceof Number) return ClassLevelStatDataType.INTEGER;
        if (value instanceof Boolean) return ClassLevelStatDataType.BOOLEAN;
        if (value instanceof String) return ClassLevelStatDataType.TEXT;
        return ClassLevelStatDataType.JSON;
    }

    private String serialize(Object value, ClassLevelStatDataType type) {
        if (type != ClassLevelStatDataType.JSON) return String.valueOf(value);
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Could not store class progression value", exception);
        }
    }

    private String displayNameFor(String key) {
        String[] words = key.replace('_', ' ').split(" ");
        StringBuilder label = new StringBuilder();
        for (String word : words) {
            if (!label.isEmpty()) label.append(' ');
            label.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return label.toString();
    }
}
