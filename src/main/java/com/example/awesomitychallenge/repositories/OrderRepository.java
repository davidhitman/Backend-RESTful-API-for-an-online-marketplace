package com.example.awesomitychallenge.repositories;

import com.example.awesomitychallenge.entities.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Page<Orders> findByEmail(String email, Pageable pageable);
}




