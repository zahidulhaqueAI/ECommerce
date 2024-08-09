package com.product.productcatalogservice.controller;

import com.product.productcatalogservice.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    // get all the Products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return null;
    }

    // get the Product by id
    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long productId) {

        Product product = new Product();
        product.setId(productId);
        return product;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return product;
    }
}
