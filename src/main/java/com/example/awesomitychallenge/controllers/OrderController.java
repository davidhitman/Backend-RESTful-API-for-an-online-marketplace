

package com.example.awesomitychallenge.controllers;

import com.example.awesomitychallenge.dto.OrderDto;
import com.example.awesomitychallenge.entities.Orders;
import com.example.awesomitychallenge.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Order Controller", description = "Handles Orders")
@AllArgsConstructor
@RestController
@RequestMapping("/orders")

public class OrderController {
    private OrderService orderService;

    @Operation(summary = "Placing an Order", description = "Allows loggedIn users to place orders")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<GenericResponse<OrderDto>> placeOrders(@RequestParam String product_name, @RequestParam int quantity) {
        OrderDto order = orderService.placeOrders(product_name, quantity);
        return ResponseEntity.ok(new GenericResponse<>("The Order has been Placed", order));
    }

    @Operation(summary = "View Order History", description = "Allows Users and Admins to User Order History Of a particular User")
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<GenericResponse<Page<Orders>>> viewOrdersHistory(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "5") int pageSize) {
        Page<Orders> placedOrders = orderService.viewOrderHistory(offset, pageSize);
        return ResponseEntity.ok(new GenericResponse<>("The Order has been Placed", placedOrders));
    }

    @Operation(summary = "Delete Order", description = "Allows Users and Admins to delete Orders")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<String>> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.ok(new GenericResponse<>("Order deleted successfully", "Deleted ID" + id));
    }

    @Operation(summary = "view order by id", description = "Allows Users to view one order by the order id")
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<OrderDto>> viewOrder(@PathVariable Long id) {
        var order = orderService.viewOrder(id);
        return ResponseEntity.ok(new GenericResponse<>("Order with id" + id + "is:", order));
    }

    @Operation(summary = "Update Order", description = "Allows users and admins to update the order that was placed previously")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<OrderDto>> updateOrder(@PathVariable Long id, @RequestParam String product_name, @RequestParam int quantity) {
        var order = orderService.updateOrder(id, product_name, quantity);
        return ResponseEntity.ok(new GenericResponse<>("Order Updated", order));
    }


    @Operation(summary = "Update Order Status", description = "Allows Admins to Update the Status of the Order")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<GenericResponse<OrderDto>> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        var updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(new GenericResponse<>("Order Updated", updatedOrder));
    }
}




