package com.example.awesomitychallenge.repositories;

import com.example.awesomitychallenge.entities.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    Page<Orders> findByEmail(@Param("email") String email, Pageable pageable);
}




