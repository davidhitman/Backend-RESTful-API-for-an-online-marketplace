package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.RatingDto;
import com.example.awesomitychallenge.entities.ProductRatings;

public interface ProductRatingService {
    ProductRatings rateProduct(RatingDto productRatings);

}
