package com.example.awesomitychallenge.controllers;

import com.example.awesomitychallenge.dto.CreateProductDto;
import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.dto.UpdateProductDto;
import com.example.awesomitychallenge.entities.Products;
import com.example.awesomitychallenge.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Product Controller", description = "Handles Products")
@AllArgsConstructor
@RestController
@RequestMapping("/products")

public class ProductController {
    private ProductService product_service;

    @Operation(summary = "Add product", description = "Allow Admins to add products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<String> registerProduct(@Valid @RequestBody CreateProductDto productDto) {
        product_service.registerProduct(productDto);
        return ResponseEntity.ok("Product has been registered successfully");
    }

    @Operation(summary = "Delete product", description = "Allow Admins to delete product")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long Id) {
        product_service.deleteProduct(Id);
        return ResponseEntity.ok("Product deleted successfully!");
    }

    @Operation(summary = "View all Products", description = "Allow Admins to view all stored products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Products>> viewAllProducts() {
        List<Products> products = product_service.viewAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "View one product", description = "Allows Admins to view all products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{Id}")
    public ResponseEntity<ProductDto> viewProduct(@PathVariable Long Id) {
        ProductDto product = product_service.viewProduct(Id);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @Operation(summary = "Update products", description = "Allow Admins to Update products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{Id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Long Id, @Valid @RequestBody UpdateProductDto product) {
        var updatedProduct = product_service.updateProduct(Id, product);
        return new ResponseEntity<>(updatedProduct , HttpStatus.OK);
    }

    @Operation(summary = "Mark product as featured", description = "Allow Admins to mark a product as featured")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{Id}/featured")
    public ResponseEntity<Products> updateFeaturedStatus(@PathVariable Long Id, @RequestParam boolean featured) {
        Products updatedProduct = product_service.markAsFeatured(Id, featured);
        return ResponseEntity.ok(updatedProduct);
    }

    /*
    @Operation(summary = "View all categories of stored products", description = "Allow admins to view all categories of stored products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = product_service.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
     */
}


