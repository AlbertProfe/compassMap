package com.example.demo.service;

import com.example.demo.model.AssessmentView;
import com.example.demo.model.QuestionAndAnswer;
import com.example.demo.repository.AssessmentViewRepository;
import com.example.demo.repository.CustomerProfileRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.QuestionAndAnswerRepository;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssessmentViewService {

    // we are using here DEPENDENCE INJECTION
    @Autowired
    private AssessmentViewRepository assessmentViewRepository;

    @Autowired
    private QuestionAndAnswerRepository questionAndAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    public List<AssessmentView> getAllAssessmentViews() {
        return assessmentViewRepository.findAll();
    }

    // one assessment run: the Q&A rows are saved by cascade (CascadeType.ALL)
    public AssessmentView createAssessmentView(AssessmentView assessmentView) {
        // SET ID assessmentView to UUID (id is not sent from client)
        assessmentView.setId(UUID.randomUUID().toString());
        if (assessmentView.getTimestamp() == null) {
            assessmentView.setTimestamp(Instant.now());
        }

        // customer and profile are referenced by id, not created here
        if (assessmentView.getCustomer() != null && assessmentView.getCustomer().getId() != null) {
            assessmentView.setCustomer(customerRepository.findById(assessmentView.getCustomer().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + assessmentView.getCustomer().getId())));
        }
        if (assessmentView.getProfile() != null && assessmentView.getProfile().getId() != null) {
            assessmentView.setProfile(customerProfileRepository.findById(assessmentView.getProfile().getId())
                    .orElseThrow(() -> new IllegalArgumentException("CustomerProfile not found: " + assessmentView.getProfile().getId())));
        }

        // Rebuild the questionsAndAnswers list using the helper so that both sides of the
        // bidirectional association point to each other.
        //
        // Why this is necessary:
        // - QuestionAndAnswer is the *owning* side of the relationship (it holds the FK
        //   assessment_view_id).
        // - The inverse side (AssessmentView.questionsAndAnswers) is mappedBy, so Hibernate
        //   ignores whatever is in that collection when deciding the FK value.
        // - Calling assessmentView.addQuestionAndAnswer(...) sets BOTH sides:
        //     1. adds the Q&A to the collection, and
        //     2. sets questionAndAnswer.assessmentView = this
        //   Without the second step the FK would be saved as NULL.
        //
        // The incoming list is first copied (defensive copy) and then the entity's collection
        // is cleared so we can rebuild it cleanly with properly managed entities.
        List<QuestionAndAnswer> incoming = assessmentView.getQuestionsAndAnswers() == null
                ? new ArrayList<>()
                : new ArrayList<>(assessmentView.getQuestionsAndAnswers());

        // Start with a fresh empty collection on the aggregate root
        assessmentView.setQuestionsAndAnswers(new ArrayList<>());

        for (QuestionAndAnswer questionAndAnswer : incoming) {

            // Every QuestionAndAnswer is a new entity – generate its primary key
            questionAndAnswer.setId(UUID.randomUUID().toString());

            // The client must reference an *existing* Question by id only
            // (Questions are associations, not owned by the assessment)
            if (questionAndAnswer.getQuestion() == null || questionAndAnswer.getQuestion().getId() == null) {
                throw new IllegalArgumentException("QuestionAndAnswer requires an existing question id");
            }

            String questionId = questionAndAnswer.getQuestion().getId();

            // Replace the stub Question (only id) with the fully-loaded managed entity
            // so that Hibernate sees a real reference and does not try to insert a new Question
            questionAndAnswer.setQuestion(questionRepository.findById(questionId)
                    .orElseThrow(() -> new IllegalArgumentException("Question not found: " + questionId)));

            // Answer is a composition (owned by QuestionAndAnswer) and is therefore created
            // together with the Q&A.  Assign an id if the client did not send one.
            if (questionAndAnswer.getAnswer() != null && questionAndAnswer.getAnswer().getId() == null) {
                questionAndAnswer.getAnswer().setId(UUID.randomUUID().toString());
            }

            // Add via the helper so the bidirectional link is established correctly
            // (this also sets questionAndAnswer.assessmentView = assessmentView)
            assessmentView.addQuestionAndAnswer(questionAndAnswer);
        }

        // eventually, set qtyQuestions, since it is not set in the constructor,
        // and we have the list of questionsAndAnswers
        assessmentView.setQtyQuestions(assessmentView.getQuestionsAndAnswers().size());

        // call repository to use SAVE operation to save data to h2 db
        AssessmentView createdAssessmentView = assessmentViewRepository.save(assessmentView);
        System.out.println("Service created assessmentView: " + createdAssessmentView);
        return createdAssessmentView;
    }

    public AssessmentView getAssessmentViewById(String id) {

        Optional<AssessmentView> foundAssessmentView = assessmentViewRepository.findById(id);

        return foundAssessmentView.orElse(null);
    }

    public List<AssessmentView> getAssessmentViewsByCustomerId(String customerId) {
        return assessmentViewRepository.findByCustomerId(customerId);
    }

    // query by FK instead of navigating the LAZY collection outside a transaction
    public List<QuestionAndAnswer> getQuestionAndAnswersByAssessmentViewId(String assessmentViewId) {
        return questionAndAnswerRepository.findByAssessmentViewId(assessmentViewId);
    }

    public double getTotalScore(String assessmentViewId) {
        double total = 0.0;
        for (QuestionAndAnswer questionAndAnswer : getQuestionAndAnswersByAssessmentViewId(assessmentViewId)) {
            if (questionAndAnswer.getScoreContribution() != null) {
                total += questionAndAnswer.getScoreContribution();
            }
        }
        return total;
    }

    public void deleteAssessmentView(String id) {
        assessmentViewRepository.deleteById(id);
    }

    public void deleteAllAssessmentViews() {
        assessmentViewRepository.deleteAll();
    }

    public long countAssessmentViews() {
        return assessmentViewRepository.count();
    }
}
