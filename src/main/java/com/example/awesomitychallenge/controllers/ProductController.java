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
import org.springframework.data.domain.Page;
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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        product_service.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully!");
    }

    @Operation(summary = "View all Products", description = "Allow Admins to view all stored products")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<Products>> viewAllProducts(@RequestParam(required = false) String search, @RequestParam(defaultValue = "0")  int page, @RequestParam(defaultValue = "5") int size) {
        List<Products> products = product_service.viewAllProducts(search, page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "View one product", description = "Allows Admins to view all products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> viewProduct(@PathVariable Long id) {
        ProductDto product = product_service.viewProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @Operation(summary = "Update product", description = "Allow Admins to Update products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductDto product) {
        var updatedProduct = product_service.updateProduct(id, product);
        return new ResponseEntity<>(updatedProduct , HttpStatus.OK);
    }

    @Operation(summary = "Mark product as featured", description = "Allow Admins to mark a product as featured")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}/featured")
    public ResponseEntity<Products> updateFeaturedStatus(@PathVariable Long id, @RequestParam boolean featured) {
        Products updatedProduct = product_service.markAsFeatured(id, featured);
        return ResponseEntity.ok(updatedProduct);
    }
}


