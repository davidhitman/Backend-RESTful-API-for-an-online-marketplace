
package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.CreateProductDto;
import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.dto.UpdateProductDto;
import com.example.awesomitychallenge.entities.Products;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void registerProduct(CreateProductDto productdto);
    List<ProductDto> browserByCategory(String category);
    void deleteProduct(Long Id);
    List<Products> viewAllProducts();
    ProductDto viewProduct(Long Id);
    Products updateProduct(Long Id, UpdateProductDto updatedProduct);
    Products markAsFeatured(Long productId, boolean isFeatured);
    List<String> getAllCategories();
    void updateCategory(Long id, String new_category);
}


