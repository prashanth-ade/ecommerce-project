package com.ecommerce.controller;

import com.ecommerce.entity.Review;
import com.ecommerce.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reviews")
@CrossOrigin
public class AdminReviewController {

    private final ReviewRepository reviewRepository;

    public AdminReviewController(
            ReviewRepository reviewRepository) {

        this.reviewRepository = reviewRepository;
    }

    // Get all reviews
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {

        return ResponseEntity.ok(
                reviewRepository.findAll()
        );
    }

    // Delete review
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long id) {

        if (!reviewRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        reviewRepository.deleteById(id);

        return ResponseEntity.ok(
                "Review deleted successfully"
        );
    }
}