package api.tutoringschool.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
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
import api.tutoringschool.repositories.SchoolRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/schools")
public class SchoolController {
    @Autowired
    SchoolRepository repository;

    @PostMapping
    public ResponseEntity<School> createSchool(@RequestBody @Valid SchoolDTO schoolDTO) {
        var newSchool = new School();
        BeanUtils.copyProperties(schoolDTO, newSchool);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newSchool));
    }

    @GetMapping
    public ResponseEntity<List<School>> getAllSchools() {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSchool(@PathVariable(value = "id") UUID id) {
        Optional<School> foundedSchool = repository.findById(id);

        if (foundedSchool.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");

        return ResponseEntity.status(HttpStatus.OK).body(foundedSchool.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSchool(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid SchoolDTO schoolData) {
        Optional<School> foundedSchool = repository.findById(id);

        if (foundedSchool.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");

        School updatedSchool = foundedSchool.get();
        BeanUtils.copyProperties(schoolData, updatedSchool);

        return ResponseEntity.status(HttpStatus.OK).body(repository.save(updatedSchool));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSchool(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid SchoolDTO schoolData) {
        Optional<School> foundedSchool = repository.findById(id);

        if (foundedSchool.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");

        repository.delete(foundedSchool.get());

        return ResponseEntity.status(HttpStatus.OK).body("School successfully deleted.");
    }
}
