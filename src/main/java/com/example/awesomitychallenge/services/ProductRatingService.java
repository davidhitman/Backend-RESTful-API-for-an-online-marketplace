package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.CreateRatingDto;
import com.example.awesomitychallenge.dto.ViewRatingDto;
import org.springframework.data.domain.Page;

public interface ProductRatingService {
    void rateProduct(CreateRatingDto productRatings);
    Page<ViewRatingDto> viewProductRate(String productName, int page, int size);
}
