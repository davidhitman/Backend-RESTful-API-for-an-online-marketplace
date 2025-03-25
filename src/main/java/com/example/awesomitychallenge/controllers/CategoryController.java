package com.example.awesomitychallenge.controllers;


import com.example.awesomitychallenge.dto.CreateCategoryDto;
import com.example.awesomitychallenge.dto.UpdateCategoryDto;
import com.example.awesomitychallenge.dto.ViewCategoryDto;
import com.example.awesomitychallenge.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category Controller", description = "Handles categories")
@AllArgsConstructor
@RestController
@RequestMapping("/categories")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class CategoryController {
    private CategoryService categoryService;

    @Operation(summary = "Adding category", description = "Allows Admins to register an category")
    @PostMapping
    public ResponseEntity<GenericResponse<String>> addCategory(@Valid @RequestBody CreateCategoryDto category) {
        categoryService.addCategory(category);
        return ResponseEntity.ok(new GenericResponse<>("Category added successfully", "The category added is:" + category.getName()));
    }

    @Operation(summary = "Delete category", description = "Allows Admins to delete a category")
    @DeleteMapping
    public ResponseEntity<GenericResponse<String>> deleteCategory(@RequestParam Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new GenericResponse<>("Category deleted successfully", "The category deleted has id:" + id));
    }

    @Operation(summary = "Update category", description = "Allows Admins to update a category")
    @PutMapping
    public ResponseEntity<GenericResponse<ViewCategoryDto>> updateCategory(@RequestParam Long id, @Valid @RequestBody UpdateCategoryDto categoryDto) {
        var category = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(new GenericResponse<>("Category Updated successfully", category));
    }

    @Operation(summary = "view categories", description = "Allows Admins to view stored categories")
    @GetMapping
    public ResponseEntity<GenericResponse<Page<ViewCategoryDto>>> viewCategory(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "5") int pageSize) {
        var category = categoryService.viewCategory(offset, pageSize);
        return ResponseEntity.ok(new GenericResponse<>("Stored Categories", category));
    }

}