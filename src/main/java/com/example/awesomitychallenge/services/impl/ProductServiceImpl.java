package com.example.awesomitychallenge.services.impl;

import com.example.awesomitychallenge.dto.CreateProductDto;
import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.dto.UpdateProductDto;
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

import java.util.List;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Override
    public void registerProduct(CreateProductDto productDto) {
        Long categoryId = productDto.getCategoryId();
        var category = categoryRepository.findCategoryById(categoryId);
        Products product = ProductMapper.map(productDto);
        product.setCategory(category.getCategory());
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteProduct(Long Id) {
        var product = productRepository.findById(Id);
        if (product.isPresent()) {
            productRepository.deleteById(Id);
        }else{
            throw new RuntimeException("Product with Id " + Id + " not found.");
        }

    }

    @Override
    public List<Products> viewAllProducts(String categorySearch, int page, int size) {
        if (categorySearch != null && !categorySearch.isEmpty()) {
            var productPage = productRepository.findByCategory(categorySearch, PageRequest.of(page, size));
            return productPage.getContent();

        }else{
            var productPage = productRepository.findAll(PageRequest.of(page, size));
            return productPage.getContent();
        }
    }

    @Override
    public ProductDto viewProduct(Long id) {
        var product = productRepository.findById(id);
        if (product.isPresent()) {
            return ProductMapper.map(product.get());
        }else{
            throw new RuntimeException("Product Not Found");
        }
    }

    @Override
    public Products updateProduct(Long Id, UpdateProductDto updatedProduct) {
        var existingProduct = productRepository.findById(Id);
        var categoryId = updatedProduct.getCategoryId();
        var category = categoryRepository.findCategoryById(categoryId);
        if (existingProduct.isPresent()) {
            var product = existingProduct.get();
            product.setProductName(updatedProduct.getProductName());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            product.setCategory(category.getCategory());
            return productRepository.save(product);
        }else {
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








