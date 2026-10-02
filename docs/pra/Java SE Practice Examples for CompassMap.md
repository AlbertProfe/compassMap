# Java SE Practice Exercices for CompassMap

---

## 1. Console Questionnaire — Arrays, Loops, Conditionals

**Java SE concepts:** `String[]`, `for` loop, `Scanner`, `if-else` chains, accumulator variables

Implement menu option **"3. Profile"** as an interactive questionnaire. Hardcode questions in a `String[]`, collect yes/no answers, and use two `int` accumulators (`knowsCode`, `wantsToLearn`) to map the user to one of the four profiles.

```java
// inside PopulatorDB or a new utils/QuestionnaireRunner.java
public String runQuestionnaire(Scanner scan) {
    String[] questions = {
        "Have you written code professionally? (y/n)",
        "Can you read a stack trace? (y/n)",
        "Do you want to deepen architecture/DDD skills? (y/n)",
        "Do you want to ship faster with AI tools? (y/n)",
        "Are you new to programming logic? (y/n)"
    };

    int knowsCode = 0;
    int wantsToLearn = 0;

    for (int i = 0; i < questions.length; i++) {
        System.out.print(questions[i] + " ");
        String answer = scan.nextLine().trim().toLowerCase();
        if (i < 2 && answer.equals("y")) knowsCode++;
        if (i >= 2 && answer.equals("y")) wantsToLearn++;
    }

    // 2x2 matrix mapping
    if (knowsCode >= 2 && wantsToLearn >= 2) return "Architect";      // experienced + wants depth
    if (knowsCode >= 2 && wantsToLearn < 2)  return "AI Practitioner"; // experienced + wants speed
    if (knowsCode < 2 && wantsToLearn >= 2)  return "Beginner";        // new + wants foundations
    return "Non-Developer";                                             // new + needs literacy only
}
```

**Practices:** array iteration, `String.trim()`, `toLowerCase()`, `equals()`, branching logic, return values.

---

## 2. Profile Enum + HashMap Lookup

**Java SE concepts:** `enum` with fields/methods, `HashMap<String, ProfileType>`, `enum.values()`

Create a `ProfileType` enum that holds each profile's description and recommended focus, then look it up via a `HashMap`.

```java
// model/ProfileType.java (plain enum, no Spring needed)
public enum ProfileType {
    ARCHITECT("Experienced developer", "Architecture, DDD, best practices, controlled AI"),
    AI_PRACTITIONER("Coder who ships fast", "AI-assisted development, rapid prototyping"),
    BEGINNER("Absolute beginner", "Logic, algorithms, computational thinking"),
    NON_DEVELOPER("Non-technical professional", "Architectural literacy, tech communication");

    private final String situation;
    private final String focus;

    ProfileType(String situation, String focus) {
        this.situation = situation;
        this.focus = focus;
    }

    public String getSituation() { return situation; }
    public String getFocus() { return focus; }

    public void printCard() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.printf( "║ Profile: %-24s║%n", this.name());
        System.out.printf( "║ Who:     %-24s║%n", situation);
        System.out.printf( "║ Focus:   %-24s║%n", focus);
        System.out.println("╚══════════════════════════════════╝");
    }
}
```

```java
// In the run() method or a helper class
HashMap<String, ProfileType> profileMap = new HashMap<>();
profileMap.put("Architect", ProfileType.ARCHITECT);
profileMap.put("AI Practitioner", ProfileType.AI_PRACTITIONER);
profileMap.put("Beginner", ProfileType.BEGINNER);
profileMap.put("Non-Developer", ProfileType.NON_DEVELOPER);

String result = runQuestionnaire(scan);
ProfileType profile = profileMap.get(result);
profile.printCard();
```

**Practices:** enum constructors, enum fields, `HashMap.put()`/`get()`, `String.format()`/`printf`.

---

## 3. RoadMap Populator + Stream Operations + Formatted Table

**Java SE concepts:** `List<>`, `Comparator`, `Stream.filter()`, `Stream.map()`, `String.format()`

Wire up menu option **"2. Roadmap"** to create hardcoded [RoadMap](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/model/RoadMap.java:7:0-69:1) objects, then display/filter them using streams.

```java
// inside PopulatorDB
public List<RoadMap> createRoadmaps() {
    List<RoadMap> roadmaps = new ArrayList<>();
    roadmaps.add(new RoadMap("DDD Mastery",       "Domain-Driven Design deep dive",    8, false));
    roadmaps.add(new RoadMap("AI Shipping",        "LLM tools for rapid delivery",      5, false));
    roadmaps.add(new RoadMap("Code Foundations",   "Variables, loops, functions",       12, false));
    roadmaps.add(new RoadMap("Tech Literacy",      "Architecture for non-devs",          4, true));
    return roadmaps;
}
```

```java
// Display as formatted table
List<RoadMap> roadmaps = populatorDB.createRoadmaps();

System.out.printf("%-20s %-35s %5s %9s%n", "NAME", "DESCRIPTION", "STEPS", "DONE");
System.out.println("-".repeat(72));

roadmaps.stream()
    .sorted(Comparator.comparingInt(RoadMap::getSteps).reversed())
    .forEach(r -> System.out.printf("%-20s %-35s %5d %9s%n",
        r.getName(), r.getDescription(), r.getSteps(),
        r.isCompleted() ? "✓" : "✗"));

// Filter example: only incomplete roadmaps
long pending = roadmaps.stream().filter(r -> !r.isCompleted()).count();
System.out.println("\nPending roadmaps: " + pending);
```

**Practices:** `stream()`, `filter()`, `sorted()`, `forEach()`, `count()`, `Comparator`, `printf` alignment, `String.repeat()`.

---

## 4. Customer Submenu — Optional, try-catch, Method Extraction

**Java SE concepts:** `Optional`, `try-catch`, submenu pattern, `Iterable` → `List` conversion

Expand menu option **"1. Customer"** into its own submenu:

```java
// New method in DemoApplication or a ConsoleUI helper
private void customerSubMenu(Scanner scan) {
    while (true) {
        System.out.println("\n--- Customer Menu ---");
        System.out.println("a. Populate fake customers");
        System.out.println("b. List all customers");
        System.out.println("c. Find customer by ID");
        System.out.println("d. Delete customer by ID");
        System.out.println("e. Back");
        System.out.print("Choose: ");

        String opt = scan.nextLine();
        switch (opt) {
            case "a":
                System.out.print("How many? ");
                try {
                    int count = Integer.parseInt(scan.nextLine());
                    populatorDB.createAndSaveCustomer(count);
                    System.out.println(count + " customers created.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
                break;
            case "b":
                // Iterable to List conversion
                List<Customer> all = new ArrayList<>();
                customerService.findAll().forEach(all::add);
                if (all.isEmpty()) {
                    System.out.println("No customers found.");
                } else {
                    all.forEach(c -> System.out.println("  " + c));
                }
                break;
            case "c":
                System.out.print("Enter ID: ");
                String id = scan.nextLine();
                try {
                    Customer found = customerService.getCustomerById(id);
                    System.out.println("Found: " + found);
                } catch (java.util.NoSuchElementException e) {
                    System.out.println("Customer not found with ID: " + id);
                }
                break;
            case "d":
                System.out.print("Enter ID to delete: ");
                customerService.deleteCustomer(scan.nextLine());
                System.out.println("Deleted.");
                break;
            case "e":
                return;
            default:
                System.out.println("Invalid option.");
        }
    }
}
```

**Practices:** nested `while` loops, `try-catch` for `NumberFormatException` and `NoSuchElementException`, `Iterable.forEach()`, method references (`all::add`), method extraction.

> Note: you'd need to add a `findAll()` method to [CustomerService](cci:2://file:///home/albert/MyProjects/SpringProjects/compassMap/compassMap/src/main/java/com/example/demo/service/CustomerService.java:11:0-46:1) wrapping `customerRepository.findAll()`.

---

## 5. Score Calculator — int[], Arithmetic, Ternary

**Java SE concepts:** `int[]` array, index-based accumulation, ternary operator, range-based classification

A more numeric version of the questionnaire where each answer maps to a score:

```java
public ProfileType calculateProfile(Scanner scan) {
    String[] questions = {
        "Years of coding experience? (0-20): ",
        "GitHub repos you maintain? (0-50): ",
        "Interest in AI tools (1-10): ",
        "Interest in architecture (1-10): ",
        "Comfort reading documentation (1-10): "
    };
    double[] weights = {0.3, 0.1, 0.2, 0.2, 0.2};
    int[] answers = new int[questions.length];

    for (int i = 0; i < questions.length; i++) {
        System.out.print(questions[i]);
        answers[i] = Integer.parseInt(scan.nextLine());
    }

    // Weighted score
    double score = 0;
    for (int i = 0; i < answers.length; i++) {
        score += answers[i] * weights[i];
    }

    // Derived booleans
    boolean experienced = (answers[0] >= 2 || answers[1] >= 3);
    boolean wantsDepth  = answers[3] >= 6;

    return experienced && wantsDepth   ? ProfileType.ARCHITECT
         : experienced && !wantsDepth  ? ProfileType.AI_PRACTITIONER
         : !experienced && wantsDepth  ? ProfileType.BEGINNER
         :                               ProfileType.NON_DEVELOPER;
}
```

**Practices:** parallel arrays, `double[]` weights, `for` loop accumulation, chained ternary, `Integer.parseInt()`.

---

## 6. Profile Report — StringBuilder + File I/O (try-with-resources)

**Java SE concepts:** `StringBuilder`, `FileWriter`, `BufferedWriter`, `try-with-resources`, `LocalDateTime`

After determining a profile, generate and save a text report:

```java
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public void saveProfileReport(String customerName, ProfileType profile) {
    StringBuilder sb = new StringBuilder();
    sb.append("=== COMPASS MAP REPORT ===\n");
    sb.append("Date: ").append(LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append("\n");
    sb.append("Customer: ").append(customerName).append("\n");
    sb.append("Profile:  ").append(profile.name()).append("\n");
    sb.append("Situation: ").append(profile.getSituation()).append("\n");
    sb.append("Focus:     ").append(profile.getFocus()).append("\n");
    sb.append("===========================\n");

    String filename = "report_" + customerName.replaceAll("\\s+", "_") + ".txt";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        writer.write(sb.toString());
        System.out.println("Report saved to " + filename);
    } catch (IOException e) {
        System.out.println("Error writing report: " + e.getMessage());
    }
}
```

**Practices:** `StringBuilder.append()` chaining, `LocalDateTime.now()`, `DateTimeFormatter`, `try-with-resources`, `FileWriter`/`BufferedWriter`, `String.replaceAll()` regex.

---

## Summary — Java SE Concepts Covered

| #   | Exercise                | Key Java SE Topics                                                   |
| --- | ----------------------- | -------------------------------------------------------------------- |
| 1   | Console Questionnaire   | `String[]`, `for`, `if-else`, `Scanner`, `equals()`                  |
| 2   | Profile Enum + HashMap  | `enum`, `HashMap`, `printf`, encapsulation                           |
| 3   | RoadMap Streams + Table | `Stream`, `filter`, `sorted`, `Comparator`, `printf`                 |
| 4   | Customer Submenu        | `Optional`, `try-catch`, method references, nested loops             |
| 5   | Score Calculator        | `int[]`, `double[]`, weighted arithmetic, ternary chains             |
| 6   | Report File I/O         | `StringBuilder`, `FileWriter`, `try-with-resources`, `LocalDateTime` |

All six plug directly into your existing `run()` menu and use **only Java SE** constructs (no new Spring concepts), while building features that are genuinely useful for the CompassMap product. They progress from basic (`arrays` + `if-else`) to intermediate (`streams`, `enums`, `file I/O`).
