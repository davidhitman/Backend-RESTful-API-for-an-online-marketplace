package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.CreateProductDto;
import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.dto.UpdateProductDto;
import org.springframework.data.domain.Page;


public interface ProductService {
    ProductDto registerProduct(CreateProductDto productDto);

    void deleteProduct(Long Id);

    Page<ProductDto> viewAllProducts(String categorySearch, int offset, int pageSize);

    ProductDto viewProduct(Long Id);

    ProductDto updateProduct(Long Id, UpdateProductDto updatedProduct);

    ProductDto markAsFeatured(Long productId, boolean isFeatured);
}


