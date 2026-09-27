package com.ecommerce.dto;

public class AdminDashboardResponse {

    private long totalUsers;
    private long totalProducts;
    private long totalCategories;
    private long totalOrders;
    private long totalPayments;
    private long totalReviews;
    private long totalCoupons;

    public AdminDashboardResponse() {
    }

    public AdminDashboardResponse(
            long totalUsers,
            long totalProducts,
            long totalCategories,
            long totalOrders,
            long totalPayments,
            long totalReviews,
            long totalCoupons) {

        this.totalUsers = totalUsers;
        this.totalProducts = totalProducts;
        this.totalCategories = totalCategories;
        this.totalOrders = totalOrders;
        this.totalPayments = totalPayments;
        this.totalReviews = totalReviews;
        this.totalCoupons = totalCoupons;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(long totalCategories) {
        this.totalCategories = totalCategories;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public long getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(long totalPayments) {
        this.totalPayments = totalPayments;
    }

    public long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public long getTotalCoupons() {
        return totalCoupons;
    }

    public void setTotalCoupons(long totalCoupons) {
        this.totalCoupons = totalCoupons;
    }
}