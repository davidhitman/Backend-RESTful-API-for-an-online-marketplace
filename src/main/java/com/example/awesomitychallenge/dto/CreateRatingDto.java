package com.example.awesomitychallenge.dto;


import jakarta.validation.constraints.Max;
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

public class CreateRatingDto {

    @NotBlank(message = "ProductId cannot be blank")
    private String productName;

    @NotNull(message = "OrderId cannot be blank")
    private Long orderId;

    @NotNull(message="ratings cannot be blank")
    @Min(value = 0, message="Rating must be at least 0")
    @Max(value = 10, message="Rating must be at most 10")
    private Integer rating;
}
