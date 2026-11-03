package com.example.demo.service;

import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionAndAnswer;
import com.example.demo.repository.QuestionAndAnswerRepository;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionAndAnswerService {

    // we are using here DEPENDENCE INJECTION
    @Autowired
    private QuestionAndAnswerRepository questionAndAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Iterable<QuestionAndAnswer> getAllQuestionAndAnswers() {
        return questionAndAnswerRepository.findAll();
    }

    public QuestionAndAnswer createQuestionAndAnswer(QuestionAndAnswer questionAndAnswer) {
        // SET ID questionAndAnswer to UUID (id is not sent from client)
        questionAndAnswer.setId(UUID.randomUUID().toString());
        questionAndAnswer.setQuestion(resolveQuestion(questionAndAnswer.getQuestion()));
        assignAnswerId(questionAndAnswer.getAnswer());

        // call repository to use SAVE operation to save data to h2 db
        QuestionAndAnswer createdQuestionAndAnswer = questionAndAnswerRepository.save(questionAndAnswer);
        System.out.println("Service created questionAndAnswer: " + createdQuestionAndAnswer);
        return createdQuestionAndAnswer;
    }

    public void deleteQuestionAndAnswer(String id) {
        questionAndAnswerRepository.deleteById(id);
    }

    public QuestionAndAnswer getQuestionAndAnswerById(String id) {

        Optional<QuestionAndAnswer> foundQuestionAndAnswer = questionAndAnswerRepository.findById(id);

        return foundQuestionAndAnswer.orElse(null);
    }

    public Iterable<QuestionAndAnswer> saveAll(Iterable<QuestionAndAnswer> questionAndAnswers) {
        for (QuestionAndAnswer questionAndAnswer : questionAndAnswers) {
            // SET ID questionAndAnswer to UUID (id is not sent from client)
            questionAndAnswer.setId(UUID.randomUUID().toString());
            questionAndAnswer.setQuestion(resolveQuestion(questionAndAnswer.getQuestion()));
            assignAnswerId(questionAndAnswer.getAnswer());
        }
        return questionAndAnswerRepository.saveAll(questionAndAnswers);
    }

    public void deleteAllQuestionAndAnswers() {
        questionAndAnswerRepository.deleteAll();
    }

    public long countQuestionAndAnswers() {
        return questionAndAnswerRepository.count();
    }

    public QuestionAndAnswer updateQuestionAndAnswer(QuestionAndAnswer incoming) {
        if (incoming.getId() == null) {
            return null;
        }
        QuestionAndAnswer existing = getQuestionAndAnswerById(incoming.getId());
        if (existing == null) {
            return null;
        }
        // merge only non-null fields from incoming (partial update semantics)
        if (incoming.getQuestion() != null) {
            existing.setQuestion(resolveQuestion(incoming.getQuestion()));
        }
        if (incoming.getAnswer() != null) {
            assignAnswerId(incoming.getAnswer());
            existing.setAnswer(incoming.getAnswer());
        }
        if (incoming.getScoreContribution() != null) {
            existing.setScoreContribution(incoming.getScoreContribution());
        }
        return questionAndAnswerRepository.save(existing);
    }

    private void assignAnswerId(Answer answer) {
        if (answer != null && answer.getId() == null) {
            answer.setId(UUID.randomUUID().toString());
        }
    }

    private Question resolveQuestion(Question question) {
        if (question == null || question.getId() == null) {
            throw new IllegalArgumentException("QuestionAndAnswer requires an existing question id");
        }
        return questionRepository.findById(question.getId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found: " + question.getId()));
    }
}
