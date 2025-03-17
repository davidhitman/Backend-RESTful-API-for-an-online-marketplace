package com.example.awesomitychallenge.controllers;

import com.example.awesomitychallenge.dto.CreateUserDto;
import com.example.awesomitychallenge.dto.LoginDto;
import com.example.awesomitychallenge.dto.UserDto;
import com.example.awesomitychallenge.entities.AuthenticationResponse;
import com.example.awesomitychallenge.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Tag(name = "Authentication Controller", description = "Handles user login authentication and registration")
@AllArgsConstructor
@RestController
@RequestMapping("/auth")

public class AuthController {

    private UserService userService;

    @Operation(summary = "User Sign Up", description = "Registers a new user in the system")
    @PostMapping
    public ResponseEntity<String> userSignUp(@RequestBody CreateUserDto userDto){
        var userSignup = userService.userSignUp(userDto);
        if (userSignup){
            return ResponseEntity.ok("User Registered Successfully!");
        }else{
            return ResponseEntity.ok("Email Already Exists!");
        }
    }

    @Operation(summary = "User Login", description = "Allows users to log in and receive authentication token")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginDto credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();
        var token = userService.login(email, password);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}