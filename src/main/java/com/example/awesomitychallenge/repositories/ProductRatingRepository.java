package com.example.awesomitychallenge.repositories;


import com.example.awesomitychallenge.entities.ProductRatings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRatings, Long> {
}
