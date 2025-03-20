package com.example.awesomitychallenge.services.impl;

import com.example.awesomitychallenge.dto.CreateAdminDto;
import com.example.awesomitychallenge.dto.CreateUserDto;
import com.example.awesomitychallenge.dto.UpdateUserDto;
import com.example.awesomitychallenge.dto.UserDto;
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

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtService jwtService;
    // private variables to be used
    private UserRepository userRepository;
    private JavaMailSender mailSender;
    private PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public UserDto userSignUp(CreateUserDto userDto) {

        Users user = UserMapper.map(userDto);
        SimpleMailMessage message = new SimpleMailMessage();
        Optional<Users> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isEmpty()) {
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            userRepository.save(user);
            message.setTo(user.getEmail());
            message.setSubject("Thanks For Signing Up");
            message.setText("Hello " + user.getFirstName() + ",\n\nThank you for registering!\n\nBest Regards,\nTeam");
            mailSender.send(message);
            return UserMapper.map(user);
        } else {
            throw new RuntimeException("User already exists");
        }
    }

    @Override
    public String login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        var user = userRepository.findByEmail(email).orElse(null);
        return jwtService.generateToken(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        Optional<Users> getUser = userRepository.findById(id);
        getUser.ifPresent(users -> userRepository.delete(users));
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserDto users) {
        Optional<Users> existingUser = userRepository.findById(id);
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
            userRepository.save(user);
            return UserMapper.map(user);
        } else {
            throw new RuntimeException("User with email " + id + " not found.");
        }

    }

    @Override
    public Page<Users> viewAllUsers(int offset, int pageSize) {
        return userRepository.findAllUsers(PageRequest.of(offset, pageSize));
    }

    @Override
    public UserDto adminSignUp(CreateAdminDto adminDto) {
        Users user = UserMapper.map(adminDto);
        SimpleMailMessage messages = new SimpleMailMessage();
        Optional<Users> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isEmpty() || existingUser.get().getRole().equals(Role.USER)) {
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            userRepository.save(user);
            messages.setTo(user.getEmail());
            messages.setSubject("Thanks For Signing Up");
            messages.setText("Hello " + user.getFirstName() + ",\n\nThank you for registering!\n\n You Have been Registered as an Admin\n\n Best Regards,\nTeam");
            mailSender.send(messages);
            return UserMapper.map(user);
        } else {
            throw new RuntimeException("Admin already exists");
        }
    }
}






