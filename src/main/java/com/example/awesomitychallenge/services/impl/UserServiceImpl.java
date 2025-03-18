package com.example.awesomitychallenge.services.impl;

import com.example.awesomitychallenge.dto.CreateAdminDto;
import com.example.awesomitychallenge.dto.CreateUserDto;
import com.example.awesomitychallenge.dto.UpdateUserDto;
import com.example.awesomitychallenge.dto.UserDto;
import com.example.awesomitychallenge.entities.AuthenticationResponse;
import com.example.awesomitychallenge.entities.Role;
import com.example.awesomitychallenge.entities.Users;
import com.example.awesomitychallenge.mapper.UserMapper;
import com.example.awesomitychallenge.repositories.UserRepository;
import com.example.awesomitychallenge.services.JwtService;
import com.example.awesomitychallenge.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public  class UserServiceImpl implements UserService {

    private final JwtService jwtService;
    // private variables to be used
    private UserRepository users_repository;
    private JavaMailSender mail_sender;
    private PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public boolean userSignUp(CreateUserDto userDto) {

        Users user = UserMapper.map(userDto);
        SimpleMailMessage message = new SimpleMailMessage();
        Optional<Users> existingUser = users_repository.findByEmail(user.getEmail());

        if (existingUser.isEmpty()) {
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            users_repository.save(user);
            message.setTo(user.getEmail());
            message.setSubject("Thanks For Signing Up");
            message.setText("Hello " + user.getFirstName() + ",\n\nThank you for registering!\n\nBest Regards,\nTeam");
            mail_sender.send(message);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public AuthenticationResponse login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );
        var user = users_repository.findByEmail(email).orElse(null);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        Optional<Users> getUser = users_repository.findById(id);
        getUser.ifPresent(users -> users_repository.delete(users));
    }

    @Override
    public void updateUser(Long id, UpdateUserDto users) {
        Optional<Users> existingUser = users_repository.findById(id);
        String encodePassword = passwordEncoder.encode(users.getPassword());
        users.setPassword(encodePassword);
        if (existingUser.isPresent()) {
            Users user = existingUser.get();
            user.setFirstName(users.getFirstName());
            user.setLastName(users.getLastName());
            user.setEmail(users.getEmail());
            user.setPassword(users.getPassword());
            user.setPhoneNumber(users.getPhoneNumber());
            user.setAddress(users.getAddress());
            users_repository.save(user);
        } else {
            throw new RuntimeException("User with email " + id + " not found.");
            }

    }
    @Override
    public List<UserDto> viewAllUsers(int page, int size) {

        var usersPage = users_repository.findAllUsers(PageRequest.of(page, size));
        var retrievedUsers = usersPage.getContent();
        List<UserDto> users = new ArrayList<>();
        for (Users retrievedUser : retrievedUsers) {
            var user = UserMapper.map(retrievedUser);
            users.add(user);
        }
        return(users);
    }

    @Override
    public boolean adminSignUp(CreateAdminDto adminDto){
        Users user = UserMapper.map(adminDto);
        SimpleMailMessage messages = new SimpleMailMessage();
        Optional<Users> existingUser = users_repository.findByEmail(user.getEmail());

        if (existingUser.isEmpty() || existingUser.get().getRole().equals(Role.USER)) {
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            users_repository.save(user);
            messages.setTo(user.getEmail());
            messages.setSubject("Thanks For Signing Up");
            messages.setText("Hello " + user.getFirstName() + ",\n\nThank you for registering!\n\n You Have been Registered as an Admin\n\n Best Regards,\nTeam");
            mail_sender.send(messages);
            return true;
        }else{
            return false;
        }
    }
}






