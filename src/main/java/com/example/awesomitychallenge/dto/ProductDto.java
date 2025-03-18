package com.example.awesomitychallenge.dto;


import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be a positive value")
    private Long price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be a positive value")
    private int quantity;

    @NotBlank(message="featured cannot be blank")
    private boolean featured;

    @NotBlank(message = "feature cannot be blank")
    private String category;
}
