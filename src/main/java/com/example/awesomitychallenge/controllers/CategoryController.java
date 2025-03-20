package com.example.awesomitychallenge.controllers;


import com.example.awesomitychallenge.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category Controller", description = "Handles categories")
@AllArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;

    @Operation(summary = "Adding category", description = "Allows Admins to register an category")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<GenericResponse<String>> addCategory(@RequestParam String category) {
        categoryService.addCategory(category);
        return ResponseEntity.ok(new GenericResponse<>("Category added successfully", "The category added is:" + category));
    }
}
