package com.example.demo;

import com.example.demo.utils.PopulatorDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication  implements CommandLineRunner {

	@Autowired
	PopulatorDB populatorDB;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		Scanner scan = new Scanner(System.in);

		while (true) {
			System.out.println("\n===== MENU =====");
			System.out.println("1. Customer");
			System.out.println("2. Roadmap");
			System.out.println("3. Profile");
			System.out.println("4. Quit");
			System.out.print("Select an option: ");

			String option = scan.nextLine();

			switch (option) {
				case "1":
					System.out.print("How many customers? ");
					int count = Integer.parseInt(scan.nextLine());
					populatorDB.createAndSaveCustomer(count);
					System.out.println(count + " customers created and saved.");
					break;
				case "2":
					System.out.println("Roadmap - not implemented yet.");
					break;
				case "3":
					System.out.println("Profile - not implemented yet.");
					break;
				case "4":
					System.out.println("Goodbye!");
					return;
				default:
					System.out.println("Invalid option, try again.");
			}
		}
	}

}
