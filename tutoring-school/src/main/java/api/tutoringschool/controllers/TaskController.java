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

import api.tutoringschool.dtos.task.TaskDTO;
import api.tutoringschool.model.Task;
import api.tutoringschool.services.TaskService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService service;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskDTO TaskDTO) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTask(TaskDTO));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTask(@PathVariable(value = "id") UUID id) {
        return service.getTask(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid TaskDTO TaskDTO) {
        return service.updateTask(id, TaskDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value = "id") UUID id) {
        return service.deleteTask(id);
    }
}
