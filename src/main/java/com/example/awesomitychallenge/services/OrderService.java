
package com.example.awesomitychallenge.services;

import com.example.awesomitychallenge.dto.OrderDto;
import com.example.awesomitychallenge.entities.Orders;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    void placeOrders(String productName, int quantity);
    List<Orders> viewOrderHistory(int page, int size);
    void deleteOrderById(Long id);
    void updateOrder(Long id, String product_name, int quantity);
    Orders updateOrderStatus(Long orderId, String newStatus);
    OrderDto viewOrder(Long id);
}


