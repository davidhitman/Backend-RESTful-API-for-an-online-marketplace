package com.example.awesomitychallenge.services.impl;

import com.example.awesomitychallenge.dto.CreateProductDto;
import com.example.awesomitychallenge.dto.ProductDto;
import com.example.awesomitychallenge.dto.UpdateProductDto;
import com.example.awesomitychallenge.entities.Category;
import com.example.awesomitychallenge.entities.Orders;
import com.example.awesomitychallenge.entities.Products;
import com.example.awesomitychallenge.mapper.ProductMapper;
import com.example.awesomitychallenge.repositories.CategoryRepository;
import com.example.awesomitychallenge.repositories.OrderRepository;
import com.example.awesomitychallenge.repositories.ProductRepository;
import com.example.awesomitychallenge.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final OrderRepository orderRepository;
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


    @Override
    public void deleteProduct(Long Id) {
        var product = productRepository.findById(Id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
        } else {
            throw new RuntimeException("Product with Id " + Id + " not found.");
        }

    }

    @Override
    public Page<ProductDto> viewAllProducts(String categoryName, int page, int size) {
        Page<Products> productsPage;
        if (categoryName != null && !categoryName.isEmpty()) {
            Optional<Category> categoryOpt = categoryRepository.findByName(categoryName);
            if (categoryOpt.isPresent()) {
                productsPage = productRepository.findByCategory(categoryOpt.get(), PageRequest.of(page, size));
            }else{
                throw new RuntimeException("Category not found.");
            }
        }else{
            productsPage = productRepository.findAll(PageRequest.of(page, size));
        }
        return productsPage.map(ProductMapper::map);
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
    public ProductDto updateProduct(Long Id, UpdateProductDto updatedProduct) {
        var existingProduct = productRepository.findById(Id);
        if (existingProduct.isPresent()) {
            var categoryId = updatedProduct.getCategoryId();
            var categories = categoryRepository.findCategoryById(categoryId);
            if (categories.isPresent()) {
                var product = existingProduct.get();
                product.setProductName(updatedProduct.getProductName());
                product.setPrice(updatedProduct.getPrice());
                product.setQuantity(updatedProduct.getQuantity());
                product.setCategory(categories.get());
                productRepository.save(product);

                List<Orders> orders = product.getOrders();
                for (Orders order : orders) {
                    order.setCategory(categories.get().getName());
                    order.setPrice(updatedProduct.getPrice());
                    order.setProductName(updatedProduct.getProductName());
                    order.setQuantity(updatedProduct.getQuantity());
                    orderRepository.save(order);
                }

                return ProductMapper.map(product);
            }else{
                throw new RuntimeException("Category not found.");
            }
        } else {
            throw new RuntimeException("Product with name " + Id + " not found.");
        }
    }

    @Override
    public ProductDto markAsFeatured(Long productId, boolean isFeatured) {
        var products = productRepository.findById(productId);
        if (products.isPresent()) {
            var product = products.get();
            product.setFeatured(true);
            productRepository.save(product);
            return ProductMapper.map(product);
        }else{
            throw new RuntimeException("Product with id " + productId + " not found.");
        }
    }
}








