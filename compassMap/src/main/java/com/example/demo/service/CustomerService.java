package com.example.demo.service;

import com.example.demo.repository.CustomerRepository;
import com.example.demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    // we are using here DEPENDENCE INJECTION
    @Autowired
    private CustomerRepository customerRepository;

    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

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

        return foundCustomer.orElse(null);
    }

    public Iterable<Customer> saveAll(Iterable<Customer> customers) {
        // SET ID customer to UUID if not provided
        for (Customer customer : customers) {

                customer.setId(UUID.randomUUID().toString());

        }
        return customerRepository.saveAll(customers);
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    public long countCustomers() {
        return customerRepository.count();
    }

    public Customer updateCustomer(Customer incoming) {
        if (incoming.getId() == null) {
            return null;
        }
        Customer existing = getCustomerById(incoming.getId());
        if (existing == null) {
            return null;
        }
        // merge only non-null fields from incoming (partial update semantics)
        if (incoming.getFirstName() != null) {
            existing.setFirstName(incoming.getFirstName());
        }
        if (incoming.getLastName() != null) {
            existing.setLastName(incoming.getLastName());
        }
        if (incoming.getEmail() != null) {
            existing.setEmail(incoming.getEmail());
        }
        if (incoming.getPhone() != null) {
            existing.setPhone(incoming.getPhone());
        }
        if (incoming.getAddress() != null) {
            existing.setAddress(incoming.getAddress());
        }
        return customerRepository.save(existing);
    }
}
