package com.example.awesomitychallenge.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ratings")

public class ProductRatings {
    @Id // id is the primary key
    // auto generation (auto incrementation of the id primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Products product;

    public ProductRatings(@NotNull(message="ratings cannot be blank") int rating) {
        this.rating = rating;
    }
}
