package com.ecommerce.controller;

import com.ecommerce.entity.CartItem;
import com.ecommerce.service.CartService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Get current user's cart
    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                cartService.getCartItems(email)
        );
    }

    // Add product to cart
    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                cartService.addToCart(
                        email,
                        productId,
                        quantity
                )
        );
    }

    // Update quantity
    @PutMapping("/items/{id}")
    public ResponseEntity<CartItem> updateQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                cartService.updateQuantity(
                        email,
                        id,
                        quantity
                )
        );
    }

    // Remove item
    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> removeFromCart(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        cartService.removeFromCart(email, id);

        return ResponseEntity.ok(
                "Item removed from cart"
        );
    }

    // Clear cart
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(
            Authentication authentication) {

        String email = authentication.getName();

        cartService.clearCart(email);

        return ResponseEntity.ok(
                "Cart cleared successfully"
        );
    }
}