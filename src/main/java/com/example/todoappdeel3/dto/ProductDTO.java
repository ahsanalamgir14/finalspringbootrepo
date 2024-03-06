package com.example.todoappdeel3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ProductDTO {
    public String name;
    public String description;
    public Number price;

    @JsonAlias("category_id")
    public long categoryId;

    public ProductDTO(String name, String description, Number price, long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }
}
