package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RoadMap {

    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    private Integer steps;
    private Boolean completed;

    //constructor
    public RoadMap() {}

    public RoadMap(String name, String description, Integer steps, Boolean completed) {
        this.name = name;
        this.description = description;
        this.steps = steps;
        this.completed = completed;
    }

    //methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    // methods
    public String toString() {
        return String.format(
                "RoadMap[id=%s, name='%s', description='%s', steps=%s, completed=%s]",
                id, name, description, steps, completed);
    }
}
