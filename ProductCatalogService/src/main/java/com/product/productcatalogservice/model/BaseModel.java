package com.product.productcatalogservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public abstract class BaseModel {
    private Long id;
    private Date createdAt;
    private Date lastUpdatedAt;
    private Status status;

    @Getter
    @Setter
    public static class Category extends BaseModel {

        private String name;
        private String description;
        private List<Product> products;

    }
}
