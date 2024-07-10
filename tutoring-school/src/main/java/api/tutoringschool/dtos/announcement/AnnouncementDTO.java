package api.tutoringschool.dtos.announcement;

import java.util.UUID;

public record AnnouncementDTO(UUID userId, UUID[] receiverIds, String title, String description, String[] images) {
}
