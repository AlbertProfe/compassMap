package com.example.demo.repository;

import com.example.demo.model.AssessmentView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentViewRepository extends JpaRepository<AssessmentView, String> {

    List<AssessmentView> findByCustomerId(String customerId);
}
