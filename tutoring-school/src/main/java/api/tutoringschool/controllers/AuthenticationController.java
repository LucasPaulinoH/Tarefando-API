package api.tutoringschool.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.tutoringschool.dtos.auth.AuthenticationDTO;
import api.tutoringschool.dtos.auth.LoginResponseDTO;
import api.tutoringschool.dtos.user.UserDTO;
import api.tutoringschool.model.User;
import api.tutoringschool.repositories.UserRepository;
import api.tutoringschool.security.TokenService;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO loginData) {
        var usernamePasswordToken = new UsernamePasswordAuthenticationToken(loginData.email(), loginData.password());
        var auth = authenticationManager.authenticate(usernamePasswordToken);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserDTO registerData) {
        if (userRepository.findByEmail(registerData.email()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerData.password());
        User newUser = new User(registerData.name(), registerData.email(), registerData.phone(), registerData.role(),
                registerData.profileImage(),
                encryptedPassword);

        return ResponseEntity.ok().body(userRepository.save(newUser));
    }
}
