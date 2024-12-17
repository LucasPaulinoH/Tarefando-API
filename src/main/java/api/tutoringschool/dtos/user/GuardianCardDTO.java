package api.tutoringschool.dtos.user;

import java.util.UUID;

public record GuardianCardDTO(UUID guardianId, String profileImage, String name) {
}
