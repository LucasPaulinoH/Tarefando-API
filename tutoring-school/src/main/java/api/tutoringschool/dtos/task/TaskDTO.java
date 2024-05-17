package api.tutoringschool.dtos.task;

import java.util.Date;
import java.util.UUID;

import api.tutoringschool.types.TaskStatus;

public record TaskDTO(UUID subjectId,
        String title,
        String description, String[] images,
        Date deadlineDate,
        TaskStatus status) {
}
