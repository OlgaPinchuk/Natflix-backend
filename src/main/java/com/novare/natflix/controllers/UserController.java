package com.novare.natflix.controllers;

import com.novare.natflix.exceptions.UserNotFoundException;
import com.novare.natflix.models.user.*;
import com.novare.natflix.services.UserService;
import com.novare.natflix.utils.PasswordHasher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) throws NoSuchAlgorithmException {
        String email = loginRequest.email();

        User existingUser = userService.findByEmail(email);

        if(existingUser == null) {
            throw new UserNotFoundException(email);
        }

        String enteredPasswordHash = PasswordHasher.hashPassword(loginRequest.password());
        if(enteredPasswordHash.equals(existingUser.getPassword())) {
           return ResponseEntity.ok(createResponse(existingUser.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterInput userInput) throws NoSuchAlgorithmException {
        String email = userInput.email();
        String password = userInput.password();

        if(email == null  || email.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Email and password are required.");
        }

        User existingUser = userService.findByEmail(userInput.email());

        if(existingUser == null) {
            String hashedPassword = PasswordHasher.hashPassword(userInput.password());
            User newUser = new User(userInput.fullName(), userInput.email(), hashedPassword);
            User createdUser = userService.create(newUser);

            return ResponseEntity.ok(createResponse(createdUser.getId()));
        } else {
           return ResponseEntity.badRequest().body("User with this email already exists.");
        }
    }

    private Map<String, Long> createResponse(long userId) {
        return Map.of("userId", userId);
    }
}
