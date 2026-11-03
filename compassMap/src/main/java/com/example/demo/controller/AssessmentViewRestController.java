package com.example.demo.controller;


import com.example.demo.model.AssessmentView;
import com.example.demo.model.QuestionAndAnswer;
import com.example.demo.service.AssessmentViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assessments")
public class AssessmentViewRestController {

    @Autowired
    private AssessmentViewService assessmentViewService;


    //----------------------------- CREATE -----------------------------
    // body: { "customer": {"id": "..."}, "profile": {"id": "P01"},
    //         "questionsAndAnswers": [ {"question": {"id": "..."}, "answer": {"value": true}, "scoreContribution": 1.0} ] }
    @PostMapping("/create")
    public AssessmentView createAssessmentView(@RequestBody AssessmentView assessmentView) {
        AssessmentView newAssessmentView = assessmentViewService.createAssessmentView(assessmentView);
        System.out.println("Service createAssessmentView: " + newAssessmentView);
        return newAssessmentView;
    }

    //----------------------------- GET -----------------------------
    @GetMapping("/all")
    public List<AssessmentView> getAllAssessmentViews() {
        List<AssessmentView> assessmentViews = assessmentViewService.getAllAssessmentViews();
        System.out.println("Service getAllAssessmentViews: " + assessmentViews);
        return assessmentViews;
    }

    @GetMapping("/byId")
    public AssessmentView getAssessmentViewById(@RequestParam String id) {
        AssessmentView assessmentView = assessmentViewService.getAssessmentViewById(id);
        System.out.println("Service getAssessmentViewById: " + assessmentView);
        return assessmentView;
    }

    @GetMapping("/byCustomerId")
    public List<AssessmentView> getAssessmentViewsByCustomerId(@RequestParam String customerId) {
        return assessmentViewService.getAssessmentViewsByCustomerId(customerId);
    }

    @GetMapping("/questionsAndAnswers")
    public List<QuestionAndAnswer> getQuestionAndAnswers(@RequestParam String id) {
        return assessmentViewService.getQuestionAndAnswersByAssessmentViewId(id);
    }

    @GetMapping("/score")
    public double getTotalScore(@RequestParam String id) {
        return assessmentViewService.getTotalScore(id);
    }

    @GetMapping("/count")
    public long countAssessmentViews() {
        long count = assessmentViewService.countAssessmentViews();
        System.out.println("Service countAssessmentViews: " + count);
        return count;
    }

    //----------------------------- DELETE -----------------------------
    @DeleteMapping("/byId")
    public AssessmentView deleteById(@RequestParam String id) {
        AssessmentView foundAssessmentView = assessmentViewService.getAssessmentViewById(id);
        if (foundAssessmentView == null) return null;
        else {
            assessmentViewService.deleteAssessmentView(id);
            return foundAssessmentView;
        }

    }

    @DeleteMapping("/all")
    public void deleteAllAssessmentViews() {
        assessmentViewService.deleteAllAssessmentViews();
    }

}
