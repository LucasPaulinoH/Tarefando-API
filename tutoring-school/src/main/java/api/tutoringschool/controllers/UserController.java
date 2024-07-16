package api.tutoringschool.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.tutoringschool.dtos.user.ProfileImageUpdateDTO;
import api.tutoringschool.dtos.user.UpdateUserDTO;
import api.tutoringschool.dtos.user.ValidatePasswordDTO;
import api.tutoringschool.model.User;
import api.tutoringschool.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable(value = "id") UUID id) {
        return userService.getUser(id);
    }

    @GetMapping("/user-card/{id}")
    public ResponseEntity<Object> getUserNameAndImage(@PathVariable(value = "id") UUID id) {
        return userService.getUserNameAndImage(id);
    }

    @GetMapping("/associated-guardians")
    public ResponseEntity<Object> getAllAssociatedGuardianCards(@RequestParam("tutorId") String tutorId)
            throws BadRequestException {
        UUID tutorUUID = UUID.fromString(tutorId);
        ResponseEntity<Object> response = userService.getAllAssociatedGuardianCards(tutorUUID);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid UpdateUserDTO userData) {
        return userService.updateUser(id, userData);
    }

    @PatchMapping("/profile-image")
    public ResponseEntity<Object> updateUserProfileImage(
            @RequestBody @Valid ProfileImageUpdateDTO profileImageUpdateDTO) {
        return userService.updateUserProfileImage(profileImageUpdateDTO);
    }

    @PostMapping("/validate-password")
    public ResponseEntity<Object> validateCurrentPassword(
            @RequestBody @Valid ValidatePasswordDTO validatePasswordDTO) {
        return userService.validateCurrentPassword(validatePasswordDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
        return userService.deleteUser(id);
    }
}
