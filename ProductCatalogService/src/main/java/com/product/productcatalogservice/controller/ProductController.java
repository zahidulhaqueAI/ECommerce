package com.product.productcatalogservice.controller;

import com.product.productcatalogservice.dtos.ProductDto;
import com.product.productcatalogservice.model.Product;
import com.product.productcatalogservice.services.IProductService;

import com.product.productcatalogservice.utils.ProductUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private final IProductService productService;
    private final ProductUtils productUtils;

    public  ProductController(IProductService productService, ProductUtils productUtils) {
        this.productService = productService;
        this.productUtils = productUtils;
    }

    // get the Product by id
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        try {

            // error handling
            if (productId <= 0) {
                throw new IllegalArgumentException("Product id must be greater than 0");
            }
            Product product = productService.getProductById(productId);

            ProductDto productDto = productUtils.getProductDto(product);
            MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
            header.add("Called By", "Zahid");
            return new ResponseEntity<>(productDto, header, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    // get all the Products
    @GetMapping("/allProducts")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<Product> allProducts = productService.getAllProducts();
        List<ProductDto> response = new ArrayList<>();

        for (Product product : allProducts) {
            response.add(productUtils.getProductDto(product));
        }
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Called For", "All Products");
        return new ResponseEntity<>(response, header, HttpStatus.OK);
    }

    // POST request
    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {

        // convert ProductDto to Product object
        Product input = productUtils.getProduct(productDto);
        Product product = productService.createProduct(input);
        return productUtils.getProductDto(product);
    }

    // PUT request
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id,
                                                    @RequestBody ProductDto productDto) {

        // converting ProductDto to Product object
        Product input = productUtils.getProduct(productDto);
        Product product = productService.replaceProduct(input, id);
        return new ResponseEntity<>(productUtils.getProductDto(product), HttpStatus.OK);
    }


}
