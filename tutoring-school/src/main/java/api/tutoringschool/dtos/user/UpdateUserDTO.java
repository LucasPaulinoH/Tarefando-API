package api.tutoringschool.dtos.user;

import api.tutoringschool.types.UserRole;

public record UpdateUserDTO(
        String name,
        String email,
        String phone,
        UserRole role) {
}
