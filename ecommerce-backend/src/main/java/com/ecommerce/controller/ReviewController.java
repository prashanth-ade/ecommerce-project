package com.ecommerce.controller;

import com.ecommerce.entity.Review;
import com.ecommerce.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Get reviews for a product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                reviewService.getReviewsByProduct(productId)
        );
    }

    // Add review
    @PostMapping
    public ResponseEntity<Review> addReview(
            @RequestParam Long productId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                reviewService.addReview(
                        email,
                        productId,
                        rating,
                        comment
                )
        );
    }

    // Update review
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long id,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                reviewService.updateReview(
                        email,
                        id,
                        rating,
                        comment
                )
        );
    }

    // Delete review
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        reviewService.deleteReview(
                email,
                id
        );

        return ResponseEntity.ok(
                "Review deleted successfully"
        );
    }
}
