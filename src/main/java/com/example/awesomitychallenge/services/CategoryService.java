package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.CreateCategoryDto;
import com.example.awesomitychallenge.dto.UpdateCategoryDto;
import com.example.awesomitychallenge.dto.ViewCategoryDto;
import org.springframework.data.domain.Page;

public interface CategoryService {
    void addCategory(CreateCategoryDto category);
    void deleteCategory(Long id);
    ViewCategoryDto updateCategory (Long id, UpdateCategoryDto categoryDto);
    Page<ViewCategoryDto> viewCategory(int offset, int pageSize);
}
