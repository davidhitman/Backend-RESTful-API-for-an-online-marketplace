package com.example.awesomitychallenge.repositories;

import com.example.awesomitychallenge.dto.CreateUserDto;
import com.example.awesomitychallenge.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    @Query(value = "Select p from Users p")
    Page<Users> findAllUsers(Pageable pageable);


}
