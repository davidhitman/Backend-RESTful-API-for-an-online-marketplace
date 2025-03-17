package com.example.awesomitychallenge.mapper;

import com.example.awesomitychallenge.dto.OrderDto;
import com.example.awesomitychallenge.entities.Orders;


public class OrderMapper {
    public static OrderDto mapToOrderdto (Orders orders) {
        return new OrderDto(
                orders.getId(),
                orders.getProductName(),
                orders.getCategory(),
                orders.getQuantity(),
                orders.getFirstName(),
                orders.getLastName(),
                orders.getEmail(),
                orders.getPhoneNumber(),
                orders.getAddress(),
                orders.getOrderStatus()
        );
    }

    public static Orders mapToOrder(OrderDto orderdto) {
        return new Orders(
                orderdto.getId(),
                orderdto.getProductName(),
                orderdto.getCategory(),
                orderdto.getQuantity(),
                orderdto.getFirstName(),
                orderdto.getLastName(),
                orderdto.getEmail(),
                orderdto.getPhoneNumber(),
                orderdto.getAddress(),
                orderdto.getOrderStatus()
        );
    }
}