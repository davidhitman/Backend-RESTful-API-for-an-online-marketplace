package com.example.awesomitychallenge.controllers;

import com.example.awesomitychallenge.dto.CreateUserDto;
import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.dto.UpdateUserDto;
import com.example.awesomitychallenge.entities.Products;
import com.example.awesomitychallenge.entities.Users;
import com.example.awesomitychallenge.services.ProductService;
import com.example.awesomitychallenge.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "User Controller", description = "Handles Users")
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private ProductService product_service;

    @Operation(summary = "Delete User", description = "Allow Admins to delete Users")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok("User deleted successfully!");
    }

    @Operation(summary = "Update User", description = "Allows Admin to Update Users")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{email}")
    public ResponseEntity<String> updateUser(@PathVariable String email, @RequestBody UpdateUserDto user) {
        userService.updateUser(email, user);
        return ResponseEntity.ok("User Updated Successfully");
    }

    @Operation(summary = "View all Stored Users", description = "Allows Admins to view all signed Up users")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Users>> viewUsers() {
        List<Users> users = userService.viewAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Search product by category", description = "Allow Admins and Users to search products by the product category")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/search/{category}")
    public ResponseEntity<List<ProductDto>> BrowseByCategory(@PathVariable String category) {
        var browserByCategory = product_service.browserByCategory(category);
        return new ResponseEntity<>(browserByCategory , HttpStatus.OK);
    }
}

