package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoadMap {

    //fields
    @Id
    //@GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    private int steps;
    private boolean completed;

    // methods
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s', email='%s', phone='%s', address='%s']",
                id, name, description, steps, completed);
    }
}
