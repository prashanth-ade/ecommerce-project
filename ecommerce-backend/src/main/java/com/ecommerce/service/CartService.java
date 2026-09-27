package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Get or create cart for user
    public Cart getOrCreateCart(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return cartRepository.findByUser(user)
                .orElseGet(() ->
                        cartRepository.save(new Cart(user))
                );
    }

    // Get all items in cart
    public List<CartItem> getCartItems(String email) {

        Cart cart = getOrCreateCart(email);

        return cartItemRepository.findByCart(cart);
    }

    // Add product to cart
    public CartItem addToCart(
            String email,
            Long productId,
            Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than 0");
        }

        Cart cart = getOrCreateCart(email);

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        // Check whether product already exists in cart
        CartItem existingItem =
                cartItemRepository
                        .findByCartAndProduct(cart, product)
                        .orElse(null);

        if (existingItem != null) {

            existingItem.setQuantity(
                    existingItem.getQuantity() + quantity
            );

            return cartItemRepository.save(existingItem);
        }

        CartItem cartItem =
                new CartItem(cart, product, quantity);

        return cartItemRepository.save(cartItem);
    }

    // Update product quantity
    public CartItem updateQuantity(
            String email,
            Long cartItemId,
            Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than 0");
        }

        Cart cart = getOrCreateCart(email);

        CartItem cartItem =
                cartItemRepository.findById(cartItemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cart item not found"));

        // Security check:
        // Make sure this item belongs to current user's cart
        if (!cartItem.getCart().getId()
                .equals(cart.getId())) {

            throw new RuntimeException(
                    "You cannot modify this cart item");
        }

        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    // Remove item from cart
    public void removeFromCart(
            String email,
            Long cartItemId) {

        Cart cart = getOrCreateCart(email);

        CartItem cartItem =
                cartItemRepository.findById(cartItemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cart item not found"));

        if (!cartItem.getCart().getId()
                .equals(cart.getId())) {

            throw new RuntimeException(
                    "You cannot remove this cart item");
        }

        cartItemRepository.delete(cartItem);
    }

    // Clear entire cart
    public void clearCart(String email) {

        Cart cart = getOrCreateCart(email);

        List<CartItem> items =
                cartItemRepository.findByCart(cart);

        cartItemRepository.deleteAll(items);
    }
}