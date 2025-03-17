package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.CreateAdminDto;
import com.example.awesomitychallenge.dto.CreateUserDto;
import com.example.awesomitychallenge.dto.UpdateUserDto;
import com.example.awesomitychallenge.dto.UserDto;
import com.example.awesomitychallenge.entities.AuthenticationResponse;
import com.example.awesomitychallenge.entities.Users;

import java.util.List;

public interface UserService {
    boolean userSignUp(CreateUserDto userDto);
    AuthenticationResponse login(String email, String password);
    void deleteUser(String email);
    void updateUser(String email, UpdateUserDto updatedUser);
    List<Users> viewAllUsers();
    boolean adminSignUp(CreateAdminDto userDto);
}
