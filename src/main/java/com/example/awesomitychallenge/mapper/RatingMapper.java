package com.example.awesomitychallenge.mapper;

import com.example.awesomitychallenge.dto.CreateRatingDto;
import com.example.awesomitychallenge.dto.ViewRatingDto;
import com.example.awesomitychallenge.entities.ProductRatings;

public class RatingMapper {

    public static ProductRatings map(CreateRatingDto createRatingDto) {
        return new ProductRatings(createRatingDto.getRating());
    }

    public static ViewRatingDto map(ProductRatings productRatings) {
        return new ViewRatingDto( productRatings.getProduct().getProductName(), productRatings.getRating());
    }
}
