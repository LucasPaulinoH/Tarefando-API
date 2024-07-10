package api.tutoringschool.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api.tutoringschool.model.Student;
import api.tutoringschool.model.User;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query("SELECT e FROM Student e JOIN FETCH e.tasks WHERE e.id = :id")
    Student findByIdWithTasks(@Param("id") UUID studentId);

    List<Student> findByUser(User user);

    List<Student> findBySchoolId(UUID schoolId);
}
