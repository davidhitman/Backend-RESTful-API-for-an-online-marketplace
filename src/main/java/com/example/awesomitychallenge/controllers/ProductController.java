package com.example.awesomitychallenge.controllers;

import com.example.awesomitychallenge.dto.CreateProductDto;
import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.dto.UpdateProductDto;
import com.example.awesomitychallenge.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Product Controller", description = "Handles Products")
@AllArgsConstructor
@RestController
@RequestMapping("/products")
@PreAuthorize("hasAuthority('ADMIN')")

public class ProductController {
    private ProductService productService;

    @Operation(summary = "Add product", description = "Allow Admins to add products")
    @PostMapping
    public ResponseEntity<GenericResponse<ProductDto>> registerProduct(@Valid @RequestBody CreateProductDto productDto) {
        ProductDto product = productService.registerProduct(productDto);
        return ResponseEntity.ok(new GenericResponse<>("Product Successfully Registered", product));
    }

    @Operation(summary = "Delete product", description = "Allow Admins to delete product")
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<String>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new GenericResponse<>("Product deleted successfully", "Deleted ID:" + id));
    }

    @Operation(summary = "View all Products", description = "Allow Admins to view all stored products")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<GenericResponse<Page<ProductDto>>> viewAllProducts(@RequestParam(required = false) String search, @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "5") int pageSize) {
        var products = productService.viewAllProducts(search, offset, pageSize);
        return ResponseEntity.ok(new GenericResponse<>("Stored Products", products));
    }

    @Operation(summary = "View one product", description = "Allows Admins to view all products")
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<ProductDto>> viewProduct(@PathVariable Long id) {
        ProductDto product = productService.viewProduct(id);
        return ResponseEntity.ok(new GenericResponse<>("The product with id:"+ " " + id +" "+ "is:", product));

    }

    @Operation(summary = "Update product", description = "Allow Admins to Update products")
    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<ProductDto>> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductDto product) {
        var updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(new GenericResponse<>("Product updated successfully", updatedProduct));
    }

    @Operation(summary = "Mark product as featured", description = "Allow Admins to mark a product as featured")
    @PatchMapping("/{id}/featured")
    public ResponseEntity<GenericResponse<ProductDto>> updateFeaturedStatus(@PathVariable Long id, @RequestParam boolean featured) {
        var updatedProduct = productService.markAsFeatured(id, featured);
        return ResponseEntity.ok(new GenericResponse<>("The Product is now featured", updatedProduct));
    }
}