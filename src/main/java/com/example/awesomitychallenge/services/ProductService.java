package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.CreateProductDto;
import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.dto.UpdateProductDto;
import com.example.awesomitychallenge.entities.Products;
import org.springframework.data.domain.Page;


public interface ProductService {
    ProductDto registerProduct(CreateProductDto productDto);

    void deleteProduct(Long Id);

    Page<Products> viewAllProducts(String categorySearch, int offset, int pageSize);

    ProductDto viewProduct(Long Id);

    Products updateProduct(Long Id, UpdateProductDto updatedProduct);

    Products markAsFeatured(Long productId, boolean isFeatured);
}


