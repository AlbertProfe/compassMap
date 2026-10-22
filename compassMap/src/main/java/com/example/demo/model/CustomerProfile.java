package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfile {

    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private  CodeProfile codeProfile;
    private String name;
    private String description;
    private String goal;
    // answers of the customer per dimension
    private HashMap<Dimension, Boolean> answers;
    // recommended roadmap
    private RoadMap recommendedRoadmap;


    public String toString() {
        return String.format(
                "Profile[id=%s, name='%s']",
                id, name);
    }
}
