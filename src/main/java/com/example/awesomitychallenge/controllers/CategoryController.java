package com.example.awesomitychallenge.controllers;


import com.example.awesomitychallenge.dto.CreateCategoryDto;
import com.example.awesomitychallenge.entities.Category;
import com.example.awesomitychallenge.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category Controller", description = "Handles categories")
@AllArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;

    @Operation(summary = "Adding category", description = "Allows Admins to register an category")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<GenericResponse<String>> addCategory(@Valid @RequestBody CreateCategoryDto category) {
        categoryService.addCategory(category);
        return ResponseEntity.ok(new GenericResponse<>("Category added successfully", "The category added is:" + category));
    }

    @Operation(summary = "Delete category", description = "Allows Admins to delete a category")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping
    public ResponseEntity<GenericResponse<String>> deleteCategory(@RequestParam Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new GenericResponse<>("Category deleted successfully", "The category deleted has id:" + id));
    }

    @Operation(summary = "Update category", description = "Allows Admins to update a category")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<GenericResponse<Category>> updateCategory(@RequestParam Long id, @Valid @RequestBody CreateCategoryDto categoryDto) {
        var category = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(new GenericResponse<>("Category deleted successfully", category));
    }

}
