package com.ecommerce.controller;

import com.ecommerce.entity.Coupon;
import com.ecommerce.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupons")
@CrossOrigin
public class AdminCouponController {

    private final CouponService couponService;

    public AdminCouponController(
            CouponService couponService) {

        this.couponService = couponService;
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {

        return ResponseEntity.ok(
                couponService.getAllCoupons()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                couponService.getCouponById(id)
        );
    }

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(
            @RequestBody Coupon coupon) {

        return ResponseEntity.ok(
                couponService.createCoupon(coupon)
        );
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoupon(
            @PathVariable Long id) {

        couponService.deleteCoupon(id);

        return ResponseEntity.ok(
                "Coupon deleted successfully"
        );
    }
}