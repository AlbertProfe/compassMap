# compassMap Fullstack

> **compassMap** is a fullstack project: a Spring Boot backend that runs a self-assessment for a customer (questions & answers per dimension), computes a resulting `CustomerProfile` and recommends learning `RoadMap`s, plus a React frontend built with Vite.

## Tech stack

**Backend** (`compassMap/`)

- Java 21
- Spring Boot 4 (Web MVC, Data JPA, Thymeleaf, DevTools)
- H2 database (file-based) + H2 console
- Lombok, JavaFaker (data population)
- Maven (wrapper included)

**Frontend** (`compassMap-React/`)

- React and React DOM `^19.3.1`
- Axios `^1.21.0` (HTTP client)
- Vite (dev server and build)

## Monorepo

This repository is a **monorepo**: the backend and the frontend live side by side in one Git repository, with one history and one set of branches. Each project keeps its own build tool, so you build and run them separately.

- `compassMap/` is the Spring Boot backend, built with Maven.
- `compassMap-React/` is the React frontend, built with npm and Vite.
- `db/` and `docs/` are shared by both.

One `git clone` gives you everything, and a single commit can change the API and the UI together.

## Repository structure

| Folder | Content |
|--------|---------|
| `compassMap/` | Spring Boot project (`pom.xml`, `src/`) |
| `compassMap-React/` | React frontend (Vite, `package.json`, `src/`) |
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

### Prerequisites

- **JDK 21** (check with `java -version`)
- **Git**
- An IDE: **IntelliJ IDEA** (recommended), VS Code (with *Extension Pack for Java* + *Spring Boot Extension Pack*) or Eclipse/STS
- **Lombok** support enabled in your IDE (IntelliJ: *Settings > Build > Compiler > Annotation Processors > Enable annotation processing*)
- Maven is **not** required: the project ships with the Maven wrapper (`mvnw`)

### 1. Clone the repository

```bash
git clone https://github.com/AlbertProfe/compassMap.git
cd compassMap
```

### 2. Open the project in your IDE

- **IntelliJ IDEA**: *File > Open* and select the inner `compassMap/compassMap/pom.xml` (choose *Open as Project*). Wait for Maven to import dependencies.
- **VS Code**: *File > Open Folder* and select `compassMap/compassMap`.
- Set the project SDK to **Java 21** if the IDE asks.

### 3. Configure the database

Edit `compassMap/src/main/resources/application.properties` and point `spring.datasource.url` to a path on your machine, for example:

```properties
# file-based (data persists between runs)
spring.datasource.url=jdbc:h2:./db/compassmapdb
# or in-memory (data lost on shutdown)
#spring.datasource.url=jdbc:h2:mem:testdb
```

Credentials are defined in the same file (`spring.datasource.username` / `spring.datasource.password`).

### 4. Build and run

From the IDE: run `DemoApplication` (`com.example.demo.DemoApplication`).

From the terminal:

```bash
cd compassMap
./mvnw clean install      # build + run tests
./mvnw spring-boot:run    # start the app
```

On Windows use `mvnw.cmd` instead of `./mvnw`.

### 5. Try it

- App: <http://localhost:8080>
- H2 console: <http://localhost:8080/h2-console> (use the same JDBC URL, user and password as in `application.properties`)
- REST API, e.g.:

```bash
curl http://localhost:8080/api/v1/customers
```

### 6. Run the tests

```bash
cd compassMap
./mvnw test
```

## Frontend: React

The frontend lives in `compassMap-React/`. It is a React app created with Vite, and it uses Axios to fetch data (`src/App.jsx`).

### Prerequisites

- **Node.js** (LTS) and **npm** (check with `node -v` and `npm -v`)
- **Git**

### 1. Clone the repository

If you already cloned it for the backend, skip this step.

```bash
git clone https://github.com/AlbertProfe/compassMap.git
cd compassMap
```

### 2. Install the dependencies

```bash
cd compassMap-React
npm install
```

### 3. Run it in development

```bash
npm run dev
```

Open <http://localhost:5173> (Vite's default port).

### 4. Build and preview for production

```bash
npm run build     # output in dist/
npm run preview   # serve the build locally
```

### 5. Lint

```bash
npm run lint
```

## Documentation

- [compassMap v1](docs/compassMap_v1.md)
- [compassMap v2](docs/compassMap_v2.md)
- [compassMap-React v1](docs/compassMap-React_v1.md)
- [UML data model v2 (PDF)](https://github.com/AlbertProfe/compassMap/blob/master/docs/diagrams/data_model/v2/UML.pdf)
- [Postman API documentation](https://documenter.getpostman.com/view/7473960/2sBYHSy1U9)
- [Commits](https://github.com/AlbertProfe/compassMap/commits/master/)
