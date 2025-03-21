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

public class RatingDto {

    @NotBlank(message = "ProductId cannot be blank")
    private String productName;

    @NotNull(message = "OrderId cannot be blank")
    private Long orderId;

    @NotNull(message="ratings cannot be blank")
    private Integer rating;

}
