package api.tutoringschool.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import api.tutoringschool.dtos.subject.SubjectDTO;
import api.tutoringschool.model.Subject;
import api.tutoringschool.repositories.SubjectRepository;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository repository;

    public Subject createSubject(SubjectDTO subjectDTO) {
        Subject newSubject = new Subject();
        BeanUtils.copyProperties(subjectDTO, newSubject);

        return repository.save(newSubject);
    }

    public List<Subject> getAllSubjects() {
        return repository.findAll();
    }

    public ResponseEntity<Object> getSubject(UUID id) {
        Optional<Subject> foundedSubject = repository.findById(id);

        if (foundedSubject.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject not found.");

        return ResponseEntity.status(HttpStatus.OK).body(foundedSubject);
    }

    public ResponseEntity<Object> updateSubject(UUID id, SubjectDTO subjectData) {
        Optional<Subject> foundedSubject = repository.findById(id);

        if (foundedSubject.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject not found.");

        Subject updatedSubject = foundedSubject.get();
        BeanUtils.copyProperties(subjectData, updatedSubject);

        return ResponseEntity.status(HttpStatus.OK).body(repository.save(updatedSubject));
    }

    public ResponseEntity<Object> deleteSubject(UUID id) {
        Optional<Subject> foundedSubject = repository.findById(id);

        if (foundedSubject.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject not found.");

        repository.delete(foundedSubject.get());

        return ResponseEntity.status(HttpStatus.OK).body("Subject successfully deleted.");
    }
}
