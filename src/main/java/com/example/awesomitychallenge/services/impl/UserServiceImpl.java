package com.example.awesomitychallenge.services.impl;

import com.example.awesomitychallenge.dto.*;
import com.example.awesomitychallenge.entities.Orders;
import com.example.awesomitychallenge.entities.Role;
import com.example.awesomitychallenge.entities.Users;
import com.example.awesomitychallenge.mapper.UserMapper;
import com.example.awesomitychallenge.repositories.OrderRepository;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtService jwtService;
    // private variables to be used
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private JavaMailSender mailSender;
    private PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public String getAuthenticatedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }

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
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return jwtService.generateToken(user);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<Users> getUser = userRepository.findById(id);
        if (getUser.isPresent()) {
            var email = getUser.get().getEmail();
            if (email.equals(getAuthenticatedUserEmail())) {
                throw new UsernameNotFoundException("You cannot delete a signed in user");
            }else{
                userRepository.deleteById(id);
            }
        } else{
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserDto users) {
        Optional<Users> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            Users user = existingUser.get();
            String encodePassword = passwordEncoder.encode(users.getPassword());
            users.setPassword(encodePassword);
            user.setFirstName(users.getFirstName());
            user.setLastName(users.getLastName());
            user.setEmail(users.getEmail());
            user.setPassword(users.getPassword());
            user.setPhoneNumber(users.getPhoneNumber());
            user.setAddress(users.getAddress());
            // changing user detains in orders
            List<Orders> orders = user.getOrders();
            for (Orders order : orders) {
                order.setFirstName(user.getFirstName());
                order.setLastName(user.getLastName());
                order.setEmail(user.getEmail());
                order.setPhoneNumber(user.getPhoneNumber());
                order.setAddress(user.getAddress());
                orderRepository.save(order);
            }
            userRepository.save(user);
            return UserMapper.map(user);
        } else {
            throw new RuntimeException("User with id " + id + " not found.");
        }

    }

    @Override
    public Page<UserDto> viewAllUsers(int offset, int pageSize) {
        Page<Users> usersPage;
        usersPage = userRepository.findAllUsers(PageRequest.of(offset, pageSize));
        return usersPage.map(UserMapper::map);
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






