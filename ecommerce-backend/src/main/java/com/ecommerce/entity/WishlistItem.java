package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "wishlist_items")
@Data
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public WishlistItem() {
    }

    public WishlistItem(Wishlist wishlist, Product product) {
        this.wishlist = wishlist;
        this.product = product;
    }

}