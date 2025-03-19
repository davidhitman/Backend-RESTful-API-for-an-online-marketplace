package com.example.awesomitychallenge.controllers;

import com.example.awesomitychallenge.dto.UpdateUserDto;
import com.example.awesomitychallenge.dto.UserDto;
import com.example.awesomitychallenge.entities.Users;
import com.example.awesomitychallenge.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User Controller", description = "Handles Users")
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Operation(summary = "Delete User", description = "Allow Admins to delete Users")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new GenericResponse<>("User deleted successfully!", "Deleted ID: " + id));
    }

    @Operation(summary = "Update User", description = "Allows Admin to Update Users")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<UserDto>> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto user) {
        var updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(new GenericResponse<>("User Updated Successfully!", updatedUser));
    }

    @Operation(summary = "View all Users", description = "Allows Admins to view all signed Up users")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<GenericResponse<Page<Users>>> viewUsers(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "5") int pageSize) {
        Page<Users> users = userService.viewAllUsers(offset, pageSize);
        return ResponseEntity.ok(new GenericResponse<>("All Stored users", users));
    }

}

