package com.example.awesomitychallenge.repositories;

import com.example.awesomitychallenge.entities.Category;
import com.example.awesomitychallenge.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    Page<Products> findByCategory(Category category, Pageable pageable);

    Optional<Products> findByProductName(String productName);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Products p WHERE p.productName = :product_name " +
            "AND p.category.id = :category_id " +
            "AND p.price = :price")
    boolean existsByProductNameAndCategoryAndPrice(
            @Param("product_name") String product_name,
            @Param("category_id") Long categoryId,
            @Param("price") Long price
    );
}




