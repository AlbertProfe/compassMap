package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

@SpringBootTest
class DemoApplicationTests {

	//@Autowired
	//private CustomerRepository customerRepository;
	@Autowired
	private CustomerService customerService;

	@Test
	void contextLoads() {
	}

	@Test
	void createCustomerObjects(){

		Customer francesca  = new Customer();
		Customer andres = new Customer();
		Customer cesar = new Customer();

		cesar.getALotOfMoney();

		Customer laura = new Customer("Laura", "Sol");
		francesca.setFirstName("Francesca");

		System.out.println("Laura Object" + laura);

		//customerRepository.save(francesca);
		//Scanner reader = new Scanner();


	}


	@Test
	void createCustomerEntity(){

		Customer laura = new Customer("Laura Maria", "Lopez Gasol");
		System.out.println("Laura Object: " + laura);

		// call the service and sends laura object: customerService.createCustomer(laura)
		// to save the laura object within a db
		// the service will do this task
		// customerService.createCustomer(laura) will return (2):
		// (1) customerCreated will be a real customer object
		// (2) null
		Customer customerCreated = customerService.createCustomer(laura);
		System.out.println("Laura Created: " + customerCreated);




	}



}
