package com.product.productcatalogservice.services;

import com.product.productcatalogservice.dtos.FakestoreProductDto;
import com.product.productcatalogservice.model.Category;
import com.product.productcatalogservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    private Long id;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public Product getProductById(Long id) {

          RestTemplate restTemplate = restTemplateBuilder.build();
          ResponseEntity<FakestoreProductDto> fakestoreProductDtoResponseEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products/{id}",
                        FakestoreProductDto.class, id);

        if(fakestoreProductDtoResponseEntity.getStatusCode().is2xxSuccessful() &&
                fakestoreProductDtoResponseEntity.getBody() != null){
            return getProduct(fakestoreProductDtoResponseEntity.getBody());
        }
    return null;
    }

    @Override
    public List<Product> getAllProducts() {
       // https://fakestoreapi.com/products
       RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakestoreProductDto[]> forEntity = restTemplate.getForEntity("https://fakestoreapi.com/products",
                FakestoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        // loop and populate products
        for(FakestoreProductDto fakestoreProductDto : forEntity.getBody()){
            products.add(getProduct(fakestoreProductDto));
        }
        return products;
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
