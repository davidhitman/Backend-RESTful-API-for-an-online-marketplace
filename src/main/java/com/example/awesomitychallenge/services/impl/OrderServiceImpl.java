

package com.example.awesomitychallenge.services.impl;


import com.example.awesomitychallenge.dto.OrderDto;
import com.example.awesomitychallenge.entities.Orders;
import com.example.awesomitychallenge.entities.Products;
import com.example.awesomitychallenge.entities.Users;
import com.example.awesomitychallenge.mapper.OrderMapper;
import com.example.awesomitychallenge.repositories.OrderRepository;
import com.example.awesomitychallenge.repositories.ProductRepository;
import com.example.awesomitychallenge.repositories.UserRepository;
import com.example.awesomitychallenge.services.JwtService;
import com.example.awesomitychallenge.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private JavaMailSender mailSender;

    public String getAuthenticatedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }

    @Override
    public OrderDto placeOrders(String productName, int quantity) {
        Orders order = new Orders();
        var email = getAuthenticatedUserEmail();
        Optional<Users> usersOptional = userRepository.findByEmail(email);
        if (usersOptional.isPresent()) {
            Users user = usersOptional.get();
            Products product = productRepository.findByProductName(productName);
            if (product != null) {
                if (product.getQuantity() > quantity) {
                    order.setProductName(productName);
                    order.setQuantity(quantity);
                    order.setFirstName(user.getFirstName());
                    order.setLastName(user.getLastName());
                    order.setEmail(email);
                    order.setCategory(product.getCategory());
                    order.setPhoneNumber(user.getPhoneNumber());
                    order.setAddress(user.getAddress());
                    order.setOrderStatus("Order Placed");
                    orderRepository.save(order);
                    product.setQuantity(product.getQuantity() - quantity);
                    productRepository.save(product);
                    return OrderMapper.mapToOrderdto(order);
                } else {
                    throw new RuntimeException("Insufficient product quantity available");
                }
            } else {
                throw new RuntimeException("Product not found");
            }
        } else {
            throw new RuntimeException("LogIn to place an order");
        }
    }


    @Override
    public Page<Orders> viewOrderHistory(int page, int size) {
        String email = getAuthenticatedUserEmail();
        return orderRepository.findByEmail(email, PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public void deleteOrderById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order with Id " + id + " not found.");
        }
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderDto updateOrder(Long id, String product_name, int quantity) {
        Optional<Orders> existingOrderOpt = orderRepository.findById(id);
        Products product = productRepository.findByProductName(product_name);
        if (existingOrderOpt.isPresent()) {
            Orders existingOrder = existingOrderOpt.get();
            if (existingOrder.getProductName().equals(product_name)) {
                if (quantity != 0 && quantity < existingOrder.getQuantity()) {
                    var difference = existingOrder.getQuantity() - quantity;
                    existingOrder.setQuantity(quantity);
                    product.setQuantity(product.getQuantity() + difference);
                    productRepository.save(product);
                    orderRepository.save(existingOrder);
                } else if (quantity > existingOrder.getQuantity()) {
                    if (product.getQuantity() >= quantity) {
                        var difference = quantity - existingOrder.getQuantity();
                        existingOrder.setQuantity(quantity);
                        product.setQuantity(product.getQuantity() - difference);
                        productRepository.save(product);
                        orderRepository.save(existingOrder);
                    } else {
                        throw new RuntimeException("Insufficient product quantity available");
                    }
                } else if (quantity == 0) {
                    deleteOrderById(id);
                } else {
                    throw new RuntimeException("You have not made any changes");
                }
                return OrderMapper.mapToOrderdto(existingOrder);
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
            return OrderMapper.mapToOrderdto(order);
        }
    }

    @Override
    public OrderDto viewOrder(Long id) {
        Optional<Orders> existingOrderOpt = orderRepository.findById(id);
        if (existingOrderOpt.isPresent()) {
            Orders existingOrder = existingOrderOpt.get();
            OrderDto order = OrderMapper.mapToOrderdto(existingOrder);
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




