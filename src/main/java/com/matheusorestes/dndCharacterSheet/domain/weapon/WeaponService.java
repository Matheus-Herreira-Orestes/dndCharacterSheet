package com.matheusorestes.dndCharacterSheet.domain.weapon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.matheusorestes.dndCharacterSheet.domain.dice.DiceTerm;
import com.matheusorestes.dndCharacterSheet.domain.user.User;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@Service 
@Transactional(readOnly = true)
public class WeaponService {

    @Autowired
    WeaponRepository weaponRepository;

    @Transactional
    public WeaponResponseDTO create(CreateWeaponDTO dto, User currentUser) {
        List<DiceTerm> weaponDamage = dto.weaponDamage().stream()
            .map(diceTermDTO -> new DiceTerm(
                diceTermDTO.quantity(),
                diceTermDTO.diceType(),
                diceTermDTO.damageType()
            ))
            .toList();

        Weapon weapon = new Weapon(
            dto.name(),
            currentUser,
            dto.isPublic(),
            dto.properties(),
            dto.proficiencies(),
            weaponDamage
        );

        return WeaponResponseDTO.from(weaponRepository.save(weapon));
    }
    
    public List<WeaponResponseDTO> findPublicOrCreatedBy(User user) {
        return weaponRepository.findByIspublicTrueOrCreatedById(user.getId())
            .stream()
            .map(WeaponResponseDTO::from)
            .toList();
    }

    public List<WeaponResponseDTO> findAll() {
        return weaponRepository.findAll()
            .stream()
            .map(WeaponResponseDTO::from)
            .toList();
    }

    public WeaponResponseDTO findAvailableById(String weaponId, User user) {
        Weapon weapon = weaponRepository.findById(weaponId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean isCreator = weapon.getCreatedBy() != null
            && weapon.getCreatedBy().getId().equals(user.getId());

        if (!weapon.isIspublic() && !isCreator) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return WeaponResponseDTO.from(weapon);
    }
}
