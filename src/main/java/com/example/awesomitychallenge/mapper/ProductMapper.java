package com.example.awesomitychallenge.mapper;

import com.example.awesomitychallenge.dto.CreateProductDto;
import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.entities.Category;
import com.example.awesomitychallenge.entities.Products;


public class ProductMapper {
    public static ProductDto map(Products products) {
        return new ProductDto(products.getId(), products.getProductName(), products.getPrice(), products.getQuantity(), products.isFeatured(), products.getCategory().getName());
    }

    public static Products map(CreateProductDto productDto, Category category) {
        return new Products(productDto.getProductName(), productDto.getPrice(), productDto.getQuantity(), false, category);
    }
}
