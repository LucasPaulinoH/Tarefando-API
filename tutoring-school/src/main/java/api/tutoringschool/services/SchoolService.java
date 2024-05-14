package api.tutoringschool.services;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import api.tutoringschool.dtos.school.SchoolDTO;
import api.tutoringschool.model.School;
import api.tutoringschool.repositories.SchoolRepository;
import api.tutoringschool.repositories.UserRepository;
import api.tutoringschool.types.UserRole;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserRepository userRepository;

    public School createSchool(SchoolDTO schoolDTO) throws BadRequestException {
        var foundedUser = userRepository.findById(schoolDTO.tutorId());

        if (foundedUser.isEmpty())
            throw new BadRequestException("Given tutorId is not registered.");

        if (foundedUser.get().getRole() != UserRole.TUTOR)
            throw new BadRequestException("Given user id is not from a TUTOR.");

        School newSchool = new School();
        BeanUtils.copyProperties(schoolDTO, newSchool);
        
        return schoolRepository.save(newSchool);
    }

    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    public List<School> getSchoolsFromTutor(UUID tutorId) {
        return schoolRepository.findByTutorId(tutorId);
    }

    public ResponseEntity<Object> getSchool(UUID id) {
        Optional<School> foundedSchool = schoolRepository.findById(id);

        if (foundedSchool.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");

        return ResponseEntity.status(HttpStatus.OK).body(foundedSchool.get());
    }

    public ResponseEntity<Object> updateSchool(UUID id, SchoolDTO schoolData) {
        Optional<School> foundedSchool = schoolRepository.findById(id);

        if (foundedSchool.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");

        School updatedSchool = foundedSchool.get();
        BeanUtils.copyProperties(schoolData, updatedSchool);

        return ResponseEntity.status(HttpStatus.OK).body(schoolRepository.save(updatedSchool));
    }

    public ResponseEntity<Object> deleteSchool(UUID id) {
        Optional<School> foundedSchool = schoolRepository.findById(id);

        if (foundedSchool.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");

        schoolRepository.delete(foundedSchool.get());

        return ResponseEntity.status(HttpStatus.OK).body("School successfully deleted.");
    }
}
