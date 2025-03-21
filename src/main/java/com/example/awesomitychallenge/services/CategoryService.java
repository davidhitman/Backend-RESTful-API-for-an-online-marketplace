package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.CreateCategoryDto;
import com.example.awesomitychallenge.entities.Category;

public interface CategoryService {
    void addCategory(CreateCategoryDto category);
    void deleteCategory(Long id);
    Category updateCategory (Long id, CreateCategoryDto categoryDto);
}
