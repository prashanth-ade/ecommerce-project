package com.ecommerce.service;


import com.ecommerce.entity.Order;
import com.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersByEmail(String email) {

        return orderRepository.findByEmail(email);
    }

    public Order updateOrderStatus(Long id, String status) {

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        existingOrder.setStatus(status);

        return orderRepository.save(existingOrder);
    }
}