package com.example.awesomitychallenge.services.impl;

import com.example.awesomitychallenge.dto.CreateRatingDto;
import com.example.awesomitychallenge.dto.ViewRatingDto;
import com.example.awesomitychallenge.entities.Orders;
import com.example.awesomitychallenge.entities.ProductRatings;
import com.example.awesomitychallenge.entities.Products;
import com.example.awesomitychallenge.entities.Users;
import com.example.awesomitychallenge.mapper.RatingMapper;
import com.example.awesomitychallenge.repositories.ProductRatingRepository;
import com.example.awesomitychallenge.repositories.ProductRepository;
import com.example.awesomitychallenge.repositories.UserRepository;
import com.example.awesomitychallenge.services.ProductRatingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductRatingServiceImpl implements ProductRatingService {
    private final ProductRatingRepository productRatingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Long getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Users) {
            return ((Users) principal).getUserId();
        }
        return null;
    }


    @Override
    public void rateProduct(CreateRatingDto productRatings) {
        var userId = getAuthenticatedUserId();
        var user = userRepository.findById(userId).orElseThrow();
        if (user.getOrders().isEmpty()){
            throw new RuntimeException("You have no orders");
        }else{
            var products= productRepository.findByProductName(productRatings.getProductName());
            for (Orders order : user.getOrders()){
                if (order.getId().equals(productRatings.getOrderId())){
                    if (products.isEmpty()){
                        throw new RuntimeException("Product does not exist");
                    } else {
                        ProductRatings productRating = RatingMapper.map(productRatings);
                        var storedProductRating = productRatingRepository.findProductRating(products.get());
                        if (storedProductRating.isEmpty()) {
                            productRating.setRating(productRating.getRating());
                            productRating.setProduct(products.get());
                            productRatingRepository.save(productRating);
                        } else{
                            var storedProductRatings = storedProductRating.get();
                            var rating = storedProductRating.get().getRating();
                            var newRating = (productRating.getRating()/10) + rating;
                            storedProductRatings.setRating(newRating);
                            productRatingRepository.save( storedProductRatings);
                        }

                    }
                }else{
                    throw new RuntimeException("You have no order with OrderId: " +" "+ productRatings.getOrderId());
                }
            }
        }
    }

    @Override
    public Page<ViewRatingDto> viewProductRate(String productName, int page, int size) {
        Page<ProductRatings> productsRatesPage;
        if (productName != null && !productName.isEmpty()) {
            Optional<Products> productOpt= productRepository.findByProductName(productName);
            if (productOpt.isPresent()) {
                productsRatesPage = productRatingRepository.findRatingByProduct(productOpt.get(), PageRequest.of(page, size));
            }else{
                throw new RuntimeException("Product not found");
            }
        }else{
            productsRatesPage = productRatingRepository.findAll(PageRequest.of(page, size));
        }
        return productsRatesPage.map(RatingMapper::map);
    }
}
