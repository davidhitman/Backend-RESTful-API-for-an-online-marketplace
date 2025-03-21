package com.example.awesomitychallenge.services.impl;

import com.example.awesomitychallenge.dto.CreateCategoryDto;
import com.example.awesomitychallenge.entities.Category;
import com.example.awesomitychallenge.mapper.CategoryMapper;
import com.example.awesomitychallenge.repositories.CategoryRepository;
import com.example.awesomitychallenge.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public void addCategory(CreateCategoryDto category) {
        Category categoryEntity = CategoryMapper.map(category);
        var existCategory = categoryRepository.findByName(categoryEntity.getName());
        if (existCategory.isEmpty()) {
            categoryEntity.setName(category.getName());
            categoryRepository.save(categoryEntity);
        } else {
            throw new RuntimeException("Category already exists");
        }
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        }
    }


    @Override
    public Category updateCategory(Long id, CreateCategoryDto categoryDto) {
        Optional<Category> categories = categoryRepository.findById(id);
        if (categories.isPresent()) {
            Category category = CategoryMapper.map(categoryDto);
            category.setName(categoryDto.getName());
            return categoryRepository.save(category);
        } else{
            throw new RuntimeException("Category not found");
        }
    }




}
