package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.CreateAdminDto;
import com.example.awesomitychallenge.dto.CreateUserDto;
import com.example.awesomitychallenge.dto.UpdateUserDto;
import com.example.awesomitychallenge.dto.UserDto;
import com.example.awesomitychallenge.entities.AuthenticationResponse;
import com.example.awesomitychallenge.entities.Users;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    boolean userSignUp(CreateUserDto userDto);
    AuthenticationResponse login(String email, String password);
    void deleteUser(Long id);
    void updateUser(Long id, UpdateUserDto updatedUser);
    List<UserDto> viewAllUsers(int page, int size);
    boolean adminSignUp(CreateAdminDto userDto);
}
