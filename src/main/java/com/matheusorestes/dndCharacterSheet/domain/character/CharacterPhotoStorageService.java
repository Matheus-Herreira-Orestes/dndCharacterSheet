package com.matheusorestes.dndCharacterSheet.domain.character;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CharacterPhotoStorageService {
    private static final long MAX_PHOTO_SIZE_BYTES = 5 * 1024 * 1024;
    private static final Map<String, String> EXTENSIONS = Map.of(
        "image/jpeg", ".jpg",
        "image/png", ".png"
    );

    private final Path storageDirectory;

    public CharacterPhotoStorageService(
            @Value("${app.storage.character-photos-dir}") String storageDirectory
    ) {
        this.storageDirectory = Path.of(storageDirectory).toAbsolutePath().normalize();
    }

    public String store(String characterId, MultipartFile photo) {
        if (photo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A photo is required.");
        }
        if (photo.getSize() > MAX_PHOTO_SIZE_BYTES) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Photo must be 5 MB or smaller.");
        }

        String contentType = photo.getContentType();
        String extension = EXTENSIONS.get(contentType);
        if (extension == null || !isDecodableImage(photo)) {
            throw new ResponseStatusException(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "Only valid JPEG and PNG images are supported."
            );
        }

        String key = characterId + "/" + UUID.randomUUID() + extension;
        Path target = storageDirectory.resolve(key).normalize();
        if (!target.startsWith(storageDirectory)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid photo path.");
        }

        try {
            Files.createDirectories(target.getParent());
            try (var input = photo.getInputStream()) {
                Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING);
            }
            return key;
        } catch (IOException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not store photo.", exception);
        }
    }

    public Resource load(String key) {
        Path photo = storageDirectory.resolve(key).normalize();
        if (!photo.startsWith(storageDirectory) || !Files.isRegularFile(photo)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found.");
        }
        return new FileSystemResource(photo);
    }

    public void delete(String key) {
        if (key == null) {
            return;
        }
        Path photo = storageDirectory.resolve(key).normalize();
        if (photo.startsWith(storageDirectory)) {
            try {
                Files.deleteIfExists(photo);
            } catch (IOException exception) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete previous photo.", exception);
            }
        }
    }

    private boolean isDecodableImage(MultipartFile photo) {
        try (var input = photo.getInputStream()) {
            return ImageIO.read(input) != null;
        } catch (IOException exception) {
            return false;
        }
    }
}
