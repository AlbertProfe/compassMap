package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoadMap {

    // fields
    @Id
    private String id;
    private String name;
    private String description;
    // stored in its own table road_map_steps_items_summary
    // (road_map_id FK + key + value columns), not a serialized blob
    // @ElementCollection is used to map a collection of basic types
    // we use Map<Dimension, Boolean> to store the answers of the customer per dimension
    // Hibernate can inject PersistentMap on load;
    // Jackson still deserializes the JSON object fine on request.
    // HashMap does not work with H2 because of the size limit of the column
    @ElementCollection
    private Map<String, String> stepsItemsSummary;
    private int steps;
    private boolean completed;

    //@OneToMany(mappedBy = "recommendedRoadmap", fetch = FetchType.LAZY)
    //@JsonIgnore
    //@EqualsAndHashCode.Exclude
    //private List<CustomerProfile> profiles;

    @ManyToMany(fetch = FetchType.EAGER,
            mappedBy = "recommendedRoadmaps")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private List<CustomerProfile> profiles;

    // methods
    public String toString() {
        return String.format(
                "RoadMap[id=%s, name='%s', description='%s', steps='%s', completed='%s']",
                id, name, description, steps, completed);
    }

}
