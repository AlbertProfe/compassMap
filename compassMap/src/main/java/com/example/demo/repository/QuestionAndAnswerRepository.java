package com.example.demo.repository;

import com.example.demo.model.QuestionAndAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAndAnswerRepository extends JpaRepository<QuestionAndAnswer, String> {
}
