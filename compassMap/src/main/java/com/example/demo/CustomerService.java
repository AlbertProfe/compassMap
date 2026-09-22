package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



}
