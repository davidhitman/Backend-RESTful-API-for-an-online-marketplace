
package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.CreateProductDto;
import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.dto.UpdateProductDto;
import com.example.awesomitychallenge.entities.Products;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void registerProduct(CreateProductDto productDto);
    void deleteProduct(Long Id);
    List<Products> viewAllProducts(String categorySearch, int page, int size);
    ProductDto viewProduct(Long Id);
    Products updateProduct(Long Id, UpdateProductDto updatedProduct);
    Products markAsFeatured(Long productId, boolean isFeatured);
}


