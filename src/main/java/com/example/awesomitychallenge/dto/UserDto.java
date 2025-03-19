// data that is passed between client and server
package com.example.awesomitychallenge.dto;

import com.example.awesomitychallenge.entities.Role;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private String address;

    private Role role;

}

