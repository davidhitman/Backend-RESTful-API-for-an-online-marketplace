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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    @Override
    public List<ProductDto> browserByCategory(String category) {
        var products = productRepository.findByCategory(category);
        if (products != null) {
            return products.stream().map(ProductMapper::map).collect(Collectors.toList());
        }else{
            throw new RuntimeException("Product Not Found");
        }
    }

    @Transactional
    @Override
    public void deleteProduct(Long Id) {
        var product = productRepository.findById(Id);
        if (product.isPresent()) {
            throw new RuntimeException("Product with name " + Id + " not found.");
        }
        productRepository.deleteById(Id);
    }

    @Override
    public List<Products> viewAllProducts() {
        return productRepository.findAllProducts();
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
        if (existingProduct.isPresent()) {
            var product = existingProduct.get();
            product.setProductName(updatedProduct.getProductName());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            product.setCategory(updatedProduct.getCategory());
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

    @Override
    public List<String> getAllCategories() {
        return productRepository.findAllCategory();
    }

    @Override
    public void updateCategory(Long id, String new_category) {
        Optional<Products> product = productRepository.findById(id);
        if (product.isPresent()) {
            Products updatedProduct = product.get();
            updatedProduct.setCategory(new_category);
            productRepository.save(updatedProduct);
        }else{
            throw new RuntimeException("Product not found");
        }
    }
}








