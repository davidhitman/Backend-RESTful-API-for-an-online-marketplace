package com.example.awesomitychallenge.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "featured")
    private boolean featured;

    // Many Products belong to one Category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // One Product can have multiple Orders
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders;

    @OneToMany (mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductRatings> productRatings = new ArrayList<>();


    public Products(String productName, Long price, int quantity, boolean featured, Category category) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.featured = featured;
        this.category = category;
    }
}
