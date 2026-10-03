package com.matheusorestes.dndCharacterSheet.domain.characterclass;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;
import com.matheusorestes.dndCharacterSheet.integration.dto.ClassDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.SubclassDto;
import com.matheusorestes.dndCharacterSheet.integration.service.DndApiService;

/** Seeds local official classes and their subclasses after Hibernate creates the tables. */
@Component
public class OfficialClassCatalogInitializer implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(OfficialClassCatalogInitializer.class);

    private final DndApiService dndApiService;
    private final CharacterClassRepository characterClassRepository;
    private final SubclassRepository subclassRepository;

    public OfficialClassCatalogInitializer(DndApiService dndApiService,
            CharacterClassRepository characterClassRepository, SubclassRepository subclassRepository) {
        this.dndApiService = dndApiService;
        this.characterClassRepository = characterClassRepository;
        this.subclassRepository = subclassRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (characterClassRepository.existsBySource(CatalogSource.OFFICIAL)) {
            return;
        }

        try {
            List<ClassDto> classes = dndApiService.getClassIndexes().stream()
                    .map(dndApiService::getClass)
                    .toList();

            classes.stream()
                    .map(this::toOfficialClass)
                    .forEach(characterClassRepository::save);

            int subclasses = classes.stream()
                    .mapToInt(this::importSubclasses)
                    .sum();
            log.info("Imported {} official classes and {} official subclasses into the local catalog",
                    classes.size(), subclasses);
        } catch (RuntimeException exception) {
            log.warn("Could not seed the official class catalog; it will be retried on the next startup", exception);
        }
    }

    private CharacterClass toOfficialClass(ClassDto dto) {
        String spellcastingAbility = dto.getSpellcasting() == null
                || dto.getSpellcasting().getSpellcastingAbility() == null
                ? null
                : dto.getSpellcasting().getSpellcastingAbility().getName();
        return CharacterClass.official(dto.getIndex(), dto.getName(), dto.getHitDie(), spellcastingAbility);
    }

    private int importSubclasses(ClassDto dto) {
        if (dto.getSubclasses() == null) {
            return 0;
        }

        CharacterClass characterClass = characterClassRepository.findByExternalIndex(dto.getIndex())
                .orElseThrow(() -> new IllegalStateException("Imported class was not found: " + dto.getIndex()));
        return dto.getSubclasses().stream()
                .map(reference -> dndApiService.getSubclass(reference.getIndex()))
                .map(subclass -> toOfficialSubclass(subclass, characterClass))
                .map(subclassRepository::save)
                .toList()
                .size();
    }

    private Subclass toOfficialSubclass(SubclassDto dto, CharacterClass characterClass) {
        return Subclass.official(dto.getIndex(), dto.getName(), dto.getDesc(), dto.getSubclassFlavor(), characterClass);
    }
}
