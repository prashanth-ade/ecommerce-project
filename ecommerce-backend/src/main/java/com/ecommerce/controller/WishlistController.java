package com.ecommerce.controller;

import com.ecommerce.entity.WishlistItem;
import com.ecommerce.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // Get current user's wishlist
    @GetMapping
    public ResponseEntity<List<WishlistItem>> getWishlist(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                wishlistService.getWishlistItems(email)
        );
    }

    // Add product to wishlist
    @PostMapping("/add")
    public ResponseEntity<WishlistItem> addToWishlist(
            @RequestParam Long productId,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                wishlistService.addToWishlist(
                        email,
                        productId
                )
        );
    }

    // Remove item from wishlist
    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> removeFromWishlist(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        wishlistService.removeFromWishlist(
                email,
                id
        );

        return ResponseEntity.ok(
                "Item removed from wishlist"
        );
    }

    // Clear wishlist
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearWishlist(
            Authentication authentication) {

        String email = authentication.getName();

        wishlistService.clearWishlist(email);

        return ResponseEntity.ok(
                "Wishlist cleared successfully"
        );
    }
}