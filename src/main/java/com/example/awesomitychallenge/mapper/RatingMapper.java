package com.example.awesomitychallenge.mapper;

import com.example.awesomitychallenge.dto.RatingDto;
import com.example.awesomitychallenge.entities.ProductRatings;

public class RatingMapper {

    public static ProductRatings map(RatingDto ratingDto) {
        return new ProductRatings(ratingDto.getOrderId(), ratingDto.getRating());
    }

    public static RatingDto map(ProductRatings rating) {
        return new RatingDto(rating.getProduct().getProductName(), rating.getOrderId(), rating.getRating());
    }
}
