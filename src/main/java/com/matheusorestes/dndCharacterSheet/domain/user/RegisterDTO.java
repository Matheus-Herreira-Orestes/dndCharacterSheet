package com.matheusorestes.dndCharacterSheet.domain.user;

public record RegisterDTO(String Login, String Password, UserRole Role) {
    
}
