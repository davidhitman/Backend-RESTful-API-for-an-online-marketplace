package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.CreateAdminDto;
import com.example.awesomitychallenge.dto.CreateUserDto;
import com.example.awesomitychallenge.dto.UpdateUserDto;
import com.example.awesomitychallenge.dto.UserDto;
import com.example.awesomitychallenge.entities.AuthenticationResponse;
import com.example.awesomitychallenge.entities.Users;
import org.springframework.data.domain.Page;


public interface UserService {
    UserDto userSignUp(CreateUserDto userDto);

    AuthenticationResponse login(String email, String password);

    void deleteUser(Long id);

    UserDto updateUser(Long id, UpdateUserDto updatedUser);

    Page<Users> viewAllUsers(int offset, int pageSize);

    UserDto adminSignUp(CreateAdminDto userDto);
}
