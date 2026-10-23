package com.example.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

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
    // @ElementCollection is used to map a collection of basic types
    // we use Map<Dimension, Boolean> to store the answers of the customer per dimension
    // Hibernate can inject PersistentMap on load;
    // Jackson still deserializes the JSON object fine on request.
    // HashMap does not work with H2 because of the size limit of the column
    @ElementCollection
    private Map<Dimension, Boolean> answers;
    // recommended roadmap
    //private RoadMap recommendedRoadmap;


    public String toString() {
        return String.format(
                "Profile[id=%s, name='%s']",
                id, name);
    }
}
