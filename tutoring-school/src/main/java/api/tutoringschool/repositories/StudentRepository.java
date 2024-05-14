package api.tutoringschool.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.tutoringschool.model.Student;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
