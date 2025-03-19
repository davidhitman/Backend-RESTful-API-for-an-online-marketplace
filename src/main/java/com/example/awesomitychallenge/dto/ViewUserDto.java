package com.example.awesomitychallenge.dto;


import com.example.awesomitychallenge.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewUserDto {
    private String firstName;

    private String lastName;

    private String password;

    private String phoneNumber;

    private String address;

    private Role role;
}
