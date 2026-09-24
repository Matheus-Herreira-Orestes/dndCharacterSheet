package com.matheusorestes.dndCharacterSheet.domain.weapon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusorestes.dndCharacterSheet.domain.user.User;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/weapon")
public class WeaponController {
    
    @Autowired
    private WeaponService weaponService;

    @PostMapping
    public ResponseEntity<WeaponResponseDTO> create(
            @RequestBody @Valid CreateWeaponDTO dto,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(weaponService.create(dto, currentUser));
    }


    @GetMapping
    public List<WeaponResponseDTO> listAvailableWeapons(
            @AuthenticationPrincipal User currentUser
    ) {
        return weaponService.findPublicOrCreatedBy(currentUser);
    }

    @GetMapping("/all")
    public List<WeaponResponseDTO> listAllWeapons() {
        return weaponService.findAll();
    }

    @GetMapping("/{id}")
    public WeaponResponseDTO getWeaponById(
            @PathVariable String id,
            @AuthenticationPrincipal User currentUser
    ) {
        return weaponService.findAvailableById(id, currentUser);
    }

    


    
}
