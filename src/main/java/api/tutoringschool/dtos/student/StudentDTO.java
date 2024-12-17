package api.tutoringschool.dtos.student;

import java.util.Date;
import java.util.UUID;

public record StudentDTO(UUID userId, String name, Date birthdate, String grade) {
}
