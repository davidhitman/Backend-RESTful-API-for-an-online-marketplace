package com.example.awesomitychallenge.repositories;

import com.example.awesomitychallenge.entities.ProductRatings;
import com.example.awesomitychallenge.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRatings, Long> {
    @Query("SELECT pr FROM ProductRatings pr WHERE pr.product = :product")
    Optional<ProductRatings> findProductRating(@Param("product") Products product);

    @Query("SELECT pr FROM ProductRatings pr WHERE pr.product = :product ORDER BY pr.rating DESC")
    Page<ProductRatings> findRatingByProduct (@Param("product") Products product, Pageable pageable);
}
