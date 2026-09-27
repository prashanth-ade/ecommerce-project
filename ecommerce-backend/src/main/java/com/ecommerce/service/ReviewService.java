package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.Review;
import com.ecommerce.entity.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ReviewRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {

        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Get user by email
    private User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));
    }

    // Get product by ID
    private Product getProduct(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"
                        ));
    }

    // Get all reviews for a product
    public List<Review> getReviewsByProduct(
            Long productId) {

        Product product = getProduct(productId);

        return reviewRepository.findByProduct(product);
    }

    // Add review
    public Review addReview(
            String email,
            Long productId,
            Integer rating,
            String comment) {

        User user = getUser(email);

        Product product = getProduct(productId);

        validateRating(rating);

        OptionalReviewCheck:
        {
            if (reviewRepository
                    .findByUserAndProduct(user, product)
                    .isPresent()) {

                throw new RuntimeException(
                        "You have already reviewed this product"
                );
            }
        }

        Review review = new Review();

        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    // Update review
    public Review updateReview(
            String email,
            Long reviewId,
            Integer rating,
            String comment) {

        User user = getUser(email);

        Review review =
                reviewRepository
                        .findByIdAndUser(reviewId, user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Review not found or you are not allowed to modify it"
                                ));

        validateRating(rating);

        review.setRating(rating);
        review.setComment(comment);
        review.setUpdatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    // Delete review
    public void deleteReview(
            String email,
            Long reviewId) {

        User user = getUser(email);

        Review review =
                reviewRepository
                        .findByIdAndUser(reviewId, user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Review not found or you are not allowed to delete it"
                                ));

        reviewRepository.delete(review);
    }

    // Validate rating
    private void validateRating(Integer rating) {

        if (rating == null) {

            throw new RuntimeException(
                    "Rating is required"
            );
        }

        if (rating < 1 || rating > 5) {

            throw new RuntimeException(
                    "Rating must be between 1 and 5"
            );
        }
    }
}