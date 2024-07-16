package api.tutoringschool.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api.tutoringschool.model.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {
      List<Announcement> findByUserId(UUID tutorId);

      @Query(value = "SELECT * FROM announcements a WHERE :receiverId = ANY (a.receiver_ids)", nativeQuery = true)
      List<Announcement> findReceiverAnnouncements(@Param("receiverId") UUID receiverId);
}
