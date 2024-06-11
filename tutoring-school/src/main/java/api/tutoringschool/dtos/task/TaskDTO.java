package api.tutoringschool.dtos.task;

import java.util.Date;
import java.util.UUID;

public record TaskDTO(UUID subjectId,
        String title,
        String description, String[] images,
        Date deadlineDate,
        boolean isConcluded) {
}
