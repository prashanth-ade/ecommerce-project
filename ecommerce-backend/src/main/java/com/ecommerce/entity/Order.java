package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String email;
    private String phone;
    private String address;
    private double totalAmount;
    private String paymentMethod;
    private String status;
    private LocalDateTime orderDate;

    public Order() {
    }

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();


    @PrePersist
    public void prePersist() {
        if (orderDate == null) {
            orderDate = LocalDateTime.now();
        }

        if (status == null) {
            status = "PLACED";
        }
    }

}