package com.example.awesomitychallenge.repositories;

import com.example.awesomitychallenge.entities.Category;
import com.example.awesomitychallenge.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryById(Long categoryId);
}
