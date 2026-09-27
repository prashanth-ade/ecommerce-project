package com.ecommerce.service;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.Payment;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    // Get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Get payment by ID
    public Payment getPaymentById(Long id) {

        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found"
                        ));
    }

    // Get payment for an order
    public Payment getPaymentByOrderId(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        ));

        return paymentRepository.findByOrder(order)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found for this order"
                        ));
    }

    // Create COD payment
    public Payment createCodPayment(
            Long orderId,
            Double amount) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        ));

        if (paymentRepository.findByOrder(order).isPresent()) {
            throw new RuntimeException(
                    "Payment already exists for this order"
            );
        }

        if (amount == null || amount <= 0) {
            throw new RuntimeException(
                    "Payment amount must be greater than 0"
            );
        }

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setPaymentMethod("COD");
        payment.setPaymentStatus("PENDING");
        payment.setAmount(amount);
        payment.setTransactionId(null);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    // Create online payment record
    public Payment createOnlinePayment(
            Long orderId,
            Double amount,
            String transactionId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        ));

        if (paymentRepository.findByOrder(order).isPresent()) {
            throw new RuntimeException(
                    "Payment already exists for this order"
            );
        }

        if (amount == null || amount <= 0) {
            throw new RuntimeException(
                    "Payment amount must be greater than 0"
            );
        }

        if (transactionId == null
                || transactionId.isBlank()) {

            throw new RuntimeException(
                    "Transaction ID is required"
            );
        }

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setPaymentMethod("ONLINE");
        payment.setPaymentStatus("PAID");
        payment.setAmount(amount);
        payment.setTransactionId(transactionId);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    // Update payment status
    public Payment updatePaymentStatus(
            Long paymentId,
            String status) {

        Payment payment = getPaymentById(paymentId);

        String normalizedStatus =
                status.trim().toUpperCase();

        if (!normalizedStatus.equals("PENDING")
                && !normalizedStatus.equals("PAID")
                && !normalizedStatus.equals("FAILED")) {

            throw new RuntimeException(
                    "Invalid payment status"
            );
        }

        payment.setPaymentStatus(normalizedStatus);

        if (normalizedStatus.equals("PAID")
                && payment.getPaymentDate() == null) {

            payment.setPaymentDate(
                    LocalDateTime.now()
            );
        }

        return paymentRepository.save(payment);
    }
}