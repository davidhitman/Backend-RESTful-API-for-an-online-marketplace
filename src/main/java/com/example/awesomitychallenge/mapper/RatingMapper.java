package com.example.awesomitychallenge.mapper;

import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.dto.RatingDto;
import com.example.awesomitychallenge.entities.ProductRatings;
import com.example.awesomitychallenge.entities.Products;

public class RatingMapper {
    public static ProductRatings map(RatingDto ratingDto) {
        return new ProductRatings(ratingDto.getOrderId(), ratingDto.getRating());
    }
}
