package com.example.awesomitychallenge.mapper;


import com.example.awesomitychallenge.dto.CreateAdminDto;
import com.example.awesomitychallenge.dto.CreateUserDto;
import com.example.awesomitychallenge.dto.UserDto;
import com.example.awesomitychallenge.entities.Role;
import com.example.awesomitychallenge.entities.Users;

public class UserMapper {

    // maps the Userdto to the User
    public static UserDto map(Users users) {
        return new UserDto(users.getId(), users.getFirstName(), users.getLastName(), users.getEmail(), users.getPassword(), users.getPhoneNumber(), users.getAddress(), users.getRole());
    }

    public static Users map(CreateUserDto userDto) {
        return new Users(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), userDto.getPassword(), userDto.getPhoneNumber(), userDto.getAddress(), Role.ADMIN);
    }

    public static Users map(CreateAdminDto adminDto) {
        return new Users(adminDto.getFirstName(), adminDto.getLastName(), adminDto.getEmail(), adminDto.getPassword(), adminDto.getPhoneNumber(), adminDto.getAddress(), Role.ADMIN);
    }

}