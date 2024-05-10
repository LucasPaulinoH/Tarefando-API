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

import api.tutoringschool.dtos.AuthenticationDTO;
import api.tutoringschool.dtos.RegisterDTO;
import api.tutoringschool.model.User;
import api.tutoringschool.repositories.UserRepository;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO loginData){
        var usernamePasswordToken = new UsernamePasswordAuthenticationToken(loginData.email(), loginData.password());
        authenticationManager.authenticate(usernamePasswordToken);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerData){
        if(userRepository.findByEmail(registerData.email()) != null) 
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerData.password());
        User newUser = new User( registerData.email(), registerData.role(), encryptedPassword);

        userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
