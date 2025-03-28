package com.example.awesomitychallenge.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductDto {

    private Long id;

    private String productName;

    private Long price;

    private int quantity;

    private boolean featured;

    private String category;
}
