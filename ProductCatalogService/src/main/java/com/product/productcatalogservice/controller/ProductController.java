package com.product.productcatalogservice.controller;

import com.product.productcatalogservice.dtos.CategoryDto;
import com.product.productcatalogservice.dtos.ProductDto;
import com.product.productcatalogservice.model.Product;
import com.product.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    // get all the Products
    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        return null;
    }

    // get the Product by id
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        try {

            // error handling
            if (productId <= 0) {
                throw new IllegalArgumentException("Product Id must be greater than 0");
            }
            Product product = productService.getProductById(productId);

            ProductDto productDto = getProductDto(product);
            MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
            header.add("Called By", "Zahid");
            return new ResponseEntity<>(productDto, header, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
          //  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw ex;
        }
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return null;
    }

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());

        if(product.getCategory() != null){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            productDto.setCategoryDto(categoryDto);
        }
        return productDto;
    }
}
