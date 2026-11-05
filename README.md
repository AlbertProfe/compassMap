# compassMap

**compassMap** is a Spring Boot backend that runs a self-assessment for a customer (questions & answers per dimension), computes a resulting `CustomerProfile` and recommends learning `RoadMap`s.

## Tech stack

- Java 21
- Spring Boot 4 (Web MVC, Data JPA, Thymeleaf, DevTools)
- H2 database (file-based) + H2 console
- Lombok, JavaFaker (data population)
- Maven (wrapper included)

## Repository structure

| Folder | Content |
|--------|---------|
| `compassMap/` | Spring Boot project (`pom.xml`, `src/`) |
| `db/` | H2 database files |
| `docs/` | master docs, diagrams, mockups, appends |

## Data model

| Entity | Role |
|--------|------|
| `Customer` | person taking the assessment |
| `Question` | question catalogue (per `Dimension`) |
| `Answer` | a boolean answer value |
| `QuestionAndAnswer` | question + answer + score contribution |
| `CustomerProfile` | resulting profile (`CodeProfile`) linked to recommended `RoadMap`s |
| `RoadMap` | learning path |
| `AssessmentView` | one assessment run: customer, Q&A list, resulting profile |

See [docs/compassMap_v2.md](docs/compassMap_v2.md) for details and diagrams.

## REST API

| Resource | Base path |
|----------|-----------|
| Customers | `/api/v1/customers` |
| Questions | `/api/v1/questions` |
| Question & Answers | `/api/v1/questionAndAnswers` |
| Customer profiles | `/api/v1/customerProfiles` |
| Roadmaps | `/api/v1/roadmaps` |
| Assessments | `/api/v1/assessments` |

## Getting started

1. Adjust `spring.datasource.url` in `compassMap/src/main/resources/application.properties` to a local path (or use the in-memory option).
2. Run:

```bash
cd compassMap
./mvnw spring-boot:run
```

3. Open:
   - App: <http://localhost:8080>
   - H2 console: <http://localhost:8080/h2-console>

## Documentation

- [compassMap v1](docs/compassMap_v1.md)
- [compassMap v2](docs/compassMap_v2.md)
- [Commits](https://github.com/AlbertProfe/compassMap/commits/master/)
