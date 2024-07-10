package api.tutoringschool.dtos.common;

import java.util.UUID;

public record MultipleImagesUpdateDTO(UUID id, String[] urls) {
}
