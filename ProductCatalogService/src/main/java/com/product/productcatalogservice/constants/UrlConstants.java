package com.product.productcatalogservice.constants;

import org.springframework.stereotype.Component;

@Component
public class UrlConstants {

    final public String GETURLBYID          = "https://fakestoreapi.com/products/{id}";
    final public String GETALLPRODUCTS      = "https://fakestoreapi.com/products";
    final public String POSTPRODUCTS        = "https://fakestoreapi.com/products";
    final public String PUTPRODUCTS         = "https://fakestoreapi.com/products/{id}";
    final public String DELETEPRODUCTSBYID  = "https://fakestoreapi.com/products/{id}";
}
