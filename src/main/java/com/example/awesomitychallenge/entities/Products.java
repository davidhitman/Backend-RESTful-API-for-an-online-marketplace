package com.example.awesomitychallenge.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Products {
    @Id // id is the primary key
    // auto generation (auto incrementation of the id primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String productName;

    @Column(name = "price")
    private Long price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name="category")
    private String category;

    @Column(name = "featured")
    private boolean featured;

    public Products(String productName, Long price, int quantity, boolean featured) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.featured = featured;
    }
}
