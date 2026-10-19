package com.example.demo.repository;

import com.example.demo.model.Customer;
import com.example.demo.model.RoadMap;
import org.springframework.data.repository.CrudRepository;




public interface CustomerRepository extends CrudRepository<Customer, String> {}

