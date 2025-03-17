

package com.example.awesomitychallenge.services.impl;


import com.example.awesomitychallenge.dto.OrderDto;
import com.example.awesomitychallenge.entities.Orders;
import com.example.awesomitychallenge.entities.Products;
import com.example.awesomitychallenge.entities.Users;
import com.example.awesomitychallenge.mapper.OrderMapper;
import com.example.awesomitychallenge.repositories.OrderRepository;
import com.example.awesomitychallenge.repositories.ProductRepository;
import com.example.awesomitychallenge.repositories.UserRepository;
import com.example.awesomitychallenge.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orders_repository;
    private ProductRepository product_repository;
    private UserRepository users_repository;
    private JavaMailSender mail_sender;

    public String getAuthenticatedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }

    @Override
    public void placeOrders(String productName, int quantity) {
        Orders order = new Orders();
        var email = getAuthenticatedUserEmail();
        Optional<Users> usersOptional = users_repository.findByEmail(email);
        Users user = usersOptional.get();
        Products product = product_repository.findByProductName(productName);
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
                orders_repository.save(order);
                product.setQuantity(product.getQuantity() - quantity);
                product_repository.save(product);
            } else {
                throw new RuntimeException("Insufficient product quantity available");
            }
        } else {
            throw new RuntimeException("Product not found");
            }

    }


    @Override
    public List<OrderDto> viewOrderHistory(String email) {
        List<Orders> orders = orders_repository.findByEmail(email);
        if(!orders.isEmpty()) {
            List<OrderDto> orderDtos = new ArrayList<>();
            for (Orders order : orders) {
                OrderDto orderdto = OrderMapper.mapToOrderdto(order);
                orderDtos.add(orderdto);
            }
            return orderDtos;
        }else{
            throw new RuntimeException("User has no Orders");
        }
    }

    @Override
    @Transactional
    public void deleteOrderById(Long id) {
        if (!orders_repository.existsById(id)) {
            throw new RuntimeException("Order with Id " + id + " not found.");
        }
        orders_repository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateOrder(Long id, String product_name, int quantity) {
        Optional<Orders> existingOrderOpt = orders_repository.findById(id);
        Products product = product_repository.findByProductName(product_name);
        if (existingOrderOpt.isPresent()) {
            Orders existingOrder = existingOrderOpt.get();
            if (existingOrder.getProductName().equals(product_name)) {
                if (quantity != 0 && quantity < existingOrder.getQuantity()) {
                    var difference = existingOrder.getQuantity() - quantity;
                    existingOrder.setQuantity(quantity);
                    product.setQuantity(product.getQuantity() + difference);
                    product_repository.save(product);
                    orders_repository.save(existingOrder);
                } else if (quantity > existingOrder.getQuantity()) {
                    if (product.getQuantity() >= quantity) {
                        var difference = quantity - existingOrder.getQuantity();
                        existingOrder.setQuantity(quantity);
                        product.setQuantity(product.getQuantity() - difference);
                        product_repository.save(product);
                        orders_repository.save(existingOrder);
                    } else {
                        throw new RuntimeException("Insufficient product quantity available");
                    }
                }else if (quantity == 0) {
                    deleteOrderById(id);
                }else{
                    throw new RuntimeException("You have not made any changes");
                }
            }else{
                throw new RuntimeException("The Product with name does not match your order");
            }
        }else {
            throw new RuntimeException("Order with Id " + id + " not found.");
        }
    }

    @Override
    public Orders updateOrderStatus(Long orderId, String newStatus) {
        SimpleMailMessage message = new SimpleMailMessage();
        return orders_repository.findById(orderId).map(order -> {
            order.setOrderStatus(newStatus);
            message.setTo(order.getEmail());
            message.setSubject("Order Status Changed");
            message.setText("Hello " + order.getFirstName() + ",\n\nYour Order Status has been Changed to!\n\n" + newStatus + "\n\n Best Regards,\nTeam");
            mail_sender.send(message);
            return orders_repository.save(order);
        }).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public String getOrderStatus(Long orderId) {
        return orders_repository.findById(orderId)
                .map(Orders::getOrderStatus)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

}




