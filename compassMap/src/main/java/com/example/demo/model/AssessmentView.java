package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

//@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentView {

    //@Id
    private String id;

    private Customer customer;
    private Instant timestamp;
    private int qtyQuestions;

    private List<QuestionAndAnswer> questionsAndAnswers;

    private CustomerProfile profile;


}
