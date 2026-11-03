package com.example.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    private Map<Dimension, Boolean> profileAnswers;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    // recommended roadmap
    //@ManyToOne
    //@JoinColumn(name = "roadmap_id")
    //private RoadMap recommendedRoadmap;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(name = "customer_profile_roadmap",
            joinColumns = @JoinColumn(name = "customer_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "roadmap_id"))
    private List<RoadMap> recommendedRoadmaps;



    public String toString() {
        return String.format(
                "Profile[id=%s, name='%s']",
                id, name);
    }
}
