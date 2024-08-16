package api.tutoringschool.dtos.user;

import java.util.UUID;

public record ValidatePasswordDTO(UUID id, String currentPassword, String newPassword) {
}
