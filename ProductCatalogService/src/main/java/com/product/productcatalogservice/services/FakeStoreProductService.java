package com.product.productcatalogservice.services;

import com.product.productcatalogservice.dtos.FakestoreProductDto;
import com.product.productcatalogservice.model.Category;
import com.product.productcatalogservice.model.Product;
import org.apache.tomcat.util.IntrospectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    private Long id;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public Product getProductById(Long id) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakestoreProductDto> fakestoreProductDtoResponseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakestoreProductDto.class, id);

        if(fakestoreProductDtoResponseEntity.getStatusCode().is2xxSuccessful() && fakestoreProductDtoResponseEntity.getBody() != null){
            return getProduct(fakestoreProductDtoResponseEntity.getBody());
        }


    return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    private Product getProduct(FakestoreProductDto dto) {
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
}
