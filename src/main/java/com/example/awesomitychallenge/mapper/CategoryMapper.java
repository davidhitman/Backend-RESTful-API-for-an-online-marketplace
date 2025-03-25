package com.example.awesomitychallenge.mapper;

import com.example.awesomitychallenge.dto.CreateCategoryDto;
import com.example.awesomitychallenge.dto.ViewCategoryDto;
import com.example.awesomitychallenge.entities.Category;

public class CategoryMapper {
    public static Category map(CreateCategoryDto categoryDto) {
        return new Category(categoryDto.getName());
    }

    public static ViewCategoryDto map(Category category) {
        return new ViewCategoryDto (category.getId(), category.getName());
    }
}
