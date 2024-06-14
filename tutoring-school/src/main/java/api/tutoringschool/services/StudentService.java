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

import api.tutoringschool.dtos.student.StudentDTO;
import api.tutoringschool.model.Student;
import api.tutoringschool.model.Task;
import api.tutoringschool.repositories.StudentRepository;
import api.tutoringschool.repositories.UserRepository;
import api.tutoringschool.types.UserRole;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public Student createStudent(StudentDTO studentDTO) throws BadRequestException {
        var foundedUser = userRepository.findById(studentDTO.guardianId());

        if (foundedUser.isEmpty())
            throw new BadRequestException("Given guardianId is not registered.");

        if (foundedUser.get().getRole() != UserRole.GUARDIAN)
            throw new BadRequestException("Given user id is not from a GUARDIAN.");

        Student newStudent = new Student();
        BeanUtils.copyProperties(studentDTO, newStudent);

        return studentRepository.save(newStudent);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Task> getTasksFromStudent(UUID id) {
        return studentRepository.findByIdWithTasks(id).getTasks();
    }

    public ResponseEntity<Object> getStudent(UUID id) {
        Optional<Student> foundedStudent = studentRepository.findById(id);

        if (foundedStudent.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");

        return ResponseEntity.status(HttpStatus.OK).body(foundedStudent.get());
    }

    public List<Student> getStudentsFromGuardian(UUID guardianId) {
        return studentRepository.findByGuardianId(guardianId);
    }


    public ResponseEntity<Object> updateStudent(UUID id, StudentDTO studentData) {
        Optional<Student> foundedStudent = studentRepository.findById(id);

        if (foundedStudent.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");

        Student updatedStudent = foundedStudent.get();
        BeanUtils.copyProperties(studentData, updatedStudent);

        return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(updatedStudent));
    }

    public ResponseEntity<Object> deleteStudent(UUID id) {
        Optional<Student> foundedStudent = studentRepository.findById(id);

        if (foundedStudent.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");

        studentRepository.delete(foundedStudent.get());

        return ResponseEntity.status(HttpStatus.OK).body("Student successfully deleted.");
    }
}
