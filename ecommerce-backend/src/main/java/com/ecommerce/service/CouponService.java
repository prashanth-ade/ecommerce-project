package com.ecommerce.service;

import com.ecommerce.entity.Coupon;
import com.ecommerce.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    // Get all coupons
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    // Get coupon by ID
    public Coupon getCouponById(Long id) {

        return couponRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Coupon not found"
                        ));
    }

    // Get coupon by code
    public Coupon getCouponByCode(String code) {

        return couponRepository.findByCode(
                        code.trim().toUpperCase()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid coupon code"
                        ));
    }

    // Create coupon
    public Coupon createCoupon(Coupon coupon) {

        String code =
                coupon.getCode()
                        .trim()
                        .toUpperCase();

        if (couponRepository.existsByCode(code)) {
            throw new RuntimeException(
                    "Coupon code already exists"
            );
        }

        coupon.setCode(code);

        if (coupon.getActive() == null) {
            coupon.setActive(true);
        }

        if (coupon.getMinimumOrderAmount() == null) {
            coupon.setMinimumOrderAmount(0.0);
        }

        validateCouponData(coupon);

        return couponRepository.save(coupon);
    }

    // Update coupon
    public Coupon updateCoupon(
            Long id,
            Coupon updatedCoupon) {

        Coupon existingCoupon =
                getCouponById(id);

        String code =
                updatedCoupon.getCode()
                        .trim()
                        .toUpperCase();

        if (!existingCoupon.getCode().equals(code)
                && couponRepository.existsByCode(code)) {

            throw new RuntimeException(
                    "Coupon code already exists"
            );
        }

        existingCoupon.setCode(code);

        existingCoupon.setDiscountType(
                updatedCoupon.getDiscountType()
                        .toUpperCase()
        );

        existingCoupon.setDiscountValue(
                updatedCoupon.getDiscountValue()
        );

        existingCoupon.setMinimumOrderAmount(
                updatedCoupon.getMinimumOrderAmount()
        );

        existingCoupon.setExpiryDate(
                updatedCoupon.getExpiryDate()
        );

        existingCoupon.setActive(
                updatedCoupon.getActive()
        );

        if (existingCoupon.getMinimumOrderAmount() == null) {
            existingCoupon.setMinimumOrderAmount(0.0);
        }

        validateCouponData(existingCoupon);

        return couponRepository.save(existingCoupon);
    }

    // Delete coupon
    public void deleteCoupon(Long id) {

        Coupon coupon = getCouponById(id);

        couponRepository.delete(coupon);
    }

    // Validate and calculate discount
    public Double calculateDiscount(
            String code,
            Double orderAmount) {

        Coupon coupon = getCouponByCode(code);

        validateCoupon(coupon, orderAmount);

        if ("PERCENTAGE".equalsIgnoreCase(
                coupon.getDiscountType())) {

            double discount =
                    orderAmount
                            * coupon.getDiscountValue()
                            / 100;

            return Math.min(
                    discount,
                    orderAmount
            );
        }

        if ("FLAT".equalsIgnoreCase(
                coupon.getDiscountType())) {

            return Math.min(
                    coupon.getDiscountValue(),
                    orderAmount
            );
        }

        throw new RuntimeException(
                "Invalid discount type"
        );
    }

    // Validate coupon before applying
    private void validateCoupon(
            Coupon coupon,
            Double orderAmount) {

        if (!Boolean.TRUE.equals(
                coupon.getActive())) {

            throw new RuntimeException(
                    "Coupon is inactive"
            );
        }

        if (coupon.getExpiryDate() != null
                && coupon.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Coupon has expired"
            );
        }

        if (orderAmount == null || orderAmount < 0) {

            throw new RuntimeException(
                    "Invalid order amount"
            );
        }

        if (coupon.getMinimumOrderAmount() != null
                && orderAmount
                < coupon.getMinimumOrderAmount()) {

            throw new RuntimeException(
                    "Minimum order amount is ₹"
                            + coupon.getMinimumOrderAmount()
            );
        }
    }

    // Validate coupon configuration
    private void validateCouponData(
            Coupon coupon) {

        if (coupon.getCode() == null
                || coupon.getCode().isBlank()) {

            throw new RuntimeException(
                    "Coupon code is required"
            );
        }

        if (coupon.getDiscountType() == null
                || coupon.getDiscountType().isBlank()) {

            throw new RuntimeException(
                    "Discount type is required"
            );
        }

        String type =
                coupon.getDiscountType()
                        .toUpperCase();

        if (!type.equals("PERCENTAGE")
                && !type.equals("FLAT")) {

            throw new RuntimeException(
                    "Discount type must be PERCENTAGE or FLAT"
            );
        }

        coupon.setDiscountType(type);

        if (coupon.getDiscountValue() == null
                || coupon.getDiscountValue() <= 0) {

            throw new RuntimeException(
                    "Discount value must be greater than 0"
            );
        }

        if ("PERCENTAGE".equals(type)
                && coupon.getDiscountValue() > 100) {

            throw new RuntimeException(
                    "Percentage discount cannot exceed 100"
            );
        }

        if (coupon.getMinimumOrderAmount() != null
                && coupon.getMinimumOrderAmount() < 0) {

            throw new RuntimeException(
                    "Minimum order amount cannot be negative"
            );
        }
    }
}