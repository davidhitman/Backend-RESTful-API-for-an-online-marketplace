package com.example.awesomitychallenge.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {
    private String productName;

    private Long price;

    private int quantity;

    private Long categoryId;

}
