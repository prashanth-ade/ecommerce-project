package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductService productService;

    public ProductController(
            ProductService productService) {

        this.productService = productService;
    }

    @PutMapping("/{id}/stock")
    public Product updateStock(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> request) {

        int stock = request.get("stock");

        return productService.updateStock(
                id,
                stock
        );
    }

    @PostMapping("/bulk")
    public List<Product> addProducts(
            @RequestBody List<Product> products) {

        return products.stream()
                .map(productService::addProduct)
                .toList();
    }

    @GetMapping
    public List<Product> getAllProducts() {

        return productService
                .getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product>
    getProductById(
            @PathVariable Long id) {

        return productService
                .getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    @PostMapping
    public Product addProduct(
            @RequestBody Product product) {

        return productService
                .addProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        return productService
                .updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent()
                .build();
    }
}