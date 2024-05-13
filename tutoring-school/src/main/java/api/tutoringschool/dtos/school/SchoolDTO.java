package api.tutoringschool.dtos.school;

import java.util.UUID;

public record SchoolDTO(UUID tutorId, String name, String description, String phone, String email, String profileImage,
        String cep, String address, String addressNumber, String district, String city, String state) {
}
