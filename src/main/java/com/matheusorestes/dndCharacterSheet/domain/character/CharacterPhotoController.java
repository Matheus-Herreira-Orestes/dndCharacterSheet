package com.matheusorestes.dndCharacterSheet.domain.character;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.matheusorestes.dndCharacterSheet.domain.user.User;

@RestController
@RequestMapping("/characters")
public class CharacterPhotoController {
    private final CharacterPhotoService characterPhotoService;
    private final CharacterPhotoStorageService photoStorageService;

    public CharacterPhotoController(
            CharacterPhotoService characterPhotoService,
            CharacterPhotoStorageService photoStorageService
    ) {
        this.characterPhotoService = characterPhotoService;
        this.photoStorageService = photoStorageService;
    }

    @PostMapping(value = "/{characterId}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CharacterPhotoResponse uploadPhoto(
            @PathVariable String characterId,
            @RequestParam("photo") MultipartFile photo,
            @AuthenticationPrincipal User currentUser
    ) {
        return characterPhotoService.upload(characterId, photo, currentUser);
    }

    @GetMapping("/{characterId}/photo")
    public ResponseEntity<Resource> downloadPhoto(
            @PathVariable String characterId,
            @AuthenticationPrincipal User currentUser
    ) {
        Character character = characterPhotoService.findOwnedCharacter(characterId, currentUser);
        if (character.getCharacterPhotoKey() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character has no photo.");
        }
        Resource photo = photoStorageService.load(character.getCharacterPhotoKey());
        MediaType contentType = MediaTypeFactory.getMediaType(photo)
            .orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok().contentType(contentType).body(photo);
    }
}
