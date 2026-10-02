package com.matheusorestes.dndCharacterSheet.domain.spell;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;
import com.matheusorestes.dndCharacterSheet.domain.rules.MagicSchools;
import com.matheusorestes.dndCharacterSheet.integration.dto.SpellDto;
import com.matheusorestes.dndCharacterSheet.integration.service.DndApiService;

/** Seeds the local spell catalog once, after Hibernate has created its tables. */
@Component
public class OfficialSpellCatalogInitializer implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(OfficialSpellCatalogInitializer.class);

    private final DndApiService dndApiService;
    private final SpellRepository spellRepository;

    public OfficialSpellCatalogInitializer(DndApiService dndApiService, SpellRepository spellRepository) {
        this.dndApiService = dndApiService;
        this.spellRepository = spellRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (spellRepository.existsBySource(CatalogSource.OFFICIAL)) {
            return;
        }

        try {
            int imported = dndApiService.getSpellIndexes().stream()
                    .map(dndApiService::getSpell)
                    .map(this::toOfficialSpell)
                    .map(spellRepository::save)
                    .toList()
                    .size();
            log.info("Imported {} official spells into the local catalog", imported);
        } catch (RuntimeException exception) {
            // The app remains usable for homebrew if the third-party catalog is unavailable.
            log.warn("Could not seed the official spell catalog; it will be retried on the next startup", exception);
        }
    }

    private Spell toOfficialSpell(SpellDto dto) {
        MagicSchools school = dto.getSchool() == null ? null
                : MagicSchools.valueOf(dto.getSchool().getName().toUpperCase(Locale.ROOT));
        String description = dto.getDesc() == null ? null : String.join("\n\n", dto.getDesc());

        return Spell.official(dto.getIndex(), dto.getName(), description, dto.getLevel(), school,
                dto.getCastingTime(), dto.getRange(), dto.getDuration(), dto.getMaterial(), dto.isRitual(),
                dto.isConcentration(), dto.getComponents());
    }
}
