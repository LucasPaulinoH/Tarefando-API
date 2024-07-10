package api.tutoringschool.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.tutoringschool.model.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {
}
