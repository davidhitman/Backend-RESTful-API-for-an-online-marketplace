package com.example.awesomitychallenge.services.impl;

import com.example.awesomitychallenge.entities.Category;
import com.example.awesomitychallenge.repositories.CategoryRepository;
import com.example.awesomitychallenge.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public void addCategory(String category) {
        var existCategory = categoryRepository.findCategoryByCategory(category);
        if (existCategory.isEmpty()) {
            Category categoryEntity = new Category();
            categoryEntity.setCategory(category);
            categoryRepository.save(categoryEntity);
        } else {
            throw new RuntimeException("Category already exists");
        }
    }

}
