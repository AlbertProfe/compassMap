package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private CustomerRepository customerRepository;

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

}
