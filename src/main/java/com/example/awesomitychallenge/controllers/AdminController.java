package com.example.awesomitychallenge.controllers;

import com.example.awesomitychallenge.dto.CreateAdminDto;
import com.example.awesomitychallenge.dto.UserDto;
import com.example.awesomitychallenge.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin Controller", description = "Handles Admins")
@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Operation(summary = "Admin Sign Up", description = "Registers a new Admin in the system")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<GenericResponse<UserDto>> adminSignUp(@RequestBody CreateAdminDto adminDto) {
        var adminSignup = userService.adminSignUp(adminDto);
        return ResponseEntity.ok(new GenericResponse<>("Admin Registered successfully", adminSignup));
    }
}
