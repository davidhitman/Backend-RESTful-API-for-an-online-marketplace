package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.OrderDto;
import org.springframework.data.domain.Page;


public interface OrderService {
    OrderDto placeOrders(String productName, int quantity);

    Page<OrderDto> viewOrderHistory(int offset, int pageSize);

    void deleteOrderById(Long id);

    OrderDto updateOrder(Long id, String product_name, int quantity);

    OrderDto updateOrderStatus(Long orderId, String newStatus);

    OrderDto viewOrder(Long id);
}


