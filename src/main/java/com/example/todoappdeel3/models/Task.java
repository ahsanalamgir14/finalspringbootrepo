package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;

    private boolean isFinished = false;

     /*
    Maps the many-to-one relationship between task and category, jsonbackreference so that we do not get an
    infinite dependency loop in the request. Cascasdetype merge so the task is able to create a category if we
    seed the data to the database. Without the merge you get a persistence race condition.
    */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private Category category;

    //needed by JPA to create the entity must be present no arg constructor
    public Task() {
    }

    public Task(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    //getters and setters are needed to map all the properties to the database by JPA, could
    //also be solved by making the properties public but gives less control over the properties.
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
