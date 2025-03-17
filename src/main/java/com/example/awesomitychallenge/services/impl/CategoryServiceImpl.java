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
        var saveCategory = new Category();
        saveCategory.setCategory(category);
        categoryRepository.save(saveCategory);
    }

}
