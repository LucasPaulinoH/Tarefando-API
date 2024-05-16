package api.tutoringschool.dtos.user;

import api.tutoringschool.types.UserRole;

public record UserDTO(
        String name,
        String email,
        String phone,
        UserRole role,
        String profileImage,
        String password) {}