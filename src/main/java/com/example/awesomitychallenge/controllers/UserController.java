package com.example.awesomitychallenge.controllers;

import com.example.awesomitychallenge.dto.RatingDto;
import com.example.awesomitychallenge.dto.UpdateUserDto;
import com.example.awesomitychallenge.dto.UserDto;
import com.example.awesomitychallenge.entities.ProductRatings;
import com.example.awesomitychallenge.services.ProductRatingService;
import com.example.awesomitychallenge.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User Controller", description = "Handles Users")
@AllArgsConstructor
@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private UserService userService;
    private ProductRatingService productRatingService;

    @Operation(summary = "Delete User", description = "Allow Admins to delete Users")
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new GenericResponse<>("User deleted successfully!", "Deleted ID: " + id));
    }

    @Operation(summary = "Update User", description = "Allows Admin to Update Users")
    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<UserDto>> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserDto user) {
        var updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(new GenericResponse<>("User Updated Successfully!", updatedUser));
    }

    @Operation(summary = "View all Users", description = "Allows Admins to view all signed Up users")
    @GetMapping
    public ResponseEntity<GenericResponse<Page<UserDto>>> viewUsers(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "5") int pageSize) {
        var users = userService.viewAllUsers(offset, pageSize);
        return ResponseEntity.ok(new GenericResponse<>("All Stored users", users));
    }

    @Operation(summary = "Rate products", description = "Review and rate previously ordered products")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<GenericResponse<RatingDto>> rateProducts(@RequestBody RatingDto ratingDto) {
        var rate = productRatingService.rateProduct(ratingDto);
        return ResponseEntity.ok(new GenericResponse<>("The Product Rating", rate));
    }



}

