package com.matheusorestes.dndCharacterSheet.domain.weapon;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;
import com.matheusorestes.dndCharacterSheet.domain.damage.DamageType;
import com.matheusorestes.dndCharacterSheet.domain.dice.DiceTerm;
import com.matheusorestes.dndCharacterSheet.domain.dice.DiceType;
import com.matheusorestes.dndCharacterSheet.integration.dto.WeaponDamageDto;
import com.matheusorestes.dndCharacterSheet.integration.dto.WeaponDto;
import com.matheusorestes.dndCharacterSheet.integration.service.DndApiService;

/** Seeds the local weapon catalog once, after Hibernate has created its tables. */
@Component
public class OfficialWeaponCatalogInitializer implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(OfficialWeaponCatalogInitializer.class);
    private static final Pattern DICE_PATTERN = Pattern.compile("(\\d+)d(4|6|8|10|12)");

    private final DndApiService dndApiService;
    private final WeaponRepository weaponRepository;

    public OfficialWeaponCatalogInitializer(DndApiService dndApiService, WeaponRepository weaponRepository) {
        this.dndApiService = dndApiService;
        this.weaponRepository = weaponRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (weaponRepository.existsBySource(CatalogSource.OFFICIAL)) {
            return;
        }

        try {
            int imported = dndApiService.getWeaponIndexes().stream()
                    .map(dndApiService::getWeapon)
                    .map(this::toOfficialWeapon)
                    .map(weaponRepository::save)
                    .toList()
                    .size();
            log.info("Imported {} official weapons into the local catalog", imported);
        } catch (RuntimeException exception) {
            log.warn("Could not seed the official weapon catalog; it will be retried on the next startup", exception);
        }
    }

    private Weapon toOfficialWeapon(WeaponDto dto) {
        List<String> properties = dto.getProperties() == null ? List.of()
                : dto.getProperties().stream().map(property -> property.getName()).toList();
        return Weapon.official(dto.getIndex(), dto.getName(), properties, toDiceTerms(dto.getDamage()));
    }

    private List<DiceTerm> toDiceTerms(WeaponDamageDto damage) {
        if (damage == null || damage.getDamageDice() == null || damage.getDamageType() == null) {
            return List.of();
        }

        Matcher matcher = DICE_PATTERN.matcher(damage.getDamageDice());
        if (!matcher.matches()) {
            log.warn("Skipping unsupported weapon damage expression: {}", damage.getDamageDice());
            return List.of();
        }

        DiceType diceType = DiceType.valueOf("D" + matcher.group(2));
        DamageType damageType = DamageType.valueOf(
                damage.getDamageType().getName().toUpperCase(Locale.ROOT).replace(' ', '_'));
        return List.of(new DiceTerm(Integer.parseInt(matcher.group(1)), diceType, damageType));
    }
}
