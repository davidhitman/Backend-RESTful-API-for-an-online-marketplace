
package com.example.awesomitychallenge.repositories;

import com.example.awesomitychallenge.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    Optional<Products> findById(Long Id);
    Page<Products> findByCategory(String category, Pageable pageable);

    void deleteById(Long Id);
    @Query("Select category from Products")
    List<String> findAllCategory();
    @Query("Select p from Products p")
    List<Products> findAllProducts();
    Products findByProductName(String productName);
    Page<Products> findAll(Pageable pageable);
}




