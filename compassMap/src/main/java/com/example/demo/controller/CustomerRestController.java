package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// REST Controller is a annotation that marks the class as a controller
// that is a component that handles HTTP requests
@RestController
// RequestMapping is a annotation that maps HTTP requests to handler methods
// it is used to map the HTTP requests to the methods in the controller
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/getAll")
    public Iterable<Customer> getAllCustomers() {
        // call service to get all customers and return them
        Iterable<Customer> customers =  customerService.getAllCustomers();
        System.out.println("Service getAllCustomers: " + customers);
        return customers;
    }

    @GetMapping("/byId")
    public Customer getCustomerById(@RequestParam String id) {
        Customer customer = customerService.getCustomerById(id);
        System.out.println("Service getCustomerById: " + customer);
        return customer;
    }

    @GetMapping("/count")
    public long countCustomers() {
        long count = customerService.countCustomers();
        System.out.println("Service countCustomers: " + count);
        return count;
    }

    @DeleteMapping("/byId")
    public void deleteById(@RequestParam String id) {
       customerService.deleteCustomer(id);
    }

    @DeleteMapping("/all")
    public void deleteAllCustomers() {
        customerService.deleteAllCustomers();
    }
}
