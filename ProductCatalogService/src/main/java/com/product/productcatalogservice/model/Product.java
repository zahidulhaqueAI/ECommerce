package com.product.productcatalogservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseModel {

    private String name;
    private String description;
    private String imageUrl;
    private Category category;
}
