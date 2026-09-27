package com.ecommerce.controller;

import com.ecommerce.entity.Coupon;
import com.ecommerce.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    // Get all coupons
    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {

        return ResponseEntity.ok(
                couponService.getAllCoupons()
        );
    }

    // Get coupon by ID
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                couponService.getCouponById(id)
        );
    }

    // Find coupon by code
    @GetMapping("/code/{code}")
    public ResponseEntity<Coupon> getCouponByCode(
            @PathVariable String code) {

        return ResponseEntity.ok(
                couponService.getCouponByCode(code)
        );
    }

    // Create coupon
    @PostMapping
    public ResponseEntity<Coupon> createCoupon(
            @RequestBody Coupon coupon) {

        return ResponseEntity.ok(
                couponService.createCoupon(coupon)
        );
    }

    // Update coupon
    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(
            @PathVariable Long id,
            @RequestBody Coupon coupon) {

        return ResponseEntity.ok(
                couponService.updateCoupon(
                        id,
                        coupon
                )
        );
    }

    // Delete coupon
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoupon(
            @PathVariable Long id) {

        couponService.deleteCoupon(id);

        return ResponseEntity.ok(
                "Coupon deleted successfully"
        );
    }

    // Calculate discount
    @PostMapping("/apply")
    public ResponseEntity<Double> applyCoupon(
            @RequestParam String code,
            @RequestParam Double orderAmount) {

        return ResponseEntity.ok(
                couponService.calculateDiscount(
                        code,
                        orderAmount
                )
        );
    }
}