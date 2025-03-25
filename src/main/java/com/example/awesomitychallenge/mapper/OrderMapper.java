package com.example.awesomitychallenge.mapper;

import com.example.awesomitychallenge.dto.OrderDto;
import com.example.awesomitychallenge.entities.Orders;


public class OrderMapper {
    public static OrderDto map(Orders orders) {
        return new OrderDto(orders.getId(), orders.getProductName(), orders.getCategory(), orders.getQuantity(), orders.getFirstName(), orders.getLastName(), orders.getEmail(), orders.getPhoneNumber(), orders.getAddress(), orders.getOrderStatus(), orders.getPrice());
    }

}