package com.example.awesomitychallenge.repositories;


import com.example.awesomitychallenge.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    @Query("SELECT u FROM Users u")
    Page<Users> findAllUsers(Pageable pageable);


}
