package com.matheusorestes.dndCharacterSheet.domain.character;

public record CharacterPhotoResponse(String characterId, String photoUrl) {
    public static CharacterPhotoResponse from(Character character) {
        String photoUrl = character.getCharacterPhotoKey() == null
            ? null
            : "/characters/" + character.getId() + "/photo";
        return new CharacterPhotoResponse(character.getId(), photoUrl);
    }
}
