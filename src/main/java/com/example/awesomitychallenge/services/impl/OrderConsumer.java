
package com.example.awesomitychallenge.services.impl;

import com.example.awesomitychallenge.dto.OrderDto;

import com.example.awesomitychallenge.entities.Orders;
import com.example.awesomitychallenge.entities.Products;
import com.example.awesomitychallenge.entities.Users;
import com.example.awesomitychallenge.repositories.OrderRepository;
import com.example.awesomitychallenge.repositories.ProductRepository;
import com.example.awesomitychallenge.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @KafkaListener(topics = "order-topic", groupId = "order_group", containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrder(OrderDto orderDto) {

        Users user = userRepository.findByEmail(orderDto.getEmail()).orElseThrow();
        Products product = productRepository.findByProductName(orderDto.getProductName()).orElseThrow();

        if (product.getQuantity() < orderDto.getQuantity()) {
            throw new RuntimeException ("Insufficient stock for product: " + orderDto.getProductName());
        }

        Orders order = getOrders(orderDto, user, product);

        product.setQuantity(product.getQuantity() - orderDto.getQuantity());
        productRepository.save(product);
        orderRepository.save(order);
    }

    private Orders getOrders(OrderDto orderDto, Users user, Products product) {
        Orders order = new Orders();
        order.setProductName(orderDto.getProductName());
        order.setQuantity(orderDto.getQuantity());
        order.setFirstName(orderDto.getFirstName());
        order.setLastName(orderDto.getLastName());
        order.setEmail(orderDto.getEmail());
        order.setPhoneNumber(orderDto.getPhoneNumber());
        order.setAddress(orderDto.getAddress());
        order.setOrderStatus("Order Placed");
        order.setPrice(orderDto.getPrice());
        order.setCategory(orderDto.getCategory());
        order.setUser(user);
        order.setProduct(product);
        return order;
    }
}


