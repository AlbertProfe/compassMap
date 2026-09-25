# compassMap masterDoc v1

## Summary

### References


- Microservices: [Spring IO](https://spring.io/)
- Spring Boot is open-source: [GitHub - spring-projects/spring-boot: Spring Boot helps you to create Spring-powered, production-grade applications and services with absolute minimum fuss.](https://github.com/spring-projects/spring-boot)
- Spring Boot Guides / Academy: [Spring Guides](https://spring.io/guides) / [Spring Academy](https://spring.academy/courses)
- Quickstart Spring Boot project: [Spring quickstart](https://spring.io/quickstart)

Historical notes:

- [Spring Framework – albertprofe wiki](https://albertprofe.dev/spring/spring-basics.html)
- [JPA Before Annotations one-to-many xml](https://github.com/AlbertProfe/ApartmentPredictor/blob/master/docs/appends/JPA_Before_Annotatons:_one-to-many-xml.md)

Projects:

- Reference project: [Spring Boot: H2 DB and Thymeleaf – albertprofe wiki](https://albertprofe.dev/springboot/boot-what-create-th-h2.html)
- ApartmentPredictor: [ApartmentPredictor github](https://github.com/AlbertProfe/ApartmentPredictor)

### Product Goal


The `product goal` is to help learners, developers, and non-technical professionals **to accurately identify their current coding/programming profile** so they can build a clear, personalized learning **roadmap** with confidence.

**Users answer a short, guided questionnaire** focused on two core dimensions: whether they already know how to program and whether they want to learn (or deepen) programming skills.

Based on their answers, the system maps them to one of four well-defined profiles drawn from the course matrix (as example):

- **Experienced developers** who want to level up in architecture, DDD, best practices and controlled AI use
- **Practitioners** who already code but prioritize speed and want to leverage AI to ship projects faster
- **Absolute beginners** seeking solid foundations in logic, algorithms and computational thinking
- **Non-developers** (product managers, designers, founders, etc.) who only need enough architectural literacy to communicate effectively with technical teams

Once the profile is determined, the application presents a clear description of the user’s situation, recommended learning focus, and a high-level roadmap tailored to that profile. This removes ambiguity and helps users stop guessing which course, content path or skill investment is right for them.

>Technically the solution consists of a Spring Boot backend that stores the profile logic, questionnaire rules and user results, and a modern frontend that guides the user through the assessment, displays the resulting profile and recommended next steps in an intuitive, mobile-friendly interface. The overall aim is simple: give every user an honest, actionable picture of where they stand so they can choose the most effective path forward instead of wasting time on mismatched content.


### Version goal

**Spring Boot Goal #1**

Create a `Spring Boot` project with a clean data model using` H2 in-memory database`. Implement `entities`, `repositories` and basic configuration only. No `controllers`, no `Thymeleaf`/views, no frontend integration in this version — pure backend foundation for the profile assessment system.

## Project

### Create project: Spring Init

- [Spring Init](https://start.spring.io/)

> `Spring Initializr` **makes bootstrapping a Spring project fast and predictable**.

Here’s a practical guide to create a `Spring Boot` web project with the dependencies we need, using Maven, Java 21, and a smooth path to IDE import (`IntelliJ IDEA`) and initial setup steps.

Start by choosing the right project setup:
- Type: `Maven` Project
- Language: `Java`
- Java: `21`
- Spring Boot: pick the latest stable 3.x line (for example 3.5.x or newer)
- Project Metadata: groupId (com.example), artifactId (demo), version (1.0.0), packaging (jar)
- Dependencies: add Spring Web MVC, Spring Data JPA, Thymeleaf, H2 Console, DevTools, and test stubs as you listed
  - spring-boot-starter-webmvc
  - spring-boot-starter-thymeleaf
  - spring-boot-starter-data-jpa
  - spring-boot-devtools
  - com.h2database:h2 (runtime)
  - spring-boot-starter-thymeleaf-test (test)
  - spring-boot-starter-webmvc-test (test)

Generate and import:
- **Click Generate to download** a zip, then unzip.
- In `IntelliJ IDEA`, choose Import Project, select the `pom.xml`, and let IDEA resolve dependencies. At first run, Maven will download all required artifacts, including transitive dependencies. This may take a few minutes on first setup.

Project structure overview:
- `src/main/java`: your Java source
- `src/main/resources`: configuration files, templates, static resources
- `src/test/java`: tests
- `pom.xml`: Maven configuration and dependency management

Configure `application.properties`:
- `src/main/resources/application.properties` (or application.yml)
- Common settings to start with:
  - spring.datasource.url=jdbc:h2:mem:testdb
  - spring.datasource.driver-class-name=org.h2.Driver
  - spring.datasource.username=sa
  - spring.datasource.password=
  - spring.jpa.hibernate.ddl-auto=update
  - spring.h2.console.enabled=true
  - server.port=8080
These enable an **`in-memory H2` database and the web-based console at `/h2-console`**.

### Mini project setup

Create a simple data model and repository:
- Define an entity, for example, a User with id, name, and email.
- Use JPA annotations: @Entity, @Id, @GeneratedValue, etc.
- Create a Spring Data JPA repository interface, e.g., UserRepository extends JpaRepository<User, Long>.
- This provides CRUD operations out of the box.

Expose a minimal controller with Thymeleaf views:
- Create a Spring MVC controller to handle HTTP requests, e.g., show a list of users and a form to add a new user.
- Use @Controller and @GetMapping, @PostMapping.
- Add a Thymeleaf template under src/main/resources/templates, such as users.html, to render pages without manual HTML assembly.
- Thymeleaf is included via the thymeleaf starter; use standard th:* attributes to bind data.

Enable H2 Console:
- Access http://localhost:8080/h2-console after the app starts, using JDBC URL jdbc:h2:mem:testdb, with default credentials (sa/empty), to inspect the in-memory database during development.

Watch for common pitfalls:
- Ensure your pom.xml aligns with Java 21 compatibility. If you see dependency resolution issues, refresh Maven in IDEA and reimport.
- If the H2 console reports missing schema, verify that spring-boot-starter-data-jpa pulls in a compatible JPA provider and that your entities are properly scanned (package structure).
- When running tests, include spring-boot-starter-test or specific test dependencies; the current list includes test siblings, so expect a clean test harness.


### Commits

- [compassMap project commits on master](https://github.com/AlbertProfe/compassMap/commits/master/)


### Project structure

```

[Wed Sep 23 11:48:48] albert@albert-VirtualBox:~/MyProjects/SpringProjects/compassMap/compassMap/src (master)
$ tree
.
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── demo
│   │               ├── Customer.java
│   │               ├── CustomerRepository.java
│   │               ├── CustomerService.java
│   │               ├── DemoApplication.java
│   │               ├── HomeController.java
│   │               └── RoadMap.java
│   └── resources
│       ├── application.properties
│       ├── static
│       └── templates
│           ├── home1.html
│           └── home2.html
└── test
    └── java
        └── com
            └── example
                └── demo
                    └── DemoApplicationTests.java

14 directories, 10 files
```


## Java classes

### Entities

```java
package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {

    //fields or attributes
    //variables to model, define, configure, identify ,etc ...
    // when we create an object
    @Id
    private String id;
    private String firstName;
    private String lastName;


    //constructor
    //we use to instantiate the class
    protected Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // methods
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getALotOfMoney(){
        return 10L;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
```


### Synthetic data

#### Command Line Runner & Java Faker

**Using CommandLineRunner + Java Faker to seed an H2 test database is highly relevant and useful** for `Spring Boot` development, testing, and demos.

> `CommandLineRunner` **runs automatically** after the `Spring` context starts and beans (including repositories) are ready.
> This makes it ideal for **one-time data initialization**: you inject the repository, create entities, and save them before the application fully serves requests. In the next example `ApartmentPredictorApplication`, it seeds three sample apartments so the in-memory H2 database is never empty on startup. This is especially valuable with H2 because the database is ephemeral—data vanishes when the JVM stops—so you need reliable, automatic population every time the app launches.

**Key benefits:**
- **Immediate usability**: Controllers, services, and prediction logic can query real data right away without manual SQL scripts or external setup.
- **Reproducible demos & development**: Developers and stakeholders always see consistent sample apartments (price, area, bedrooms, furnishing status, etc.) instead of an empty database.
- **Testing support**: Integration tests and exploratory work have data available without extra configuration or `@Sql` scripts.
- **Zero external dependencies**: Works purely in-memory with H2; no need for a real database during local runs.

**Adding Java Faker elevates this further.** Instead of hard-coding a few static records, Faker generates realistic, varied synthetic data (random but plausible prices, areas, bedroom counts, “yes/no” features, furnishing statuses). You can produce dozens or hundreds of apartments in a few lines of code, covering edge cases and volume that static samples cannot. This improves:
- Model training / prediction experiments (more diverse training-like data).
- UI and API testing under realistic load.
- Avoidance of over-fitting to a tiny hand-written set.


#### Example

> The [ApartmentPredictorApplication](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/ApartmentPredictorApplication.java:9:0-126:1) implements `CommandLineRunner`, which <mark>executes code after the Spring Boot application starts</mark>.

The **CommandLineRunner** serves as a data initialization mechanism, populating the database with test data upon application startup.

```java
package com.example.apartment_predictor;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApartmentPredictorApplication implements CommandLineRunner {

    @Autowired
    private ApartmentRepository apartmentRepository;


    public static void main(String[] args) {
        SpringApplication.run(ApartmentPredictorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Creating apartment objects...");

        // Create apartment objects based on your sample data
        Apartment apartment1 = new Apartment(
                13300000L,    // price
                7420,         // area
                4,            // bedrooms
                2,            // bathrooms
                3,            // stories
                "yes",        // mainroad
                "no",         // guestroom
                "no",         // basement
                "no",         // hotwater
                "yes",        // heating
                "yes",        // airconditioning
                2,            // parking
                "yes",        // prefarea
                "furnished"   // furnishingstatus
        );



        // Create additional sample apartments
        Apartment apartment2 = new Apartment(
                8500000L,     // price
                5200,         // area
                3,            // bedrooms
                2,            // bathrooms
                2,            // stories
                "yes",        // mainroad
                "yes",        // guestroom
                "no",         // basement
                "yes",        // hotwater
                "no",         // heating
                "yes",        // airconditioning
                1,            // parking
                "no",         // prefarea
                "semi-furnished" // furnishingstatus
        );

        Apartment apartment3 = new Apartment(
                6200000L,     // price
                3800,         // area
                2,            // bedrooms
                1,            // bathrooms
                1,            // stories
                "no",         // mainroad
                "no",         // guestroom
                "yes",        // basement
                "yes",        // hotwater
                "no",         // heating
                "no",         // airconditioning
                0,            // parking
                "yes",        // prefarea
                "unfurnished" // furnishingstatus
        );

        apartmentRepository.save(apartment1);
        apartmentRepository.save(apartment2);
        apartmentRepository.save(apartment3);

        int index = 0;
        System.out.println("\n=== Apartments in the Database ===");
        for (Apartment apartment : apartmentRepository.findAll()){
            index++;
            System.out.println("#" + index);
             System.out.println(apartment);
        }

        //apartmentRepository.findAll().forEach(System.out::println);
    }


}
```

## Spring Boot Annotations

- [SpringBoot Annotations](https://github.com/AlbertProfe/ApartmentPredictor/blob/master/docs/appends/SpringBoot-Annotations.md)

## JPA

- [JPA](https://albertprofe.dev/springboot/boot-concepts-jpa.html)

### application.properties

```properties

spring.application.name=demo

spring.datasource.url=jdbc:h2:/home/albert/MyProjects/SpringProjects/compassMap/db/compassmapdb
##spring.datasource.url=jdbc:h2:mem:testdb
#spring.datasource.url=jdbc:h2:~/test
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=albert
spring.datasource.password=1234

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```



### Repository

**CustomerRepository**

```java
public interface CustomerRepository extends CrudRepository<Customer, String> {}
```

>This Spring Data interface provides full CRUD operations for the `Customer` entity using a `String` primary key.

By extending `CrudRepository`, it automatically offers methods to `save`, `find` by ID, `find all`, `count`, and `delete` customers without writing any implementation code.

In the current version of the project it serves as the **persistence layer** for users who complete the programming-profile assessment. `Spring Data JPA` will generate the concrete repository at runtime and connect it to the `H2 in-memory database`. No custom query methods are defined yet; the interface remains minimal and ready for future extensions such as finding customers by profile type.

```java
package com.example.demo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {}
```

## Maven

> **Apache Maven** is a <mark>build tool for Java projects</mark>. Using a <mark>project object model </mark>(`POM`), Maven manages a project's compilation, testing, and documentation.

- [Maven](https://maven.apache.org/)
- [Maven Repository](https://mvnrepository.com/)

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>4.1.1</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.example</groupId>
	<artifactId>demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name/>
	<description/>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>21</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-h2console</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

## Tech Stack

- IDE: IntelliJ IDEA 2026.1 (Community Edition)

  - [Descargar IntelliJ IDEA](https://www.jetbrains.com/es-es/idea/download/?section=linux)
  - With [Installing snap on Ubuntu | Snapcraft documentation](https://snapcraft.io/docs/installing-snap-on-ubuntu): `sudo snap install intellij-idea-community --classic`

- Java 21 (or 25, 17, 11, 8) <mark>open-jdk</mark>
- <mark>JUnit 3.8.1</mark>
- Create project by <mark>Sprint Init</mark>

  - Alternative: Maven Project: **`maven-archetype-quickstart` archetype**
    - https://maven.apache.org/
    - mvn --version
      Apache Maven 3.8.7
      Maven home: /usr/share/maven
      Java version: 21.0.8, vendor: Ubuntu, runtime: /usr/lib/jvm/java-21-openjdk-amd64
      Default locale: en_US, platform encoding: UTF-8
      OS name: "linux", version: "6.8.0-83-generic", arch: "amd64", family: "unix"
