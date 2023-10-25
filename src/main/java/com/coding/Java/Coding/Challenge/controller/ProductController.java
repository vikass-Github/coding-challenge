package com.coding.Java.Coding.Challenge.controller;

import com.coding.Java.Coding.Challenge.entity.Product;
import com.coding.Java.Coding.Challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }

    @PutMapping("/{productId}")
    public void updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping
    public List<Product> fetchProducts() {
        return productService.fetchProducts();
    }

    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date minPostedDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date maxPostedDate) {
        return productService.searchProducts(productName, minPrice, maxPrice, minPostedDate, maxPostedDate);
    }

    @GetMapping("/approval-queue")
    public List<Product> fetchProductsInApprovalQueue() {
        return productService.fetchProductsInApprovalQueue();
    }


    @PutMapping("/approval-queue/{approvalId}/approve")
    public void approveProduct(@PathVariable Long approvalId) {
        productService.approveProduct(approvalId);
    }


    @PutMapping("/approval-queue/{approvalId}/reject")
    public void rejectProduct(@PathVariable Long approvalId) {
        productService.rejectProduct(approvalId);
    }

}
