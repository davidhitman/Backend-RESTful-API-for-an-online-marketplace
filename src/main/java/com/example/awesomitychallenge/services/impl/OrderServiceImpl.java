

package com.example.awesomitychallenge.services.impl;


import com.example.awesomitychallenge.dto.OrderDto;
import com.example.awesomitychallenge.entities.Orders;
import com.example.awesomitychallenge.mapper.OrderMapper;
import com.example.awesomitychallenge.mapper.UserMapper;
import com.example.awesomitychallenge.repositories.OrderRepository;
import com.example.awesomitychallenge.repositories.ProductRepository;
import com.example.awesomitychallenge.repositories.UserRepository;
import com.example.awesomitychallenge.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    public String getAuthenticatedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }

    @Override
    public OrderDto placeOrders(String productName, int quantity) {
        String email = getAuthenticatedUserEmail();
        var usersOptional = userRepository.findByEmail(email);
        var products = productRepository.findByProductName(productName);

        if (usersOptional.isPresent()) {
            if (products.isPresent()) {
                OrderDto dto = new OrderDto(null, productName, products.get().getCategory().getName(), quantity,
                        usersOptional.get().getFirstName(), usersOptional.get().getLastName(), email,
                        usersOptional.get().getPhoneNumber(), usersOptional.get().getAddress(), "PENDING", products.get().getPrice());
                kafkaTemplate.send("order-topic", dto);
                return dto;
            } else{
                throw new RuntimeException("Product not found");
            }
        } else {
            throw new RuntimeException("LogIn to place an order");
        }
    }


    @Override
    public Page<OrderDto> viewOrderHistory(int page, int size) {
        String email = getAuthenticatedUserEmail();
        Page<Orders> orders = orderRepository.findByEmail(email, PageRequest.of(page, size));
        return orders.map(OrderMapper::map);
    }

    @Override
    public void deleteOrderById(Long id) {
        var orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            orderRepository.delete(orderOpt.get());
        }else{
            throw new RuntimeException("Order with Id " + id + " not found.");
        }
    }

    @Override
    public OrderDto updateOrder(Long id, String product_name, int quantity) {
        Optional<Orders> existingOrderOpt = orderRepository.findById(id);
        var products = productRepository.findByProductName(product_name);
        var product = products.get();
        if (existingOrderOpt.isPresent()) {
            Orders existingOrder = existingOrderOpt.get();
            if (existingOrder.getProductName().equals(product_name)) {
                if (quantity != 0 && quantity < existingOrder.getQuantity()) {
                    var difference = existingOrder.getQuantity() - quantity;
                    existingOrder.setQuantity(quantity);
                    product.setQuantity(product.getQuantity() + difference);
                    productRepository.save(product);
                    orderRepository.save(existingOrder);
                } else if (quantity > existingOrder.getQuantity() && quantity != 0) {
                    if ((product.getQuantity() + existingOrder.getQuantity()) >= quantity) {
                        var difference = quantity - existingOrder.getQuantity();
                        existingOrder.setQuantity(quantity);
                        product.setQuantity(product.getQuantity() - difference);
                        productRepository.save(product);
                        orderRepository.save(existingOrder);
                    } else {
                        throw new RuntimeException("Insufficient product quantity available");
                    }
                } else if (quantity == 0) {
                    orderRepository.deleteById(id);
                } else {
                    throw new RuntimeException("You have not made any changes");
                }
                return OrderMapper.map(existingOrder);
            } else {
                throw new RuntimeException("The Product with name" + product_name + " does not match your order");
            }
        } else {
            throw new RuntimeException("Order with Id " + id + " not found.");
        }
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, String newStatus) {
        SimpleMailMessage message = new SimpleMailMessage();
        Optional<Orders> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order not found");
        } else {
            Orders order = orderOptional.get();
            order.setOrderStatus(newStatus);
            orderRepository.save(order);

            message.setTo(order.getEmail());
            message.setSubject("Order Status Changed");
            message.setText("Hello " + order.getFirstName() + ",\n\nYour Order Status has been changed to: " + newStatus + "\n\nBest Regards,\nTeam");

            mailSender.send(message);
            return OrderMapper.map(order);
        }
    }

    @Override
    public OrderDto viewOrder(Long id) {
        Optional<Orders> existingOrderOpt = orderRepository.findById(id);
        if (existingOrderOpt.isPresent()) {
            Orders existingOrder = existingOrderOpt.get();
            OrderDto order = OrderMapper.map(existingOrder);
            String storedEmail = order.getEmail();
            String signedInEmail = getAuthenticatedUserEmail();
            if (!storedEmail.equals(signedInEmail)) {
                throw new RuntimeException("You have no order with id:" + id);
            } else {
                return order;
            }
        } else {
            throw new RuntimeException("Order with Id " + id + " not found.");
        }
    }
}




