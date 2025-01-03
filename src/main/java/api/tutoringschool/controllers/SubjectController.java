package api.tutoringschool.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
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

import api.tutoringschool.dtos.subject.SubjectDTO;
import api.tutoringschool.model.Subject;
import api.tutoringschool.services.SubjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/subjects")
@Tag(name = "Subjects")
public class SubjectController {
    @Autowired
    private SubjectService service;

    @PostMapping
    public ResponseEntity<Subject> createSubject(@RequestBody @Valid SubjectDTO subjectDTO) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createSubject(subjectDTO));
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllSubjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSubject(@PathVariable(value = "id") UUID id) {
        return service.getSubject(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSubject(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid SubjectDTO subjectDTO) {
        return service.updateSubject(id, subjectDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubject(@PathVariable(value = "id") UUID id) {
        return service.deleteSubject(id);
    }
}
