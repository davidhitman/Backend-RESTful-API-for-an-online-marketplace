package com.example.awesomitychallenge.mapper;

import com.example.awesomitychallenge.dto.CreateCategoryDto;
import com.example.awesomitychallenge.entities.Category;

public class CategoryMapper {
    public static Category map(CreateCategoryDto categoryDto) {
        return new Category(categoryDto.getName());
    }
}
