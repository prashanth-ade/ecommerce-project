package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.Review;
import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository
        extends JpaRepository<Review, Long> {

    List<Review> findByProduct(Product product);

    Optional<Review> findByUserAndProduct(
            User user,
            Product product
    );

    Optional<Review> findByIdAndUser(
            Long id,
            User user
    );
}