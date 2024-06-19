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
import api.tutoringschool.repositories.StudentRepository;
import api.tutoringschool.repositories.SubjectRepository;
import api.tutoringschool.repositories.TaskRepository;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Task createTask(TaskDTO taskDTO) throws BadRequestException {
        var foundedSubject = subjectRepository.findById(taskDTO.subjectId());

        if (foundedSubject.isEmpty())
            throw new BadRequestException("Given subjectId is not registered.");

        var foundedStudent = studentRepository.findById(taskDTO.studentId());

        if (foundedStudent.isEmpty())
            throw new BadRequestException("Given studentId is not registered.");

        Task newTask = new Task(taskDTO.subjectId(), taskDTO.title(), taskDTO.description(), taskDTO.images(),
                taskDTO.deadlineDate(), false, foundedStudent.get());

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

    public ResponseEntity<Object> toggleTaskConcluded(UUID taskId, boolean isConcluded) {
        Optional<Task> foundedTask = taskRepository.findById(taskId);

        if (foundedTask.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");

        Task concludedTask = foundedTask.get();
        concludedTask.setConcluded(isConcluded);

        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.save(concludedTask));
    }

    public ResponseEntity<Object> deleteTask(UUID id) {
        Optional<Task> foundedTask = taskRepository.findById(id);

        if (foundedTask.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");

        taskRepository.delete(foundedTask.get());

        return ResponseEntity.status(HttpStatus.OK).body("Task successfully deleted.");
    }
}
