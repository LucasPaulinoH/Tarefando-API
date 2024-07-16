package api.tutoringschool.dtos.announcement;

import java.util.UUID;

public record ReceivedAnnouncementDTO(String title, String description, String[] images, UUID authorId) {
}