package com.example.demo;

import com.example.demo.model.CodeProfile;
import com.example.demo.model.Customer;
import com.example.demo.model.CustomerProfile;
import com.example.demo.model.RoadMap;
import com.example.demo.repository.AssessmentViewRepository;
import com.example.demo.repository.CustomerProfileRepository;
import com.example.demo.repository.RoadMapRepository;
import com.example.demo.service.CustomerService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {

	//@Autowired
	//private CustomerRepository customerRepository;
	@Autowired
	private CustomerService customerService;

	@Autowired
	private RoadMapRepository roadMapRepository;
	@Autowired
	private CustomerProfileRepository customerProfileRepository;
	@Autowired
	private AssessmentViewRepository assessmentViewRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void findCustomerById (){

		Customer customerFound1 = customerService.getCustomerById("64c7be39-d974-419f-a766-a6063346676b");
		System.out.println("Customer found 1 : " + customerFound1 );

		Customer customerFound2 = customerService.getCustomerById("banana");
		System.out.println("Customer found 2 : " + customerFound2 );


	}

	@Test
	void deleteCustomerById(){

		customerService.deleteCustomer("8b4ca0f7-5de0-4ab2-a431-a849f158cd62");
	}


	@Test
	void javaFakerCreateObjects(){


		Faker faker = new Faker();

		//String name = faker.name().fullName(); // Miss Samanta Schmidt
		//String firstName = faker.name().firstName(); // Emory
		//String lastName = faker.name().lastName(); // Barton

		//String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449

		//String titleBook = faker.book().title();

		Customer newCustomer = new Customer();

		for (int i= 0; i < 1000; i++){

			// id?
			newCustomer.setFirstName( faker.name().firstName()  );
			newCustomer.setLastName(  faker.name().lastName());

			customerService.createCustomer(newCustomer);
		}
	}

	@Test
	void customerProfileRoadMap() throws Exception {



		// we will create 20 roadmaps from the JSON file
		// JSON file is in docs/mockup/roadmaps.json
		List<RoadMap> roadMaps = new ObjectMapper().readValue(
				new File("../docs/mockup/roadmaps.json"),
				new TypeReference<List<RoadMap>>() {});

		// we will create 5 customer profiles WITH NO ROADMAP
		// JSON file is in docs/mockup/4-1.md
		List<CustomerProfile> profiles = List.of(
				CustomerProfile.builder().id("P01").codeProfile(CodeProfile.ARCHITECT)
						.name("Improve programming skills").goal("Improve programming skills")
						.description("Developers who already know how to code and want to go deeper").build(),
				CustomerProfile.builder().id("P02").codeProfile(CodeProfile.ARCHITECT)
						.name("Use AI to create projects").goal("Use AI to create projects")
						.description("People who know programming and want to boost productivity with AI").build(),
				CustomerProfile.builder().id("P03").codeProfile(CodeProfile.BEGINNER)
						.name("Starting from scratch in programming").goal("Starting from scratch in programming")
						.description("Absolute beginners who want to really learn programming").build(),
				CustomerProfile.builder().id("P04").codeProfile(CodeProfile.NON_DEVELOPER)
						.name("General introductory course").goal("General introductory course")
						.description("Non-developers who want to understand what developers do").build()
		);

		// we will assign a roadmap to each customer profile
		for (CustomerProfile profile : profiles) {
			List<RoadMap> assigned = new ArrayList<>();
			for (RoadMap roadMap : roadMaps) {
				if (roadMap.getName().contains("(" + profile.getId() + ")")) {
					assigned.add(roadMap);
				}
			}
			profile.setRecommendedRoadmaps(assigned);
		}

		//save the roadmaps
		roadMapRepository.saveAll(roadMaps);

		//save the customer profiles
		customerProfileRepository.saveAll(profiles);

		// print all roadmaps
		roadMapRepository.findAll().forEach(System.out::println);

		// print all customer profiles
		for (CustomerProfile profile : profiles) {
			CustomerProfile found = customerProfileRepository.findById(profile.getId()).orElseThrow();
			System.out.println(found + " -> " + found.getRecommendedRoadmaps());
			assertEquals(5, found.getRecommendedRoadmaps().size());
		}

	}

}
