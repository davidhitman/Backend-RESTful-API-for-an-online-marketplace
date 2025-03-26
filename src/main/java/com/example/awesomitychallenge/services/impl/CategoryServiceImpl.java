package com.example.awesomitychallenge.services.impl;

import com.example.awesomitychallenge.dto.CreateCategoryDto;
import com.example.awesomitychallenge.dto.UpdateCategoryDto;
import com.example.awesomitychallenge.dto.ViewCategoryDto;
import com.example.awesomitychallenge.entities.Category;
import com.example.awesomitychallenge.entities.Orders;
import com.example.awesomitychallenge.entities.Products;
import com.example.awesomitychallenge.mapper.CategoryMapper;
import com.example.awesomitychallenge.repositories.CategoryRepository;
import com.example.awesomitychallenge.repositories.OrderRepository;
import com.example.awesomitychallenge.repositories.ProductRepository;
import com.example.awesomitychallenge.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public void addCategory(CreateCategoryDto category) {
        Category categoryEntity = CategoryMapper.map(category);
        var existCategory = categoryRepository.findByName(categoryEntity.getName());
        if (existCategory.isEmpty()) {
            categoryEntity.setName(category.getName());
            categoryRepository.save(categoryEntity);
        } else {
            throw new RuntimeException("Category already exists");
        }
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        }else{
            throw new RuntimeException("Category with id " + id + " not found.");
        }
    }


    @Override
    public ViewCategoryDto updateCategory(Long id, UpdateCategoryDto categoryDto) {
        Optional<Category> categories = categoryRepository.findById(id);
        if (categories.isPresent()) {
            var category = categories.get();
            category.setName(categoryDto.getName());
            categoryRepository.save(category);
            List<Products> products = category.getProducts();
            for (Products product : products) {
                product.setCategory(category);
                productRepository.save(product);
                List<Orders> orders = product.getOrders();
                for (Orders order : orders) {
                    order.setCategory(category.getName());
                    orderRepository.save(order);
                }
            }
            return CategoryMapper.map(category);
        } else{
            throw new RuntimeException("Category not found");
        }
    }

    @Override
    public Page<ViewCategoryDto> viewCategory(int offset, int pageSize) {
         var categoryPage = categoryRepository.findAllCategories(PageRequest.of(offset, pageSize));
         return categoryPage.map(CategoryMapper::map);
    }

}
