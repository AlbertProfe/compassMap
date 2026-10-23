package com.example.demo.service;

import com.example.demo.model.QuestionAndAnswer;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionAndAnswerService {

    // we are using here DEPENDENCE INJECTION
    // to do: uncomment when QuestionAndAnswer becomes an @Entity
    // @Autowired
    // private QuestionAndAnswerRepository questionAndAnswerRepository;

    public Iterable<QuestionAndAnswer> getAllQuestionAndAnswers() {
        // to do: return questionAndAnswerRepository.findAll();
        return null;
    }

    public QuestionAndAnswer createQuestionAndAnswer(QuestionAndAnswer questionAndAnswer) {
        // SET ID questionAndAnswer to UUID (id is not sent from client)
        questionAndAnswer.setId(UUID.randomUUID().toString());

        // to do: call repository to use SAVE operation to save data to h2 db
        // QuestionAndAnswer createdQuestionAndAnswer = questionAndAnswerRepository.save(questionAndAnswer);
        System.out.println("Service created questionAndAnswer: " + questionAndAnswer);
        return questionAndAnswer;
    }

    public void deleteQuestionAndAnswer(String id) {
        // to do: questionAndAnswerRepository.deleteById(id);
    }

    public QuestionAndAnswer getQuestionAndAnswerById(String id) {

        // to do: Optional<QuestionAndAnswer> found = questionAndAnswerRepository.findById(id);
        Optional<QuestionAndAnswer> foundQuestionAndAnswer = Optional.empty();

        return foundQuestionAndAnswer.orElse(null);
    }

    public Iterable<QuestionAndAnswer> saveAll(Iterable<QuestionAndAnswer> questionAndAnswers) {
        for (QuestionAndAnswer questionAndAnswer : questionAndAnswers) {
            // SET ID questionAndAnswer to UUID (id is not sent from client)
            questionAndAnswer.setId(UUID.randomUUID().toString());
        }
        // to do: return questionAndAnswerRepository.saveAll(questionAndAnswers);
        return questionAndAnswers;
    }

    public void deleteAllQuestionAndAnswers() {
        // to do: questionAndAnswerRepository.deleteAll();
    }

    public long countQuestionAndAnswers() {
        // to do: return questionAndAnswerRepository.count();
        return 0;
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
            existing.setQuestion(incoming.getQuestion());
        }
        if (incoming.getAnswer() != null) {
            existing.setAnswer(incoming.getAnswer());
        }
        if (incoming.getScoreContribution() != null) {
            existing.setScoreContribution(incoming.getScoreContribution());
        }
        // to do: return questionAndAnswerRepository.save(existing);
        return existing;
    }
}
