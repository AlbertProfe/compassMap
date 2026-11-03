package com.example.demo.repository;

import com.example.demo.model.CustomerProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerProfileRepository extends CrudRepository<CustomerProfile, String> {

    Optional<CustomerProfile> findByCustomerId(String customerId);
}
