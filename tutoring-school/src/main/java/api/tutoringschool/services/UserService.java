package api.tutoringschool.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import api.tutoringschool.dtos.user.UserDTO;
import api.tutoringschool.model.User;
import api.tutoringschool.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<Object> getUser(UUID id) {
        Optional<User> foundedUser = userRepository.findById(id);

        if (foundedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        return ResponseEntity.status(HttpStatus.OK).body(foundedUser.get());
    }

    public ResponseEntity<Object> updateUser(UUID id, UserDTO userData) {
        Optional<User> foundedUser = userRepository.findById(id);

        if (foundedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        User updatedUser = foundedUser.get();
        BeanUtils.copyProperties(userData, updatedUser);

        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(updatedUser));
    }

    public ResponseEntity<Object> deleteUser(UUID id) {
        Optional<User> foundedUser = userRepository.findById(id);

        if (foundedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        userRepository.delete(foundedUser.get());

        return ResponseEntity.status(HttpStatus.OK).body("User successfully deleted.");
    }
}
