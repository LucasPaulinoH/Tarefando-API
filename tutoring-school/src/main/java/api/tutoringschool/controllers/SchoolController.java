package api.tutoringschool.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.tutoringschool.dtos.school.SchoolDTO;
import api.tutoringschool.model.School;
import api.tutoringschool.services.SchoolService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/schools")
public class SchoolController {
    @Autowired
    private SchoolService service;

    @PostMapping
    public ResponseEntity<School> createSchool(@RequestBody @Valid SchoolDTO schoolDTO) {
        School createdSchool = service.createSchool(schoolDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchool);
    }

    @GetMapping
    public ResponseEntity<List<School>> getAllSchools() {
        List<School> schools = service.getAllSchools();
        return ResponseEntity.status(HttpStatus.OK).body(schools);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSchool(@PathVariable(value = "id") UUID id) {
        Optional<School> foundedSchool = service.getSchoolById(id);
        if (foundedSchool.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundedSchool.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSchool(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid SchoolDTO schoolData) throws Exception {
        School updatedSchool = service.updateSchool(id, schoolData);
        return ResponseEntity.status(HttpStatus.OK).body(updatedSchool);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSchool(@PathVariable(value = "id") UUID id) throws Exception {
        service.deleteSchool(id);
        return ResponseEntity.status(HttpStatus.OK).body("School successfully deleted.");
    }
}
