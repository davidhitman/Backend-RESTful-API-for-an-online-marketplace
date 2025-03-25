package com.example.awesomitychallenge.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class OrderDto {

    private Long id;

    private String productName;

    private String category;

    private int quantity;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String orderStatus;

    private Long price;

}