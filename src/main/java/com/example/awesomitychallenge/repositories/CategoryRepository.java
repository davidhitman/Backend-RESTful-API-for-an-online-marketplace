package com.example.awesomitychallenge.repositories;

import com.example.awesomitychallenge.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryById(Long categoryId);

    Optional<Category> findByName(String name);
}
