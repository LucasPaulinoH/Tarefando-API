package api.tutoringschool.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.tutoringschool.dtos.announcement.AnnouncementDTO;
import api.tutoringschool.dtos.common.MultipleImagesUpdateDTO;
import api.tutoringschool.model.Announcement;
import api.tutoringschool.services.AnnouncementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/announcements")
@Tag(name = "Announcements")
public class AnnouncementController {
    @Autowired
    AnnouncementService service;

    @PostMapping
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody @Valid AnnouncementDTO announcementDTO)
            throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAnnouncement(announcementDTO));
    }

    @GetMapping
    public ResponseEntity<List<Announcement>> getAllAnnouncements() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllAnnouncements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAnnouncement(@PathVariable(value = "id") UUID id) {
        return service.getAnnouncement(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Announcement>> getAnnouncementsFromTutor(@RequestParam("tutorId") String tutorId) {
        UUID tutorUUID = UUID.fromString(tutorId);
        return ResponseEntity.status(HttpStatus.OK).body(service.getAnnouncementsFromUser(tutorUUID));
    }

    @GetMapping("/my")
    public ResponseEntity<Object> getReceiverAnnouncements(@RequestParam("receiverId") String receiverId)
            throws BadRequestException {
        UUID receiverUUID = UUID.fromString(receiverId);
        ResponseEntity<Object> response = service.getReceiverAnnouncements(receiverUUID);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAnnouncement(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid AnnouncementDTO announcementDTO) throws BadRequestException {
        return service.updateAnnouncement(id, announcementDTO);
    }

    @PatchMapping("/images")
    public ResponseEntity<Object> updateTaskImages(
            @RequestBody @Valid MultipleImagesUpdateDTO multipleImagesUpdateDTO) {
        return service.updateAnnouncementImages(multipleImagesUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnnouncement(@PathVariable(value = "id") UUID id) {
        return service.deleteAnnouncement(id);
    }
}
