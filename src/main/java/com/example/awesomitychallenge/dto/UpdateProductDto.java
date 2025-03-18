package com.example.awesomitychallenge.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UpdateProductDto {
    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be a positive value")
    private Long price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be a positive value")
    private int quantity;

    @NotNull(message = "Category cannot be blank")
    private Long  categoryId;
}
