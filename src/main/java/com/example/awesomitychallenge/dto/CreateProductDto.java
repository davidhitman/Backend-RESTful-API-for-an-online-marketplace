package com.example.awesomitychallenge.dto;


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
public class CreateProductDto {

    @NotBlank(message = "Product Name cannot be blank")
    private String productName;

    @NotNull(message = "Price cannot be Blank")
    private Long price;

    @NotNull(message = "Quantity cannot be Blank")
    private int quantity;

    @NotNull(message = "Category Id can not be null")
    private Long categoryId;

}
