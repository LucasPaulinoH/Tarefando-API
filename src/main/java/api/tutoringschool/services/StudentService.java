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
import api.tutoringschool.model.School;
import api.tutoringschool.model.Student;
import api.tutoringschool.model.Task;
import api.tutoringschool.repositories.SchoolRepository;
import api.tutoringschool.repositories.StudentRepository;
import api.tutoringschool.repositories.UserRepository;
import api.tutoringschool.types.UserRole;

@Service
public class StudentService {
    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public Student createStudent(StudentDTO studentDTO) throws BadRequestException {
        var foundedUser = userRepository.findById(studentDTO.userId());

        if (foundedUser.isEmpty())
            throw new BadRequestException("Given guardianId is not registered.");

        if (foundedUser.get().getRole() != UserRole.GUARDIAN)
            throw new BadRequestException("Given user id is not from a GUARDIAN.");

        Student newStudent = new Student();
        BeanUtils.copyProperties(studentDTO, newStudent);

        newStudent.setUser(foundedUser.get());

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
        return studentRepository.findByUserId(guardianId);
    }

    public List<Student> getStudentsFromSchool(UUID schoolId) {
        return studentRepository.findBySchoolId(schoolId);
    }

    public ResponseEntity<Object> updateStudent(UUID id, StudentDTO studentData) throws BadRequestException {
        Optional<Student> foundedStudent = studentRepository.findById(id);

        var foundedUser = userRepository.findById(studentData.userId());

        if (foundedStudent.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");

        if (foundedUser.isEmpty())
            throw new BadRequestException("Given guardianId is not registered.");

        if (foundedUser.get().getRole() != UserRole.GUARDIAN)
            throw new BadRequestException("Given user id is not from a GUARDIAN.");

        Student updatedStudent = foundedStudent.get();
        BeanUtils.copyProperties(studentData, updatedStudent);

        return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(updatedStudent));
    }

    public ResponseEntity<Object> checkStudentSchoolarLinkValidity(UUID studentId) {
        Optional<Student> foundedStudent = studentRepository.findById(studentId);

        if (foundedStudent.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");

        UUID studentSchoolId = foundedStudent.get().getSchoolId();

        if (studentSchoolId != null) {
            Optional<School> foundedSchool = schoolRepository.findById(studentSchoolId);

            if (foundedSchool.isEmpty()) {
                Student updatedStudent = foundedStudent.get();
                updatedStudent.setSchoolId(null);
                studentRepository.save(updatedStudent);

                return ResponseEntity.status(HttpStatus.OK).body(false);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    public ResponseEntity<Object> unlinkStudentFromSchool(UUID studentId) {
        Optional<Student> foundedStudent = studentRepository.findById(studentId);

        if (foundedStudent.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");

        Optional<School> foundedSchool = schoolRepository.findById(foundedStudent.get().getSchoolId());

        if (foundedSchool.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");

        Student unlinkedStudent = foundedStudent.get();
        unlinkedStudent.setSchoolId(null);

        return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(unlinkedStudent));
    }

    public ResponseEntity<Object> linkStudentToSchool(UUID studentId, UUID schoolId) {
        Optional<Student> foundedStudent = studentRepository.findById(studentId);

        if (foundedStudent.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");

        Optional<School> foundedSchool = schoolRepository.findById(schoolId);

        if (foundedSchool.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");

        Student linkedStudent = foundedStudent.get();
        linkedStudent.setSchoolId(schoolId);

        return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(linkedStudent));
    }

    public ResponseEntity<Object> deleteStudent(UUID id) {
        Optional<Student> foundedStudent = studentRepository.findById(id);

        if (foundedStudent.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");

        studentRepository.delete(foundedStudent.get());

        return ResponseEntity.status(HttpStatus.OK).body("Student successfully deleted.");
    }
}
