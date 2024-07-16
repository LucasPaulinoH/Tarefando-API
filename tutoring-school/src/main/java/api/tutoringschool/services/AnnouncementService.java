package api.tutoringschool.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import api.tutoringschool.dtos.announcement.AnnouncementDTO;
import api.tutoringschool.dtos.announcement.ReceivedAnnouncementDTO;
import api.tutoringschool.dtos.common.MultipleImagesUpdateDTO;
import api.tutoringschool.model.Announcement;
import api.tutoringschool.model.User;
import api.tutoringschool.repositories.AnnouncementRepository;
import api.tutoringschool.repositories.UserRepository;
import api.tutoringschool.types.UserRole;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    public Announcement createAnnouncement(AnnouncementDTO announcementDTO) throws BadRequestException {
        Optional<User> foundedUser = userRepository.findById(announcementDTO.userId());

        if (foundedUser.isEmpty())
            throw new BadRequestException("Given tutorId is not registered.");

        if (foundedUser.get().getRole() != UserRole.TUTOR)
            throw new BadRequestException("Given user id is not from a TUTOR.");

        Announcement newAnnouncement = new Announcement();
        BeanUtils.copyProperties(announcementDTO, newAnnouncement);
        newAnnouncement.setUser(foundedUser.get());

        return announcementRepository.save(newAnnouncement);
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public ResponseEntity<Object> getAnnouncement(UUID id) {
        Optional<Announcement> foundedAnnouncement = announcementRepository.findById(id);

        if (foundedAnnouncement.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Announcement not found.");

        return ResponseEntity.status(HttpStatus.OK).body(foundedAnnouncement.get());
    }

    public List<Announcement> getAnnouncementsFromUser(UUID userId) {
        return announcementRepository.findByUserId(userId);
    }   

    public ResponseEntity<Object> getReceiverAnnouncements(UUID guardianId) throws BadRequestException {
        Optional<User> foundedUser = userRepository.findById(guardianId);

        if (foundedUser.isEmpty())
            throw new BadRequestException("Given guardianId is not registered.");

        var receivedAnnouncements = announcementRepository.findReceiverAnnouncements(guardianId);

        if (receivedAnnouncements.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No announcement was found for this id.");

        var receivedAnnouncementsDtoList = new ArrayList<ReceivedAnnouncementDTO>();

        Announcement iterableDto = null;
        for (int i = 0; i < receivedAnnouncements.size(); i++) {
            iterableDto = receivedAnnouncements.get(i);

            receivedAnnouncementsDtoList.add(new ReceivedAnnouncementDTO(iterableDto.getTitle(),
                    iterableDto.getDescription(), iterableDto.getImages(), iterableDto.getUser().getId()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(receivedAnnouncementsDtoList);
    }

    public ResponseEntity<Object> updateAnnouncement(UUID id, AnnouncementDTO announcementData)
            throws BadRequestException {
        Optional<Announcement> foundedAnnouncement = announcementRepository.findById(id);

        var foundedUser = userRepository.findById(announcementData.userId());

        if (foundedAnnouncement.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Announcement not found.");

        if (foundedUser.isEmpty())
            throw new BadRequestException("Given tutorId is not registered.");

        if (foundedUser.get().getRole() != UserRole.TUTOR)
            throw new BadRequestException("Given user id is not from a TUTOR.");

        Announcement updatedAnnouncement = foundedAnnouncement.get();
        BeanUtils.copyProperties(announcementData, updatedAnnouncement);

        return ResponseEntity.status(HttpStatus.OK).body(announcementRepository.save(updatedAnnouncement));
    }

    public ResponseEntity<Object> updateAnnouncementImages(MultipleImagesUpdateDTO multipleImagesUpdateDTO) {
        Optional<Announcement> foundedAnnouncement = announcementRepository.findById(multipleImagesUpdateDTO.id());

        if (foundedAnnouncement.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Announcement not found.");

        var updatedAnnouncement = foundedAnnouncement.get();
        updatedAnnouncement.setImages(multipleImagesUpdateDTO.urls());
        announcementRepository.save(updatedAnnouncement);

        return ResponseEntity.status(HttpStatus.OK).body("Profile image updated successfully.");
    }

    public ResponseEntity<Object> deleteAnnouncement(UUID id) {
        Optional<Announcement> foundedAnnouncement = announcementRepository.findById(id);

        if (foundedAnnouncement.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Announcement not found.");

        announcementRepository.delete(foundedAnnouncement.get());

        return ResponseEntity.status(HttpStatus.OK).body("Announcement successfully deleted.");
    }
}
