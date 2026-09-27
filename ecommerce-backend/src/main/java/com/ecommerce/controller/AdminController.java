package com.ecommerce.controller;

import com.ecommerce.dto.AdminDashboardResponse;
import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AdminDashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    private final UserRepository userRepository;
    private final AdminDashboardService adminDashboardService;

    public AdminController(
            UserRepository userRepository,
            AdminDashboardService adminDashboardService) {

        this.userRepository = userRepository;
        this.adminDashboardService = adminDashboardService;
    }

    // Dashboard
    @GetMapping("/dashboard")
    public AdminDashboardResponse dashboard() {

        return adminDashboardService.getDashboard();
    }

    // Get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}