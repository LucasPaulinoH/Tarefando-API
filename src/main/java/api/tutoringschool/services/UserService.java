package api.tutoringschool.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import api.tutoringschool.dtos.user.GuardianCardDTO;
import api.tutoringschool.dtos.user.ProfileImageUpdateDTO;
import api.tutoringschool.dtos.user.UpdateUserDTO;
import api.tutoringschool.dtos.user.UserCardDTO;
import api.tutoringschool.dtos.user.ValidatePasswordDTO;
import api.tutoringschool.model.School;
import api.tutoringschool.model.Student;
import api.tutoringschool.model.User;
import api.tutoringschool.repositories.UserRepository;
import api.tutoringschool.types.UserRole;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentService studentService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<Object> getUser(UUID id) {
        Optional<User> foundedUser = userRepository.findById(id);

        if (foundedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        return ResponseEntity.status(HttpStatus.OK).body(foundedUser.get());
    }

    public ResponseEntity<Object> getUserNameAndImage(UUID id) {
        Optional<User> foundedUser = userRepository.findById(id);

        if (foundedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        UserCardDTO userCardDTO = new UserCardDTO(foundedUser.get().getName(), foundedUser.get().getProfileImage());

        return ResponseEntity.status(HttpStatus.OK).body(userCardDTO);
    }

    public ResponseEntity<Object> getAllAssociatedGuardianCards(UUID tutorId) throws BadRequestException {
        Optional<User> foundedTutor = userRepository.findById(tutorId);

        if (foundedTutor.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor not found.");

        if (foundedTutor.get().getRole() != UserRole.TUTOR)
            throw new BadRequestException("Given user id is not from a TUTOR.");

        if (foundedTutor.get().getSchools().size() < 1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor doesn't own any school.");

        var students = new ArrayList<Student>();
        var studentGuardians = new ArrayList<GuardianCardDTO>();

        School iterableSchool = null;
        for (int i = 0; i < foundedTutor.get().getSchools().size(); i++) {
            iterableSchool = foundedTutor.get().getSchools().get(i);

            students.addAll(studentService.getStudentsFromSchool(iterableSchool.getId()));
        }

        Student iterableStudent = null;
        for (int i = 0; i < students.size(); i++) {
            iterableStudent = students.get(i);

            User iterableStudentGuardian = userRepository.findById(iterableStudent.getUser()).get();
            studentGuardians.add(new GuardianCardDTO(iterableStudent.getUser(),
                    iterableStudentGuardian.getProfileImage(), iterableStudentGuardian.getName()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(studentGuardians);
    }

    public ResponseEntity<Object> updateUser(UUID id, UpdateUserDTO userData) {
        Optional<User> foundedUser = userRepository.findById(id);

        if (foundedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        User updatedUser = foundedUser.get();
        BeanUtils.copyProperties(userData, updatedUser);

        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(updatedUser));
    }

    public ResponseEntity<Object> updateUserProfileImage(ProfileImageUpdateDTO profileImageUpdateDTO) {
        Optional<User> foundedUser = userRepository.findById(profileImageUpdateDTO.id());

        if (foundedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        User updatedUser = foundedUser.get();
        updatedUser.setProfileImage(profileImageUpdateDTO.url());
        userRepository.save(updatedUser);

        return ResponseEntity.status(HttpStatus.OK).body("Profile image updated successfully.");
    }

    public ResponseEntity<Object> updateUserPassword(ValidatePasswordDTO validatePasswordDTO) {
        ResponseEntity<Optional<User>> responseEntity = validateCurrentPassword(validatePasswordDTO);
        Optional<User> foundedUser = responseEntity.getBody();
    
        if (foundedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    
        User updatedUser = foundedUser.get();
        updatedUser.setPassword(passwordEncoder.encode(validatePasswordDTO.newPassword()));
        userRepository.save(updatedUser);
    
        return ResponseEntity.status(HttpStatus.OK).body("Password successfully updated.");
    }
    
    private ResponseEntity<Optional<User>> validateCurrentPassword(ValidatePasswordDTO validatePasswordDTO) {
        Optional<User> foundedUser = userRepository.findById(validatePasswordDTO.id());
    
        if (foundedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
    
        boolean doesPasswordsMatch = passwordEncoder.matches(validatePasswordDTO.currentPassword(),
                foundedUser.get().getPassword());
    
        if (!doesPasswordsMatch)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Optional.empty());
    
        return ResponseEntity.status(HttpStatus.OK).body(foundedUser);
    }

    public ResponseEntity<Object> deleteUser(UUID id) {
        Optional<User> foundedUser = userRepository.findById(id);

        if (foundedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        userRepository.delete(foundedUser.get());

        return ResponseEntity.status(HttpStatus.OK).body("User successfully deleted.");
    }
}
