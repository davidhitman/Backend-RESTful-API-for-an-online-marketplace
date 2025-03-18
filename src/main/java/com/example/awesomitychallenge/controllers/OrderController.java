

package com.example.awesomitychallenge.controllers;

import com.example.awesomitychallenge.dto.OrderDto;
import com.example.awesomitychallenge.entities.Orders;
import com.example.awesomitychallenge.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
    private OrderService orderService;

    @Operation(summary = "Placing an Order", description = "Allows loggedIn users to place orders")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<String> placeOrders(@RequestParam String product_name, @RequestParam int quantity) {
        orderService.placeOrders(product_name, quantity);
        return ResponseEntity.ok("Thanks for placing an order!");
    }

    @Operation(summary = "View Order History", description = "Allows Users and Admins to User Order History Of a particular User")
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<Orders>> viewOrdersHistory(@RequestParam(defaultValue = "0")  int page, @RequestParam(defaultValue = "5") int size) {
        List<Orders> placedOrders = orderService.viewOrderHistory(page, size);
        return new ResponseEntity<>(placedOrders, HttpStatus.OK);
    }

    @Operation(summary = "Delete Order", description = "Allows Users and Admins to delete Orders")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.ok("Order deleted successfully!");
    }

    @Operation(summary = "view order by id", description = "Allows Users to view one order by the order id")
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> viewOrder(@PathVariable Long id) {
        var order = orderService.viewOrder(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Operation(summary = "Update Order", description = "Allows users and admins to update the order that was placed previously")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id,  @RequestParam String product_name, @RequestParam int quantity) {
        orderService.updateOrder(id, product_name, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Update Order Status", description = "Allows Admins to Update the Status of the Order")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Orders updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }
}




