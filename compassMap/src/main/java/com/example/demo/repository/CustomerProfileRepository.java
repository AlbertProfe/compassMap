package com.example.demo.repository;

import com.example.demo.model.CustomerProfile;
import org.springframework.data.repository.CrudRepository;

public interface CustomerProfileRepository extends CrudRepository<CustomerProfile, String> {
}
