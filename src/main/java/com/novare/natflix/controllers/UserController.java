package com.novare.natflix.controllers;

import com.novare.natflix.exceptions.UserNotFoundException;
import com.novare.natflix.models.user.*;
import com.novare.natflix.services.UserService;
import com.novare.natflix.utils.PasswordHasher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

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
           return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterInput userInput) throws NoSuchAlgorithmException {
        String email = userInput.getEmail();
        String password = userInput.getPassword();

        if(email == null  || email.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Email and password are required.");
        }

        User existingUser = userService.findByEmail(email);

        if(existingUser == null) {
            String hashedPassword = PasswordHasher.hashPassword(password);
            UserType userType = UserType.fromTypeValue(userInput.getType());
            User newUser = new User(userInput.getName(), email, hashedPassword);

            if(userType != null) {
                newUser.setType(UserType.ADMIN);
            }
            User createdUser = userService.create(newUser);

            return ResponseEntity.ok(createdUser);
        } else {
           return ResponseEntity.badRequest().body("User with this email already exists.");
        }
    }
}
