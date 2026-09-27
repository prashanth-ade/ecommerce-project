package com.ecommerce.controller;

import com.ecommerce.entity.Order;
import com.ecommerce.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@CrossOrigin
public class AdminOrderController {

    private final OrderRepository orderRepository;

    public AdminOrderController(
            OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {

        return ResponseEntity.ok(
                orderRepository.findAll()
        );
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable Long id) {

        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update order status
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        ));

        order.setStatus(status.toUpperCase());

        return ResponseEntity.ok(
                orderRepository.save(order)
        );
    }
}