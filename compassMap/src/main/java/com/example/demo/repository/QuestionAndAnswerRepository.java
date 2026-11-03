package com.example.demo.repository;

import com.example.demo.model.QuestionAndAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionAndAnswerRepository extends JpaRepository<QuestionAndAnswer, String> {

    List<QuestionAndAnswer> findByAssessmentViewId(String assessmentViewId);
}
