package com.ecommerce.controller;

import com.ecommerce.entity.Payment;
import com.ecommerce.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Get all payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {

        return ResponseEntity.ok(
                paymentService.getAllPayments()
        );
    }

    // Get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(id)
        );
    }

    // Get payment by order ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                paymentService.getPaymentByOrderId(orderId)
        );
    }

    // Create COD payment
    @PostMapping("/cod")
    public ResponseEntity<Payment> createCodPayment(
            @RequestParam Long orderId,
            @RequestParam Double amount) {

        return ResponseEntity.ok(
                paymentService.createCodPayment(
                        orderId,
                        amount
                )
        );
    }

    // Create online payment record
    @PostMapping("/online")
    public ResponseEntity<Payment> createOnlinePayment(
            @RequestParam Long orderId,
            @RequestParam Double amount,
            @RequestParam String transactionId) {

        return ResponseEntity.ok(
                paymentService.createOnlinePayment(
                        orderId,
                        amount,
                        transactionId
                )
        );
    }

    // Update payment status
    @PutMapping("/{id}/status")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(
                paymentService.updatePaymentStatus(
                        id,
                        status
                )
        );
    }
}