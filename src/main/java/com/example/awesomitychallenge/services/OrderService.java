
package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.OrderDto;
import com.example.awesomitychallenge.entities.Orders;

import java.util.List;

public interface OrderService {
    void placeOrders(String productName, int quantity);
    List<OrderDto> viewOrderHistory(String email);
    void deleteOrderById(Long id);
    void updateOrder(Long id, String product_name, int quantity);
    Orders updateOrderStatus(Long orderId, String newStatus);
    String getOrderStatus(Long orderId);
}


