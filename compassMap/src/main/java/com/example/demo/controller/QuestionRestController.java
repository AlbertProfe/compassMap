package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// REST Controller is a annotation that marks the class as a controller
// that is a component that handles HTTP requests
@RestController
// RequestMapping is a annotation that maps HTTP requests to handler methods
// it is used to map the HTTP requests to the methods in the controller
@RequestMapping("/api/v1/questions")
public class QuestionRestController {
    // Autowired is an annotation that marks the field as a dependency
    @Autowired
    private QuestionService questionService;

    //----------------------------- CREATE -----------------------------
    @PostMapping("/create")
    public Question createQuestion(@RequestBody Question question) {
        Question newQuestion = questionService.createQuestion(question);
        System.out.println("Service createQuestion: " + newQuestion);
        return newQuestion;
    }

    @PostMapping("/createAll")
    public Iterable<Question> createAllQuestions(@RequestBody Iterable<Question> questions) {
        Iterable<Question> newQuestions = questionService.saveAll(questions);
        System.out.println("Service createAllQuestions: " + questions);
        return newQuestions;
    }

    //----------------------------- UPDATE -----------------------------
    @PutMapping("/update")
    public Question updateQuestion(@RequestBody Question question) {
        if (question.getId() == null || questionService.getQuestionById(question.getId()) == null) {
            return null;
        }
        Question updatedQuestion = questionService.updateQuestion(question);
        System.out.println("Service updateQuestion: " + updatedQuestion);
        return updatedQuestion;
    }

    //----------------------------- GET -----------------------------
    @GetMapping("/all")
    public Iterable<Question> getAllQuestions() {
        // call service to get all questions and return them
        Iterable<Question> questions = questionService.getAllQuestions();
        System.out.println("Service getAllQuestions: " + questions);
        return questions;
    }

    @GetMapping("/byId")
    public Question getQuestionById(@RequestParam String id) {
        Question question = questionService.getQuestionById(id);
        System.out.println("Service getQuestionById: " + question);
        return question;
    }

    @GetMapping("/count")
    public long countQuestions() {
        long count = questionService.countQuestions();
        System.out.println("Service countQuestions: " + count);
        return count;
    }

    //----------------------------- DELETE -----------------------------
    @DeleteMapping("/byId")
    public Question deleteById(@RequestParam String id) {
        Question foundQuestion = questionService.getQuestionById(id);
        if (foundQuestion == null) return null;
        else {
            questionService.deleteQuestion(id);
            return foundQuestion;
        }

    }

    @DeleteMapping("/all")
    public void deleteAllQuestions() {
        questionService.deleteAllQuestions();
    }
}
