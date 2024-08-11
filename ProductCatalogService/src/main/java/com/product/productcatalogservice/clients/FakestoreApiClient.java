package com.product.productcatalogservice.clients;

import com.product.productcatalogservice.dtos.FakeStoreProductDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class FakestoreApiClient {

    private final RestTemplateBuilder restTemplateBuilder;

    public FakestoreApiClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    // get product details by ID
    public FakeStoreProductDto getProductById(Long id) {

        ResponseEntity<FakeStoreProductDto> fakestoreProductDtoResponseEntity =
                requestForEntity("https://fakestoreapi.com/products/{id}",
                        HttpMethod.GET, null, FakeStoreProductDto.class, id);

        if(fakestoreProductDtoResponseEntity.getStatusCode().is2xxSuccessful() &&
                fakestoreProductDtoResponseEntity.getBody() != null){
            return fakestoreProductDtoResponseEntity.getBody();
        }
        return null;
    }

    // get all product details
    public ResponseEntity<FakeStoreProductDto[]> getAllProducts() {
        return
                requestForEntity("https://fakestoreapi.com/products", HttpMethod.GET,
                        null, FakeStoreProductDto[].class);
    }

    // This is PUT helper method
    public FakeStoreProductDto replaceProductById(FakeStoreProductDto dto, Long id) {

        ResponseEntity<FakeStoreProductDto> response = requestForEntity("https://fakestoreapi.com/products/{id}",
                HttpMethod.PUT, dto, FakeStoreProductDto.class, id);
        return response.getBody();
    }

    // This is POST helper method
    public ResponseEntity<FakeStoreProductDto> createProduct(FakeStoreProductDto dto) {

        // https://fakestoreapi.com/products
        ResponseEntity<FakeStoreProductDto> response = requestForEntity("https://fakestoreapi.com/products",
                                HttpMethod.POST, dto, FakeStoreProductDto.class);
        return response;
    }

    // Generic method for GET/POST/PUT/DELETE
    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request,
                                               Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}
