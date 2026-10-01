package com.example.demo;

import com.example.demo.utils.BackOffice;
import com.example.demo.utils.PopulatorDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication  implements CommandLineRunner {

	@Autowired
	PopulatorDB populatorDB;

	//@Autowired
	//BackOffice backOffice;

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		// JAVA SE strategy with STATIC methods
		// SO we call the class and then
		// the method
		BackOffice.startBackOffice(populatorDB);

		// JAVA EE, Spring Boot tools
		// with @Autowired and DI
		//backOffice.startMenu(populatorDB);
	}

}
