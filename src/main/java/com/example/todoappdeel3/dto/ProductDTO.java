
package com.example.todoappdeel3.dto;
import com.example.todoappdeel3.models.Category;
import com.fasterxml.jackson.annotation.JsonAlias;
public class ProductDTO {
    public String name;
    public String description;
    public Number price;
    public Category category;
    public String imageUrl;
    @JsonAlias("category_id")
    public long categoryId;
    public ProductDTO(String name, String description, Number price, Category category, long categoryId, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
    }
}
