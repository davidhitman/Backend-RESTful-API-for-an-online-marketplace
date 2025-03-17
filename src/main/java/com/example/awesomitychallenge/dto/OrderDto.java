package com.example.awesomitychallenge.dto;



import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class OrderDto {

    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "Order status cannot be blank")
    private String orderStatus;
}