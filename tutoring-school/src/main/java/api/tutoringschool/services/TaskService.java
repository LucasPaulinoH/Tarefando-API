package api.tutoringschool.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import api.tutoringschool.dtos.task.TaskDTO;
import api.tutoringschool.model.Task;
import api.tutoringschool.repositories.SubjectRepository;
import api.tutoringschool.repositories.TaskRepository;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public Task createTask(TaskDTO taskDTO) throws BadRequestException {
        var foundedSubject = subjectRepository.findById(taskDTO.subjectId());

        if (foundedSubject.isEmpty())
            throw new BadRequestException("Given subjectId is not registered.");

        Task newTask = new Task();
        BeanUtils.copyProperties(taskDTO, newTask);

        return taskRepository.save(newTask);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public ResponseEntity<Object> getTask(UUID id) {
        Optional<Task> foundedTask = taskRepository.findById(id);

        if (foundedTask.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");

        return ResponseEntity.status(HttpStatus.OK).body(foundedTask.get());
    }

    public ResponseEntity<Object> updateTask(UUID id, TaskDTO taskDTO) {
        Optional<Task> foundedTask = taskRepository.findById(id);

        if (foundedTask.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");

        Task updatedTask = foundedTask.get();
        BeanUtils.copyProperties(taskDTO, updatedTask);

        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.save(updatedTask));
    }

    public ResponseEntity<Object> deleteTask(UUID id) {
        Optional<Task> foundedTask = taskRepository.findById(id);

        if (foundedTask.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");

        taskRepository.delete(foundedTask.get());

        return ResponseEntity.status(HttpStatus.OK).body("Task successfully deleted.");
    }
}
