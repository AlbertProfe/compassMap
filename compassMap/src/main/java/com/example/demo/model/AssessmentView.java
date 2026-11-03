package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentView {

    @Id
    private String id;

    // one customer can run many assessments
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private Instant timestamp;
    private int qtyQuestions;

    // the assessment owns its Q&A rows: FK assessment_view_id lives in question_and_answer
    // WRITE_ONLY: accepted in POST body, not serialized (use /api/v1/assessments/questionsAndAnswers)
    @OneToMany(mappedBy = "assessmentView", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<QuestionAndAnswer> questionsAndAnswers = new ArrayList<>();

    // resulting profile, shared (not owned)
    @ManyToOne
    @JoinColumn(name = "customer_profile_id")
    private CustomerProfile profile;

    // keeps both sides of the relationship consistent
    public void addQuestionAndAnswer(QuestionAndAnswer questionAndAnswer) {
        if (questionsAndAnswers == null) {
            questionsAndAnswers = new ArrayList<>();
        }
        questionsAndAnswers.add(questionAndAnswer);
        questionAndAnswer.setAssessmentView(this);
    }

    public void removeQuestionAndAnswer(QuestionAndAnswer questionAndAnswer) {
        questionsAndAnswers.remove(questionAndAnswer);
        questionAndAnswer.setAssessmentView(null);
    }
}
