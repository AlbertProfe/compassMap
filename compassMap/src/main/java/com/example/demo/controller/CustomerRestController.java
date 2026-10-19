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
    // Autowired is an annotation that marks the field as a dependency
    @Autowired
    private CustomerService customerService;

    //----------------------------- CREATE -----------------------------
    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        Customer newCustomer = customerService.createCustomer(customer);
        System.out.println("Service createCustomer: " + newCustomer);
        return newCustomer;
    }

    @PostMapping("/createAll")
    public Iterable<Customer> createAllCustomers(@RequestBody Iterable<Customer> customers) {
        Iterable<Customer> newCustomers = customerService.saveAll(customers);
        System.out.println("Service createAllCustomers: " + customers);
        return newCustomers;
    }

    //----------------------------- UPDATE -----------------------------
    @PutMapping("/update")
    public Customer updateCustomer(@RequestBody Customer customer) {
        if (customer.getId() == null || customerService.getCustomerById(customer.getId()) == null) {
            return null;
        }
        Customer updatedCustomer = customerService.updateCustomer(customer);
        System.out.println("Service updateCustomer: " + updatedCustomer);
        return updatedCustomer;
    }

    //----------------------------- GET -----------------------------
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

    //----------------------------- DELETE -----------------------------
    @DeleteMapping("/byId")
    public Customer deleteById(@RequestParam String id) {
       Customer foundCustomer = customerService.getCustomerById(id);
       if (foundCustomer == null) return null;
       else {
           customerService.deleteCustomer(id);
           return foundCustomer;
       }

    }

    @DeleteMapping("/all")
    public void deleteAllCustomers() {
        customerService.deleteAllCustomers();
    }
}
