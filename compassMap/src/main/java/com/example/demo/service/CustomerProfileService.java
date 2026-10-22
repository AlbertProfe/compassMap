package com.example.demo.service;

import com.example.demo.repository.CustomerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerProfileService {

    @Autowired
    private CustomerProfileRepository customerProfileRepository;
}
