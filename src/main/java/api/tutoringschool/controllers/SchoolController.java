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

import api.tutoringschool.dtos.school.SchoolDTO;
import api.tutoringschool.dtos.user.ProfileImageUpdateDTO;
import api.tutoringschool.model.School;
import api.tutoringschool.services.SchoolService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/schools")
@Tag(name = "Schools")
public class SchoolController {
    @Autowired
    private SchoolService service;

    @PostMapping
    public ResponseEntity<School> createSchool(@RequestBody @Valid SchoolDTO schoolDTO) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createSchool(schoolDTO));
    }

    @GetMapping
    public ResponseEntity<List<School>> getAllSchools() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllSchools());
    }

    @GetMapping("/search")
    public ResponseEntity<List<School>> getSchoolsFromTutor(@RequestParam("tutorId") String tutorId) {
        UUID tutorUUID = UUID.fromString(tutorId);
        return ResponseEntity.status(HttpStatus.OK).body(service.getSchoolsFromTutor(tutorUUID));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSchool(@PathVariable(value = "id") UUID id) {
        return service.getSchool(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSchool(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid SchoolDTO schoolDTO) {
        return service.updateSchool(id, schoolDTO);
    }

    @PatchMapping("/profile-image")
    public ResponseEntity<Object> updateSchoolProfileImage(
            @RequestBody @Valid ProfileImageUpdateDTO profileImageUpdateDTO) {
        return service.updateSchoolProfileImage(profileImageUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSchool(@PathVariable(value = "id") UUID id) {
        return service.deleteSchool(id);
    }
}
