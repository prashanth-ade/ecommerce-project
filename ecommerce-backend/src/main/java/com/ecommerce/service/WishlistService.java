package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.entity.Wishlist;
import com.ecommerce.entity.WishlistItem;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.WishlistItemRepository;
import com.ecommerce.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishlistService(
            WishlistRepository wishlistRepository,
            WishlistItemRepository wishlistItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.wishlistRepository = wishlistRepository;
        this.wishlistItemRepository = wishlistItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Get existing wishlist or create a new one
    public Wishlist getOrCreateWishlist(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return wishlistRepository.findByUser(user)
                .orElseGet(() ->
                        wishlistRepository.save(
                                new Wishlist(user)
                        ));
    }

    // Get all wishlist items
    public List<WishlistItem> getWishlistItems(String email) {

        Wishlist wishlist = getOrCreateWishlist(email);

        return wishlistItemRepository.findByWishlist(wishlist);
    }

    // Add product to wishlist
    public WishlistItem addToWishlist(
            String email,
            Long productId) {

        Wishlist wishlist = getOrCreateWishlist(email);

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        WishlistItem existingItem =
                wishlistItemRepository
                        .findByWishlistAndProduct(
                                wishlist,
                                product
                        )
                        .orElse(null);

        if (existingItem != null) {
            throw new RuntimeException(
                    "Product already exists in wishlist"
            );
        }

        WishlistItem wishlistItem =
                new WishlistItem(wishlist, product);

        return wishlistItemRepository.save(wishlistItem);
    }

    // Remove product from wishlist
    public void removeFromWishlist(
            String email,
            Long wishlistItemId) {

        Wishlist wishlist = getOrCreateWishlist(email);

        WishlistItem wishlistItem =
                wishlistItemRepository.findById(wishlistItemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Wishlist item not found"
                                ));

        if (!wishlistItem.getWishlist()
                .getId()
                .equals(wishlist.getId())) {

            throw new RuntimeException(
                    "You cannot remove this wishlist item"
            );
        }

        wishlistItemRepository.delete(wishlistItem);
    }

    // Clear wishlist
    public void clearWishlist(String email) {

        Wishlist wishlist = getOrCreateWishlist(email);

        List<WishlistItem> items =
                wishlistItemRepository.findByWishlist(wishlist);

        wishlistItemRepository.deleteAll(items);
    }
}