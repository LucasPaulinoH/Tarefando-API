package api.tutoringschool.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.tutoringschool.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, UUID>{
}
