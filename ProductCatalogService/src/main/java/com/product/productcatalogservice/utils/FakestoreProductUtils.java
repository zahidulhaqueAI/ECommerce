package com.product.productcatalogservice.utils;

import com.product.productcatalogservice.dtos.FakeStoreProductDto;
import com.product.productcatalogservice.model.Category;
import com.product.productcatalogservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class FakestoreProductUtils {

    /*
    Method to convert FakestoreProductDto object to Product object
     */
    public Product getProduct(FakeStoreProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getTitle());
        product.setPrice(dto.getPrice());

        Category category = new Category();
        category.setName(dto.getCategory());
        product.setCategory(category);

        return product;
    }

    /*
    Method to convert Product object to FakeStoreProjectDto object
     */
    public FakeStoreProductDto getFakestoreProductDto(Product product) {
        FakeStoreProductDto dto = new FakeStoreProductDto();

        dto.setId(product.getId());
        dto.setTitle(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImage(product.getImageUrl());
        if(product.getCategory() != null) {
            dto.setCategory(product.getCategory().getName());
        }
        return dto;
    }
}
