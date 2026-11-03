package com.example.demo.utils;


import com.example.demo.model.Answer;
import com.example.demo.model.AssessmentView;
import com.example.demo.model.Customer;
import com.example.demo.model.CustomerProfile;
import com.example.demo.model.Dimension;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionAndAnswer;
import com.example.demo.model.RoadMap;
import com.example.demo.repository.AssessmentViewRepository;
import com.example.demo.repository.RoadMapRepository;
import com.example.demo.service.CustomerProfileService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.QuestionAndAnswerService;
import com.example.demo.service.QuestionService;
import com.example.demo.service.RoadMapService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class PopulatorDB {

    @Autowired
    CustomerService customerService;

    @Autowired
    RoadMapService roadMapService;

    @Autowired
    CustomerProfileService customerProfileService;

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionAndAnswerService questionAndAnswerService;

    @Autowired
    AssessmentViewRepository assessmentViewRepository;

    // repository used directly to keep readable ids (RM01..RM20) from roadmaps.json
    @Autowired
    RoadMapRepository roadMapRepository;

    public List<Customer> createAndSaveCustomer (int qty){

        ArrayList<Customer> customers = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < qty; i++) {
            //customers.add(buildFakeCustomer(faker));
            customers.add(new Customer(
                    UUID.randomUUID().toString(),
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().phoneNumber(),
                    faker.address().fullAddress()
            ));
        }
        customerService.saveAll(customers);
        return customers;
    }

    private Customer buildFakeCustomer(Faker faker) {
        //Faker faker = new Faker();
        return Customer.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().phoneNumber())
                .address(faker.address().fullAddress())
                .build();
    }

    // ROADMAPS from docs/mockup/roadmaps.json, keeping ids RM01..RM20
    public List<RoadMap> loadAndSaveRoadMaps() {
        List<RoadMap> roadMaps = new ObjectMapper().readValue(
                mockupFile("roadmaps.json"),
                new TypeReference<List<RoadMap>>() {});
        roadMapRepository.saveAll(roadMaps);
        return roadMaps;
    }

    // PROFILES from docs/mockup/profiles.json, keeping ids P01..P04,
    // each profile gets the roadmaps whose name ends with "(P0x)"
    public List<CustomerProfile> loadAndSaveCustomerProfiles() {
        List<CustomerProfile> profiles = new ObjectMapper().readValue(
                mockupFile("profiles.json"),
                new TypeReference<List<CustomerProfile>>() {});
        Iterable<RoadMap> roadMaps = roadMapService.getAllRoadMaps();
        for (CustomerProfile profile : profiles) {
            List<RoadMap> assigned = new ArrayList<>();
            for (RoadMap roadMap : roadMaps) {
                if (roadMap.getName() != null && roadMap.getName().contains("(" + profile.getId() + ")")) {
                    assigned.add(roadMap);
                }
            }
            profile.setRecommendedRoadmaps(assigned);
            customerProfileService.saveCustomerProfile(profile);
        }
        return profiles;
    }

    // QUESTIONS with faker: random text and dimension, order 1..qty
    public List<Question> createAndSaveQuestions(int qty) {
        Faker faker = new Faker();
        Random random = new Random();
        Dimension[] dimensions = Dimension.values();
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            questions.add(Question.builder()
                    .text(faker.lorem().sentence() + "?")
                    .dimension(dimensions[random.nextInt(dimensions.length)])
                    .order(i + 1)
                    .weight(1.0)
                    .build());
        }
        questionService.saveAll(questions);
        return questions;
    }

    // QUESTION AND ANSWERS: one random answer for every existing question
    public List<QuestionAndAnswer> createAndSaveQuestionAndAnswers() {
        Random random = new Random();
        List<QuestionAndAnswer> questionAndAnswers = new ArrayList<>();
        for (Question question : questionService.getAllQuestions()) {
            boolean value = random.nextBoolean();
            questionAndAnswers.add(QuestionAndAnswer.builder()
                    .question(question)
                    .answer(Answer.builder().value(value).build())
                    .scoreContribution(value ? question.getWeight() : 0.0)
                    .build());
        }
        questionAndAnswerService.saveAll(questionAndAnswers);
        return questionAndAnswers;
    }

    // ASSESSMENT VIEW: one sample run linked to a random existing profile
    public AssessmentView createAndSaveAssessmentView() {
        List<CustomerProfile> profiles = new ArrayList<>();
        customerProfileService.getAllCustomerProfiles().forEach(profiles::add);
        CustomerProfile profile = profiles.isEmpty()
                ? null
                : profiles.get(new Random().nextInt(profiles.size()));
        AssessmentView assessmentView = AssessmentView.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .qtyQuestions((int) questionAndAnswerService.countQuestionAndAnswers())
                .profile(profile)
                .build();
        return assessmentViewRepository.save(assessmentView);
    }

    // mockup files live in <repo>/docs/mockup; works from repo root or from the module folder
    private File mockupFile(String name) {
        File file = new File("docs/mockup/" + name);
        return file.exists() ? file : new File("../docs/mockup/" + name);
    }



}
