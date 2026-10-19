package com.example.demo.utils;


import com.example.demo.model.Customer;
import com.example.demo.model.RoadMap;
import com.example.demo.service.CustomerService;
import com.example.demo.service.RoadMapService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PopulatorDB {

    @Autowired
    CustomerService customerService;

    @Autowired
    RoadMapService roadMapService;

    public List<Customer> createAndSaveCustomer (int qty){

        ArrayList<Customer> customers = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < qty; i++) {
            //customers.add(buildFakeCustomer(faker));
            customers.add(new Customer(
                    UUID.randomUUID().toString(),
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().phoneNumber(),
                    faker.address().fullAddress()
            ));
        }
        customerService.saveAll(customers);
        return customers;
    }

    private Customer buildFakeCustomer(Faker faker) {
        //Faker faker = new Faker();
        return Customer.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().phoneNumber())
                .address(faker.address().fullAddress())
                .build();
    }

    public List<RoadMap> createRoadMaps (){
        List<RoadMap> roadmaps = new ArrayList<>();
        roadmaps.add(new RoadMap("DDD Mastery",      "Domain-Driven Design deep dive", 8,  false));
        roadmaps.add(new RoadMap("AI Shipping",      "LLM tools for rapid delivery",   5,  false));
        roadmaps.add(new RoadMap("Code Foundations", "Variables, loops, functions",    12, false));
        roadmaps.add(new RoadMap("Tech Literacy",    "Architecture for non-devs",      4,  true));
        return roadmaps;
    }

    public List<RoadMap> createAndSaveRoadMap (){
        List<RoadMap> roadmaps = createRoadMaps();
        roadMapService.saveAll(roadmaps);
        return roadmaps;
    }

}
