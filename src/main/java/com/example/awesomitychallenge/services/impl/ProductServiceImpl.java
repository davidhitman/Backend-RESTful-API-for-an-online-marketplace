package com.example.awesomitychallenge.services.impl;

import com.example.awesomitychallenge.dto.CreateProductDto;
import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.dto.UpdateProductDto;
import com.example.awesomitychallenge.entities.Category;
import com.example.awesomitychallenge.entities.Products;
import com.example.awesomitychallenge.mapper.ProductMapper;
import com.example.awesomitychallenge.repositories.CategoryRepository;
import com.example.awesomitychallenge.repositories.ProductRepository;
import com.example.awesomitychallenge.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto registerProduct(CreateProductDto productDto) {
        Long categoryId = productDto.getCategoryId();
        var category = categoryRepository.findCategoryById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        boolean exists = productRepository.existsByProductNameAndCategoryAndPrice(productDto.getProductName(), category.getId(), productDto.getPrice());
        if (!exists) {
            Products product = ProductMapper.map(productDto, category);
            productRepository.save(product);
            return ProductMapper.map(product);
        } else {
            throw new RuntimeException("Product with name " + productDto.getProductName() + " already exists.");
        }
    }

    @Transactional
    @Override
    public void deleteProduct(Long Id) {
        var product = productRepository.findById(Id);
        if (product.isPresent()) {
            productRepository.deleteById(Id);
        } else {
            throw new RuntimeException("Product with Id " + Id + " not found.");
        }

    }

    @Override
    public Page<Products> viewAllProducts(String categoryName, int page, int size) {
        if (categoryName != null && !categoryName.isEmpty()) {
            Optional<Category> categoryOpt = categoryRepository.findByName(categoryName);
            if (categoryOpt.isPresent()) {
                return productRepository.findByCategory(categoryOpt.get(), PageRequest.of(page, size));
            } else{
                throw new RuntimeException("Category not found.");
            }
        } else {
            return productRepository.findAll(PageRequest.of(page, size));
        }
    }

    @Override
    public ProductDto viewProduct(Long id) {
        var product = productRepository.findById(id);
        if (product.isPresent()) {
            return ProductMapper.map(product.get());
        } else {
            throw new RuntimeException("Product Not Found");
        }
    }

    @Override
    public Products updateProduct(Long Id, UpdateProductDto updatedProduct) {
        var existingProduct = productRepository.findById(Id);
        var categoryId = updatedProduct.getCategoryId();
        var categories = categoryRepository.findCategoryById(categoryId);
        categories.orElseThrow(() -> new RuntimeException("Category not found"));
        if (existingProduct.isPresent()) {
            var product = existingProduct.get();
            product.setProductName(updatedProduct.getProductName());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            product.setCategory(categories.get());
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Product with name " + Id + " not found.");
        }
    }

    @Override
    public Products markAsFeatured(Long productId, boolean isFeatured) {
        return productRepository.findById(productId).map(product -> {
            product.setFeatured(true);
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}








