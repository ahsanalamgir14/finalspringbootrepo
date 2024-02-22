package com.example.todoappdeel3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class TaskDTO {
    public String name;
    public String description;

    @JsonAlias("category_id")
    public long categoryId;

    public TaskDTO(String name, String description, long categoryId) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
    }
}
