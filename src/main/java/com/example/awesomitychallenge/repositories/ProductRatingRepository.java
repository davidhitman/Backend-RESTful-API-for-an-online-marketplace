package com.example.awesomitychallenge.repositories;

import com.example.awesomitychallenge.entities.Orders;
import com.example.awesomitychallenge.entities.ProductRatings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRatingRepository extends JpaRepository<ProductRatings, Long> {
}
