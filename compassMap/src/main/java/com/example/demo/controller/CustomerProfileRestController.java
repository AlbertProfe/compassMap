package com.example.demo.controller;


import com.example.demo.model.CustomerProfile;
import com.example.demo.service.CustomerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customerProfiles")
public class CustomerProfileRestController {

    @Autowired
    private CustomerProfileService customerProfileService;


    //----------------------------- CREATE -----------------------------
    @PostMapping("/create")
    public CustomerProfile createCustomerProfile(@RequestBody CustomerProfile customerProfile) {
        CustomerProfile newCustomerProfile = customerProfileService.createCustomerProfile(customerProfile);
        System.out.println("Service createCustomerProfile: " + newCustomerProfile);
        return newCustomerProfile;
    }

    @PostMapping("/createAll")
    public Iterable<CustomerProfile> createAllCustomerProfiles(@RequestBody Iterable<CustomerProfile> customerProfiles) {
        Iterable<CustomerProfile> newCustomerProfiles = customerProfileService.saveAll(customerProfiles);
        System.out.println("Service createAllCustomerProfiles: " + customerProfiles);
        return newCustomerProfiles;
    }

    //----------------------------- UPDATE -----------------------------
    @PutMapping("/update")
    public CustomerProfile updateCustomerProfile(@RequestBody CustomerProfile customerProfile) {
        if (customerProfile.getId() == null || customerProfileService.getCustomerProfileById(customerProfile.getId()) == null) {
            return null;
        }
        CustomerProfile updatedCustomerProfile = customerProfileService.updateCustomerProfile(customerProfile);
        System.out.println("Service updateCustomerProfile: " + updatedCustomerProfile);
        return updatedCustomerProfile;
    }

    //----------------------------- GET -----------------------------
    @GetMapping("/all")
    public Iterable<CustomerProfile> getAllCustomerProfiles() {
        // call service to get all customerProfiles and return them
        Iterable<CustomerProfile> customerProfiles = customerProfileService.getAllCustomerProfiles();
        System.out.println("Service getAllCustomerProfiles: " + customerProfiles);
        return customerProfiles;
    }

    @GetMapping("/byId")
    public CustomerProfile getCustomerProfileById(@RequestParam String id) {
        CustomerProfile customerProfile = customerProfileService.getCustomerProfileById(id);
        System.out.println("Service getCustomerProfileById: " + customerProfile);
        return customerProfile;
    }

    @GetMapping("/count")
    public long countCustomerProfiles() {
        long count = customerProfileService.countCustomerProfiles();
        System.out.println("Service countCustomerProfiles: " + count);
        return count;
    }

    //----------------------------- DELETE -----------------------------
    @DeleteMapping("/byId")
    public CustomerProfile deleteById(@RequestParam String id) {
        CustomerProfile foundCustomerProfile = customerProfileService.getCustomerProfileById(id);
        if (foundCustomerProfile == null) return null;
        else {
            customerProfileService.deleteCustomerProfile(id);
            return foundCustomerProfile;
        }

    }

    @DeleteMapping("/all")
    public void deleteAllCustomerProfiles() {
        customerProfileService.deleteAllCustomerProfiles();
    }





}
