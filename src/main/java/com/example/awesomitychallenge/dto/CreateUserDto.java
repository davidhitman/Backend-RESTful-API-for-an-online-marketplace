package com.example.awesomitychallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private String address;
}