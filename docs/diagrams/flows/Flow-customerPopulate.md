# Flow of Execution: CLI → DB Population via Spring Boot

![](https://raw.githubusercontent.com/AlbertProfe/compassMap/refs/heads/master/docs/diagrams/flows/Flow-PopulateCustomer-1.png)

The image shows the chain of calls across 6 classes, connected by colored arrows (red, green, yellow) representing different dependency/call paths. Here's the step-by-step flow:

---

## Step 1 — Spring Boot Entry Point: [DemoApplication](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/DemoApplication.java:9:0-37:1)

[`DemoApplication.java#L19-L21`](https://github.com/AlbertProfe/compassMap/blob/master/compassMap/src/main/java/com/example/demo/DemoApplication.java#L19-L21)

```java
public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
}
```

[main()](cci:1://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/DemoApplication.java:18:1-21:2) boots the Spring context. Spring scans for `@Component`, `@Service`, and other beans, creating and wiring them via `@Autowired`.

## Step 2 — [CommandLineRunner.run()](cci:1://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/DemoApplication.java:24:1-35:2) fires automatically

Because [DemoApplication](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/DemoApplication.java:9:0-37:1) **implements `CommandLineRunner`**, Spring calls [run()](cci:1://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/DemoApplication.java:24:1-35:2) immediately after the context is ready:

[`DemoApplication.java#L25-L36`](https://github.com/AlbertProfe/compassMap/blob/master/compassMap/src/main/java/com/example/demo/DemoApplication.java#L25-L36)

```java
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
```

The `populatorDB` field ([line 14](https://github.com/AlbertProfe/compassMap/blob/master/compassMap/src/main/java/com/example/demo/DemoApplication.java#L14)) is **injected by Spring** (`@Autowired`). It is passed as an argument to the static method [BackOffice.startBackOffice()](cci:1://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/utils/BackOffice.java:10:4-41:5). This is the bridge between the **Spring-managed world** (DI) and the **plain Java SE static world** ([BackOffice](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/utils/BackOffice.java:6:0-114:1)).

> **Red arrows in the image** trace this: [main()](cci:1://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/DemoApplication.java:18:1-21:2) → [run()](cci:1://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/DemoApplication.java:24:1-35:2) → [BackOffice.startBackOffice(populatorDB)](cci:1://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/utils/BackOffice.java:10:4-41:5).

---

## Step 3 — CLI Menu Loop: [BackOffice](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/utils/BackOffice.java:6:0-114:1)

[`BackOffice.java#L14-L42`](https://github.com/AlbertProfe/compassMap/blob/master/compassMap/src/main/java/com/example/demo/utils/BackOffice.java#L14-L42)

```java
public static void startBackOffice(PopulatorDB populatorDB) {

    Scanner scan = new Scanner(System.in);

    while (true) {
        mainMenu();

        String option = askMenuOption(scan);

        switch (option) {
            case "1":
                customerLoop(scan, populatorDB);
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
```

An **infinite `while(true)` loop** presents a main menu via `Scanner`. Selecting **option "1"** enters the customer sub-loop.

> **Green arrows in the image** connect [BackOffice](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/utils/BackOffice.java:6:0-114:1) methods to [PopulatorDB](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/utils/PopulatorDB.java:14:0-53:1).

---

## Step 4 — Customer Sub-Loop

[`BackOffice.java#L44-L77`](https://github.com/AlbertProfe/compassMap/blob/master/compassMap/src/main/java/com/example/demo/utils/BackOffice.java#L44-L77)

```java
public static void customerLoop(Scanner scan, PopulatorDB populatorDB) {
    boolean inCustomerMenu = true;
    while (inCustomerMenu) {
        customerMenu();
        String custOption = scan.nextLine();
        switch (custOption) {
            case "1":
                System.out.print("How many customers? ");
                int count = Integer.parseInt(scan.nextLine());
                populatorDB.createAndSaveCustomer(count);
                System.out.println(count + " customers created and saved.");
                break;
```

When the user types **"1"** (Create customers), the loop asks "How many?" and calls [populatorDB.createAndSaveCustomer(count)](cci:1://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/utils/PopulatorDB.java:20:4-38:5).

---

## Step 5 — Fake Data Generation: [PopulatorDB](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/utils/PopulatorDB.java:14:0-53:1)

[`PopulatorDB.java#L21-L39`](https://github.com/AlbertProfe/compassMap/blob/master/compassMap/src/main/java/com/example/demo/utils/PopulatorDB.java#L21-L39)

```java
public List<Customer> createAndSaveCustomer (int qty){

    ArrayList<Customer> customers = new ArrayList<>();
    Faker faker = new Faker();

    for (int i = 0; i < qty; i++) {
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
```

- A `for` loop creates `qty` [Customer](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/model/Customer.java:9:0-35:1) objects using **JavaFaker** for synthetic data and `UUID` for IDs.
- Each [Customer](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/model/Customer.java:9:0-35:1) is a **JPA `@Entity`** ([`Customer.java#L10-L26`](https://github.com/AlbertProfe/compassMap/blob/master/compassMap/src/main/java/com/example/demo/model/Customer.java#L10-L26)).
- After the loop, it delegates persistence to [customerService.saveAll()](cci:1://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/service/CustomerService.java:42:4-44:5).

> **Yellow arrows in the image** trace this path: [PopulatorDB](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/utils/PopulatorDB.java:14:0-53:1) → [CustomerService](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/service/CustomerService.java:11:0-54:1) → [CustomerRepository](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/repository/CustomerRepository.java:8:0-8:79) → DB.

---

## Step 6 — Service Layer: [CustomerService](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/service/CustomerService.java:11:0-54:1)

[`CustomerService.java#L43-L45`](https://github.com/AlbertProfe/compassMap/blob/master/compassMap/src/main/java/com/example/demo/service/CustomerService.java#L43-L45)

```java
public void saveAll(ArrayList<Customer> customers) {
    customerRepository.saveAll(customers);
}
```

A thin wrapper that delegates directly to the repository. The `customerRepository` is itself **`@Autowired`** ([line 17](https://github.com/AlbertProfe/compassMap/blob/master/compassMap/src/main/java/com/example/demo/service/CustomerService.java#L17)).

---

## Step 7 — Repository (JPA CRUD): [CustomerRepository](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/repository/CustomerRepository.java:8:0-8:79)

[`CustomerRepository.java#L9`](https://github.com/AlbertProfe/compassMap/blob/master/compassMap/src/main/java/com/example/demo/repository/CustomerRepository.java#L9)

```java
public interface CustomerRepository extends CrudRepository<Customer, String> {}
```

No implementation needed — **Spring Data JPA auto-generates** the [saveAll()](cci:1://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/service/CustomerService.java:42:4-44:5), `deleteById()`, `deleteAll()`, `count()`, and `findById()` methods at runtime from `CrudRepository<Customer, String>`. This issues the actual SQL `INSERT` statements against the database (likely H2 in-memory).

---

## Summary: Complete Call Chain

```
main()
  → SpringApplication.run()          // boots Spring context, creates all beans
    → run(args)                       // CommandLineRunner callback
      → BackOffice.startBackOffice(populatorDB)   // static call, passes Spring bean
        → while(true) mainMenu loop               // CLI Scanner loop
          → customerLoop(scan, populatorDB)        // user picks "1"
            → populatorDB.createAndSaveCustomer(n) // generates n fake Customers
              → customerService.saveAll(list)      // service layer
                → customerRepository.saveAll(list) // JPA CrudRepository → SQL INSERT → DB
```

**Key architectural pattern**: [DemoApplication](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/DemoApplication.java:9:0-37:1) is the only class that lives in both the Spring DI world and the static Java SE world. It receives `populatorDB` via `@Autowired` and passes it as a method argument to the static [BackOffice](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/utils/BackOffice.java:6:0-114:1), which then has access to the entire Spring-managed chain ([PopulatorDB](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/utils/PopulatorDB.java:14:0-53:1) → [CustomerService](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/service/CustomerService.java:11:0-54:1) → [CustomerRepository](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/repository/CustomerRepository.java:8:0-8:79) → DB) without itself being a Spring bean.
