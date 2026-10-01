package com.example.demo.service;

import com.example.demo.repository.CustomerRepository;
import com.example.demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    // we are using here DEPENDENCE INJECTION
    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer (Customer customer){
        // SET ID customer to UUID
        customer.setId(UUID.randomUUID().toString());

        // call repository to use SAVE operation to save data to h2 db
        Customer createdCustomer = customerRepository.save(customer);
        System.out.println("Service created customer: " +  createdCustomer);
        return createdCustomer;
    }

    public void deleteCustomer (String id){

      customerRepository.deleteById(id);


    }

    public Customer getCustomerById(String id){

        Optional<Customer> foundCustomer = customerRepository.findById(id);

        return foundCustomer.get();
    }

    public void saveAll(ArrayList<Customer> customers) {
        customerRepository.saveAll(customers);
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    public long countCustomers() {
        return customerRepository.count();
    }

}
