package com.example.awesomitychallenge.controllers;

import com.example.awesomitychallenge.dto.CreateProductDto;
import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.dto.UpdateProductDto;
import com.example.awesomitychallenge.entities.Products;
import com.example.awesomitychallenge.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Product Controller", description = "Handles Products")
@AllArgsConstructor
@RestController
@RequestMapping("/products")

public class ProductController {
    private ProductService product_service;

    @Operation(summary = "Add product", description = "Allow Admins to add products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<GenericResponse<ProductDto>> registerProduct(@RequestBody CreateProductDto productDto) {
        ProductDto product = product_service.registerProduct(productDto);
        return ResponseEntity.ok(new GenericResponse<>("Product Successfully Registered", product));
    }

    @Operation(summary = "Delete product", description = "Allow Admins to delete product")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<String>> deleteProduct(@PathVariable Long id) {
        product_service.deleteProduct(id);
        return ResponseEntity.ok(new GenericResponse<>("Product deleted successfully", "Deleted ID" + id));
    }

    @Operation(summary = "View all Products", description = "Allow Admins to view all stored products")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<GenericResponse<Page<Products>>> viewAllProducts(@RequestParam(required = false) String search, @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "5") int pageSize) {
        Page<Products> products = product_service.viewAllProducts(search, offset, pageSize);
        return ResponseEntity.ok(new GenericResponse<>("All Stored Products", products));
    }

    @Operation(summary = "View one product", description = "Allows Admins to view all products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<ProductDto>> viewProduct(@PathVariable Long id) {
        ProductDto product = product_service.viewProduct(id);
        return ResponseEntity.ok(new GenericResponse<>("The product with id" + id + "is:", product));

    }

    @Operation(summary = "Update product", description = "Allow Admins to Update products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<Products>> updateProduct(@PathVariable Long id, @RequestBody UpdateProductDto product) {
        var updatedProduct = product_service.updateProduct(id, product);
        return ResponseEntity.ok(new GenericResponse<>("Product updated successfully", updatedProduct));
    }

    @Operation(summary = "Mark product as featured", description = "Allow Admins to mark a product as featured")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}/featured")
    public ResponseEntity<GenericResponse<Products>> updateFeaturedStatus(@PathVariable Long id, @RequestParam boolean featured) {
        Products updatedProduct = product_service.markAsFeatured(id, featured);
        return ResponseEntity.ok(new GenericResponse<>("The Product is now featured", updatedProduct));
    }
}


