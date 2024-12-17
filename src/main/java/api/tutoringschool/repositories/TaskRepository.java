package api.tutoringschool.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.tutoringschool.model.Task;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
