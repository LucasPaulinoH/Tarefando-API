package api.tutoringschool.dtos;

import api.tutoringschool.types.UserRole;

public record RegisterDTO(String email, UserRole role, String password) {}
