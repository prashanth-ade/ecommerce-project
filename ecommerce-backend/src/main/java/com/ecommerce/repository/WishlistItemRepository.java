package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.Wishlist;
import com.ecommerce.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistItemRepository
        extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByWishlist(Wishlist wishlist);

    Optional<WishlistItem> findByWishlistAndProduct(
            Wishlist wishlist,
            Product product
    );
}