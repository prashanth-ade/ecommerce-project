package com.ecommerce.service;

import com.ecommerce.dto.AdminDashboardResponse;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.CouponRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.PaymentRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ReviewRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ReviewRepository reviewRepository;
    private final CouponRepository couponRepository;

    public AdminDashboardService(
            UserRepository userRepository,
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            OrderRepository orderRepository,
            PaymentRepository paymentRepository,
            ReviewRepository reviewRepository,
            CouponRepository couponRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.reviewRepository = reviewRepository;
        this.couponRepository = couponRepository;
    }

    public AdminDashboardResponse getDashboard() {

        return new AdminDashboardResponse(
                userRepository.count(),
                productRepository.count(),
                categoryRepository.count(),
                orderRepository.count(),
                paymentRepository.count(),
                reviewRepository.count(),
                couponRepository.count()
        );
    }
}