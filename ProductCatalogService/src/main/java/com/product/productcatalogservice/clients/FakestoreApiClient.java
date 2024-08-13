package com.product.productcatalogservice.clients;

import com.product.productcatalogservice.constants.UrlConstants;
import com.product.productcatalogservice.dtos.FakeStoreProductDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

@Component
public class FakestoreApiClient {

    private final RestTemplateBuilder restTemplateBuilder;
    private final UrlConstants urlConstants;

    public FakestoreApiClient(RestTemplateBuilder restTemplateBuilder, UrlConstants urlConstants) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.urlConstants = urlConstants;
    }

    // get product details by ID
    public FakeStoreProductDto getProductById(Long id) {
        String url = urlConstants.GETURLBYID + id;
        ResponseEntity<FakeStoreProductDto> fakestoreProductDtoResponseEntity =
                requestForEntity( //url,
                        "https://fakestoreapi.com/products/{id}",
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
                requestForEntity(
                        //"https://fakestoreapi.com/products",
                        urlConstants.GETALLPRODUCTS,
                        HttpMethod.GET,
                        null, FakeStoreProductDto[].class);
    }

    // This is PUT helper method
    public FakeStoreProductDto replaceProductById(FakeStoreProductDto dto, Long id) {

        ResponseEntity<FakeStoreProductDto> response = requestForEntity(
                urlConstants.PUTPRODUCTS ,
              //  "https://fakestoreapi.com/products/{id}",
                HttpMethod.PUT, dto, FakeStoreProductDto.class, id);
        return response.getBody();
    }

    // This is POST helper method
    public ResponseEntity<FakeStoreProductDto> createProduct(FakeStoreProductDto dto) {

        ResponseEntity<FakeStoreProductDto> response = requestForEntity(
                urlConstants.POSTPRODUCTS,
                //"https://fakestoreapi.com/products",
                                HttpMethod.POST, dto, FakeStoreProductDto.class);
        return response;
    }

    // This is DELETE helper method
    public void deleteProductById(Long id) throws RuntimeException {
        // check if id is valid
        ResponseEntity<FakeStoreProductDto> dto =
                requestForEntity(urlConstants.GETURLBYID ,
                        HttpMethod.GET, null, FakeStoreProductDto.class, id);

        try {
            if(dto.getBody() == null ) {
               throw new IllegalArgumentException("BadRequest. Provided id: " + id + " is not a valid id");
            }
        } catch(IllegalArgumentException ex) {
            throw  ex;
        }
        // Delete Request
        requestForEntity(urlConstants.DELETEPRODUCTSBYID ,
                HttpMethod.DELETE, null, FakeStoreProductDto.class, id);
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
