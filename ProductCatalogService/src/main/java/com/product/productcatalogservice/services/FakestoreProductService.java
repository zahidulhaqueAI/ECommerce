package com.product.productcatalogservice.services;

import com.product.productcatalogservice.clients.FakestoreApiClient;
import com.product.productcatalogservice.dtos.FakeStoreProductDto;
import com.product.productcatalogservice.model.Product;
import com.product.productcatalogservice.utils.FakestoreProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakestoreProductService implements IProductService {

    private Long id;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakestoreApiClient apiClient;

    @Autowired
    private FakestoreProductUtils utils;

    @Override
    public Product getProductById(Long id) {

        // carving the logic to separate FakestoreApiClient.
        FakeStoreProductDto fakestoreProductDto = apiClient.getProductById(id);
        if(fakestoreProductDto != null) {
            return utils.getProduct(fakestoreProductDto);
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {

        ResponseEntity<FakeStoreProductDto[]> forEntity = apiClient.getAllProducts();

        List<Product> products = new ArrayList<>();
        // loop and populate products
        for(FakeStoreProductDto fakestoreProductDto : forEntity.getBody()){
            products.add(utils.getProduct(fakestoreProductDto));
        }
        return products;
    }

    // This is POST request
    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto dto = utils.getFakestoreProductDto(product);

        FakeStoreProductDto productDto = apiClient.createProduct(dto).getBody();

        return utils.getProduct(productDto);
    }

    /*
    This is the PUT request
     */
    @Override
    public Product replaceProduct(Product product, Long id) {
        FakeStoreProductDto dto = apiClient.replaceProductById(utils.getFakestoreProductDto(product), id);
        return utils.getProduct(dto);
    }

    /*
    This is DELETE request
     */
    @Override
    public void deleteProductById(Long id) {
        try {
            apiClient.deleteProductById(id);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }
}
