# Synthetic data algorithm approaches


Here are **4 different practical approaches** in a Spring Boot service to generate **1,000 `Customer` objects** with **Java Faker** and persist them via a `customerService.save(...)` / `saveAll(...)` method.

Assumptions used in all examples:
- `Customer` is a JPA entity
- `CustomerService` has at least:
  - `Customer save(Customer customer)`
  - `List<Customer> saveAll(List<Customer> customers)` (recommended)
- Dependency: `com.github.javafaker:javafaker`

---

### Approach 1: Simple sequential loop (individual saves)

```java
@Service
@RequiredArgsConstructor
public class CustomerGeneratorService {

    private final CustomerService customerService;
    private final Faker faker = new Faker();

    public void create1000CustomersIndividually() {
        for (int i = 0; i < 1000; i++) {
            Customer customer = buildFakeCustomer();
            customerService.save(customer);   // one by one
        }
    }

    private Customer buildFakeCustomer() {
        return Customer.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().phoneNumber())
                .address(faker.address().fullAddress())
                .build();
    }
}
```

**Pros:** Very simple, easy to debug.  
**Cons:** Slow (1000 separate transactions/DB round-trips).

---

### Approach 2: Collect in a List + `saveAll` (recommended for most cases)

```java
@Service
@RequiredArgsConstructor
public class CustomerGeneratorService {

    private final CustomerService customerService;
    private final Faker faker = new Faker();

    @Transactional
    public void create1000CustomersBatch() {
        List<Customer> customers = new ArrayList<>(1000);

        for (int i = 0; i < 1000; i++) {
            customers.add(buildFakeCustomer());
        }

        customerService.saveAll(customers);   // single batch
    }

    private Customer buildFakeCustomer() {
        return Customer.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().phoneNumber())
                .address(faker.address().fullAddress())
                .build();
    }
}
```

**Pros:** Much faster, fewer DB round-trips, still easy to read.  
**Cons:** Loads all 1000 objects into memory at once (usually fine for 1k).

---

### Approach 3: Stream + `saveAll` (functional / concise style)

```java
@Service
@RequiredArgsConstructor
public class CustomerGeneratorService {

    private final CustomerService customerService;
    private final Faker faker = new Faker();

    @Transactional
    public void create1000CustomersWithStream() {
        List<Customer> customers = IntStream.range(0, 1000)
                .mapToObj(i -> buildFakeCustomer())
                .toList();                 // Java 16+ (or collect(Collectors.toList()) for older)

        customerService.saveAll(customers);
    }

    private Customer buildFakeCustomer() {
        return Customer.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().phoneNumber())
                .address(faker.address().fullAddress())
                .build();
    }
}
```

**Pros:** Clean, functional style, still uses efficient `saveAll`.  
**Cons:** Slightly less obvious for beginners; same memory characteristics as Approach 2.

---

### Approach 4: Chunked / Batched inserts (e.g. 100 at a time)

Useful when you want to avoid loading all 1,000 objects into memory at once or when the database has limits on batch size.

```java
@Service
@RequiredArgsConstructor
public class CustomerGeneratorService {

    private final CustomerService customerService;
    private final Faker faker = new Faker();

    private static final int BATCH_SIZE = 100;

    @Transactional
    public void create1000CustomersInChunks() {
        List<Customer> batch = new ArrayList<>(BATCH_SIZE);

        for (int i = 0; i < 1000; i++) {
            batch.add(buildFakeCustomer());

            if (batch.size() == BATCH_SIZE) {
                customerService.saveAll(batch);
                batch.clear();
            }
        }

        // save any remaining customers
        if (!batch.isEmpty()) {
            customerService.saveAll(batch);
        }
    }

    private Customer buildFakeCustomer() {
        return Customer.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().phoneNumber())
                .address(faker.address().fullAddress())
                .build();
    }
}
```

**Pros:** Controlled memory usage, better for larger volumes, works well with JDBC batch settings.  
**Cons:** Slightly more code.

---

### Approach 5: Asynchronous version (`@Async`)

Runs the generation in a background thread so the calling method returns immediately.

```java
@Service
@RequiredArgsConstructor
public class CustomerGeneratorService {

    private final CustomerService customerService;
    private final Faker faker = new Faker();

    @Async
    @Transactional
    public void create1000CustomersAsync() {
        List<Customer> customers = IntStream.range(0, 1000)
                .mapToObj(i -> buildFakeCustomer())
                .toList();

        customerService.saveAll(customers);
    }

    private Customer buildFakeCustomer() {
        return Customer.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().phoneNumber())
                .address(faker.address().fullAddress())
                .build();
    }
}
```

**Required configuration** (add once in your project):

```java
@Configuration
@EnableAsync
public class AsyncConfig {
    // optional: customize the executor
}
```

**Pros:** Non-blocking for the caller, good for long-running generation.  
**Cons:** Needs `@EnableAsync`, harder to handle errors/return values (you can switch to `CompletableFuture` if needed).

---


### Approach 6: Async with `CompletableFuture`

```java
@Service
@RequiredArgsConstructor
public class CustomerGeneratorService {

    private final CustomerService customerService;
    private final Faker faker = new Faker();

    // Optional: inject a custom executor for better control
    // private final TaskExecutor taskExecutor;

    public CompletableFuture<List<Customer>> create1000CustomersAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<Customer> customers = IntStream.range(0, 1000)
                    .mapToObj(i -> buildFakeCustomer())
                    .toList();

            return customerService.saveAll(customers);
        });
        // .thenApply(...) or .exceptionally(...) can be chained by the caller
    }

    private Customer buildFakeCustomer() {
        return Customer.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().phoneNumber())
                .address(faker.address().fullAddress())
                .build();
    }
}
```

**Usage example:**

```java
customerGeneratorService.create1000CustomersAsync()
    .thenAccept(saved -> log.info("Saved {} customers", saved.size()))
    .exceptionally(ex -> {
        log.error("Failed to create customers", ex);
        return null;
    });
```

**Notes:**
- Returns a `CompletableFuture<List<Customer>>` so the caller can react to success/failure.
- Uses the common ForkJoinPool by default. For production you can pass a custom `Executor` / Spring `TaskExecutor`.
- No need for `@EnableAsync` (unlike the pure `@Async` approach).

---

### Summary of all approaches

| # | Approach                        | Style              | Blocking? | Memory   | Best for                          |
|---|---------------------------------|--------------------|-----------|----------|-----------------------------------|
| 1 | Individual `save`               | Loop               | Yes       | Low      | Debugging                         |
| 2 | Full list + `saveAll`           | Loop               | Yes       | Medium   | **Default / most cases**          |
| 3 | Stream + `saveAll`              | Functional         | Yes       | Medium   | Clean / modern code               |
| 4 | Chunked batches                 | Loop + batches     | Yes       | Low      | Larger datasets / memory control  |
| 5 | `@Async`                        | Annotation-based   | No        | Medium   | Simple fire-and-forget background |
| 6 | `CompletableFuture`             | Explicit async     | No        | Medium   | **Composable async + result handling** |
