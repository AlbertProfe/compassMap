package com.example.demo.controller;


import com.example.demo.model.CustomerProfile;
import com.example.demo.service.CustomerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customerprofiles")
public class CustomerProfileRestController {

    @Autowired
    private CustomerProfileService customerProfileService;


    //----------------------------- GET -----------------------------

    @GetMapping("/all")
    public void getAllProfiles() {
        // to do

    }

    //----------------------------- DELETE -----------------------------

    @DeleteMapping("/byId")
    public void deleteById(String id) {
        // to do

    }

    // ----------------------------- CREATE -----------------------------

    @PostMapping("/create")
    public void createProfile(CustomerProfile customerProfile) {
        // to do

    }

    @PostMapping("/all")
    public void createAllProfiles(Iterable<CustomerProfile> customerProfiles) {
        // to do

    }

    //----------------------------- UPDATE -----------------------------

    @PutMapping("/byId")
    public void updateById(CustomerProfile customerProfile) {
        // to do

    }





}
