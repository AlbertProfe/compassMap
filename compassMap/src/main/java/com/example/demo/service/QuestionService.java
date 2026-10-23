package com.example.demo.service;

import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {

    // we are using here DEPENDENCE INJECTION
    @Autowired
    private QuestionRepository questionRepository;

    public Iterable<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question createQuestion(Question question) {
        // SET ID question to UUID (id is not sent from client)
        question.setId(UUID.randomUUID().toString());

        // SET name from order, e.g. order 3 -> "q-003"
        question.setName(String.format("q-%03d", question.getOrder()));

        // call repository to use SAVE operation to save data to h2 db
        Question createdQuestion = questionRepository.save(question);
        System.out.println("Service created question: " + createdQuestion);
        return createdQuestion;
    }

    public void deleteQuestion(String id) {
        questionRepository.deleteById(id);
    }

    public Question getQuestionById(String id) {

        Optional<Question> foundQuestion = questionRepository.findById(id);

        return foundQuestion.orElse(null);
    }

    public Iterable<Question> saveAll(Iterable<Question> questions) {
        for (Question question : questions) {
            // SET ID question to UUID (id is not sent from client)
            question.setId(UUID.randomUUID().toString());
            // SET name from order, e.g. order 3 -> "q-003"
            question.setName(String.format("q-%03d", question.getOrder()));
        }
        return questionRepository.saveAll(questions);
    }

    public void deleteAllQuestions() {
        questionRepository.deleteAll();
    }

    public long countQuestions() {
        return questionRepository.count();
    }

    public Question updateQuestion(Question incoming) {
        if (incoming.getId() == null) {
            return null;
        }
        Question existing = getQuestionById(incoming.getId());
        if (existing == null) {
            return null;
        }
        // merge only non-null fields from incoming (partial update semantics)
        if (incoming.getText() != null) {
            existing.setText(incoming.getText());
        }
        if (incoming.getDimension() != null) {
            existing.setDimension(incoming.getDimension());
        }
        existing.setOrder(incoming.getOrder());
        existing.setWeight(incoming.getWeight());
        return questionRepository.save(existing);
    }
}
