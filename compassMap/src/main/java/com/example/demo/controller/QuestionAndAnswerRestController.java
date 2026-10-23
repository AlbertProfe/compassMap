package com.example.demo.controller;

import com.example.demo.model.QuestionAndAnswer;
import com.example.demo.service.QuestionAndAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// REST Controller is a annotation that marks the class as a controller
// that is a component that handles HTTP requests
@RestController
// RequestMapping is a annotation that maps HTTP requests to handler methods
// it is used to map the HTTP requests to the methods in the controller
@RequestMapping("/api/v1/questionAndAnswers")
public class QuestionAndAnswerRestController {
    // Autowired is an annotation that marks the field as a dependency
    @Autowired
    private QuestionAndAnswerService questionAndAnswerService;

    //----------------------------- CREATE -----------------------------
    @PostMapping("/create")
    public QuestionAndAnswer createQuestionAndAnswer(@RequestBody QuestionAndAnswer questionAndAnswer) {
        QuestionAndAnswer newQuestionAndAnswer = questionAndAnswerService.createQuestionAndAnswer(questionAndAnswer);
        System.out.println("Service createQuestionAndAnswer: " + newQuestionAndAnswer);
        return newQuestionAndAnswer;
    }

    @PostMapping("/createAll")
    public Iterable<QuestionAndAnswer> createAllQuestionAndAnswers(@RequestBody Iterable<QuestionAndAnswer> questionAndAnswers) {
        Iterable<QuestionAndAnswer> newQuestionAndAnswers = questionAndAnswerService.saveAll(questionAndAnswers);
        System.out.println("Service createAllQuestionAndAnswers: " + questionAndAnswers);
        return newQuestionAndAnswers;
    }

    //----------------------------- UPDATE -----------------------------
    @PutMapping("/update")
    public QuestionAndAnswer updateQuestionAndAnswer(@RequestBody QuestionAndAnswer questionAndAnswer) {
        if (questionAndAnswer.getId() == null || questionAndAnswerService.getQuestionAndAnswerById(questionAndAnswer.getId()) == null) {
            return null;
        }
        QuestionAndAnswer updatedQuestionAndAnswer = questionAndAnswerService.updateQuestionAndAnswer(questionAndAnswer);
        System.out.println("Service updateQuestionAndAnswer: " + updatedQuestionAndAnswer);
        return updatedQuestionAndAnswer;
    }

    //----------------------------- GET -----------------------------
    @GetMapping("/all")
    public Iterable<QuestionAndAnswer> getAllQuestionAndAnswers() {
        // call service to get all questionAndAnswers and return them
        Iterable<QuestionAndAnswer> questionAndAnswers = questionAndAnswerService.getAllQuestionAndAnswers();
        System.out.println("Service getAllQuestionAndAnswers: " + questionAndAnswers);
        return questionAndAnswers;
    }

    @GetMapping("/byId")
    public QuestionAndAnswer getQuestionAndAnswerById(@RequestParam String id) {
        QuestionAndAnswer questionAndAnswer = questionAndAnswerService.getQuestionAndAnswerById(id);
        System.out.println("Service getQuestionAndAnswerById: " + questionAndAnswer);
        return questionAndAnswer;
    }

    @GetMapping("/count")
    public long countQuestionAndAnswers() {
        long count = questionAndAnswerService.countQuestionAndAnswers();
        System.out.println("Service countQuestionAndAnswers: " + count);
        return count;
    }

    //----------------------------- DELETE -----------------------------
    @DeleteMapping("/byId")
    public QuestionAndAnswer deleteById(@RequestParam String id) {
        QuestionAndAnswer foundQuestionAndAnswer = questionAndAnswerService.getQuestionAndAnswerById(id);
        if (foundQuestionAndAnswer == null) return null;
        else {
            questionAndAnswerService.deleteQuestionAndAnswer(id);
            return foundQuestionAndAnswer;
        }

    }

    @DeleteMapping("/all")
    public void deleteAllQuestionAndAnswers() {
        questionAndAnswerService.deleteAllQuestionAndAnswers();
    }
}
