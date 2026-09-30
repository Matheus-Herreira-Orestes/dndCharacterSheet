package com.matheusorestes.dndCharacterSheet.domain.character;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.matheusorestes.dndCharacterSheet.domain.user.User;

@Service
public class CharacterPhotoService {
    private final CharacterRepository characterRepository;
    private final CharacterPhotoStorageService photoStorageService;

    public CharacterPhotoService(
            CharacterRepository characterRepository,
            CharacterPhotoStorageService photoStorageService
    ) {
        this.characterRepository = characterRepository;
        this.photoStorageService = photoStorageService;
    }

    @Transactional
    public CharacterPhotoResponse upload(String characterId, MultipartFile photo, User currentUser) {
        Character character = findOwnedCharacter(characterId, currentUser);
        String previousKey = character.getCharacterPhotoKey();
        String newKey = photoStorageService.store(characterId, photo);

        character.setCharacterPhotoKey(newKey);
        characterRepository.save(character);

        if (previousKey != null) {
            photoStorageService.delete(previousKey);
        }
        return CharacterPhotoResponse.from(character);
    }

    @Transactional(readOnly = true)
    public Character findOwnedCharacter(String characterId, User currentUser) {
        return characterRepository.findByIdAndCreatedById(characterId, currentUser.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found."));
    }
}
