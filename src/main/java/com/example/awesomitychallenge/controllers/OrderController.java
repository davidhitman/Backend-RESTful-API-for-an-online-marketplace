

package com.example.awesomitychallenge.controllers;

import com.example.awesomitychallenge.dto.OrderDto;
import com.example.awesomitychallenge.entities.Orders;
import com.example.awesomitychallenge.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order Controller", description = "Handles Orders")
@AllArgsConstructor
@RestController
@RequestMapping("/orders")

public class OrderController {
    private OrderService order_service;

    @Operation(summary = "Placing an Order", description = "Allows loggedIn users to place orders")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<String> placeOrders(@RequestParam String product_name, @RequestParam int quantity) {
        order_service.placeOrders(product_name, quantity);
        return ResponseEntity.ok("Thanks for placing an order!");
    }

    @Operation(summary = "View Order History", description = "Allows Users and Admins to User Order History Of a particular User")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/{email}")
    public ResponseEntity<List<OrderDto>> viewOrdersHistory(@PathVariable String email) {
        List<OrderDto> placedOrders = order_service.viewOrderHistory(email);
        return new ResponseEntity<>(placedOrders, HttpStatus.OK);
    }

    @Operation(summary = "Delete Order", description = "Allows Users and Admins to delete Orders")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        order_service.deleteOrderById(id);
        return ResponseEntity.ok("Order deleted successfully!");
    }

    @Operation(summary = "Update Order", description = "Allows users and admins to update the order that was placed previously")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id,  @RequestParam String product_name, @RequestParam int quantity) {
        order_service.updateOrder(id, product_name, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "View Order Status", description = "Allows Users and Admins to View the status of the order")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/{Id}/status")
    public ResponseEntity<String> getOrderStatus(@PathVariable Long orderId) {
        String status = order_service.getOrderStatus(orderId);
        return ResponseEntity.ok(status);
    }

    @Operation(summary = "Update Order Status", description = "Allows Admins to Update the Status of the Order")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{Id}/status")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        Orders updatedOrder = order_service.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }
}




