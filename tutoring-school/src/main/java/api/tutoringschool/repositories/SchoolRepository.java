package api.tutoringschool.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.tutoringschool.model.School;

public interface SchoolRepository extends JpaRepository<School, UUID> {
    List<School> findByTutorId(UUID tutorId);
}
