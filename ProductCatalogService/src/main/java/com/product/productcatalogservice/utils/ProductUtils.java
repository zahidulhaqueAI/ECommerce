package com.product.productcatalogservice.utils;

import com.product.productcatalogservice.dtos.CategoryDto;
import com.product.productcatalogservice.dtos.ProductDto;
import com.product.productcatalogservice.model.Category;
import com.product.productcatalogservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductUtils {

    /*
    This method id used for converting ProductDto to Product object
    @input : ProductDto
    @output : Product
     */
    public Product getProduct(ProductDto productDto) {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());

        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setName(productDto.getCategory().getName());
            product.setCategory(category);
        }
        return product;
    }

    /*
    This method id used for converting Product object to ProductDto
    @input : Product
    @output : ProductDto
    */
    public ProductDto getProductDto(Product product) {
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
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }
}
