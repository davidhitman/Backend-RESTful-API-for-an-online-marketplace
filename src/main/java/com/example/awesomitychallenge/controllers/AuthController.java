package com.example.awesomitychallenge.controllers;

import com.example.awesomitychallenge.dto.CreateUserDto;
import com.example.awesomitychallenge.dto.LoginDto;
import com.example.awesomitychallenge.dto.UserDto;
import com.example.awesomitychallenge.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Authentication Controller", description = "Handles user login authentication and registration")
@AllArgsConstructor
@RestController
@RequestMapping("/auth")

public class AuthController {

    private UserService userService;

    @Operation(summary = "User Sign Up", description = "Registers a new user in the system")
    @PostMapping
    @SecurityRequirement(name = "none")
    public ResponseEntity<GenericResponse<UserDto>> userSignUp(@Valid @RequestBody CreateUserDto userDto) {
        var userSignup = userService.userSignUp(userDto);
        return ResponseEntity.ok(new GenericResponse<>("User Registered successfully", userSignup));
    }

    @Operation(summary = "User Login", description = "Allows users to log in and receive authentication token")
    @PostMapping("/login")
    @SecurityRequirement(name = "none")
    public ResponseEntity<GenericResponse<String>> login(@Valid @RequestBody LoginDto credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();
        var token = userService.login(email, password);
        return ResponseEntity.ok(new GenericResponse<>("token to be used to login", token));
    }
}