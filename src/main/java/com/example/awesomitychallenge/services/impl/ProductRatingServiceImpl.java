package com.example.awesomitychallenge.services.impl;


import com.example.awesomitychallenge.dto.RatingDto;
import com.example.awesomitychallenge.entities.ProductRatings;
import com.example.awesomitychallenge.entities.Products;
import com.example.awesomitychallenge.entities.Users;
import com.example.awesomitychallenge.mapper.RatingMapper;
import com.example.awesomitychallenge.repositories.OrderRepository;
import com.example.awesomitychallenge.repositories.ProductRatingRepository;
import com.example.awesomitychallenge.repositories.ProductRepository;
import com.example.awesomitychallenge.services.ProductRatingService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProductRatingServiceImpl implements ProductRatingService {
    private final ProductRatingRepository productRatingRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public Long getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Users) {
            return ((Users) principal).getUserId();
        }
        return null;
    }


    @Override
    public ProductRatings rateProduct(RatingDto productRatings) {
        var userId = getAuthenticatedUserId();
        orderRepository.findById(productRatings.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Products products= productRepository.findByProductName(productRatings.getProductName())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductRatings productRating = RatingMapper.map(productRatings);
        productRating.setUserId(userId);
        productRating.setOrderId(productRatings.getOrderId());
        productRating.setRating(productRating.getRating());
        productRating.setProduct(products);
        return productRatingRepository.save(productRating);
    }
}
