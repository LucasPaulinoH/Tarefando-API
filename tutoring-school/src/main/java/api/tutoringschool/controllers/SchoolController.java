package api.tutoringschool.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
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
    SchoolRepository schoolRepository;
    
    @PostMapping
    public ResponseEntity<School> createSchool(@RequestBody @Valid SchoolDTO schoolDTO) {
        var newSchool = new School();
        BeanUtils.copyProperties(schoolDTO, newSchool);
        return ResponseEntity.status(HttpStatus.CREATED).body(schoolRepository.save(newSchool));
    }
}
