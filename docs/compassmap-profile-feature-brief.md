# compassMap: "Pick a customer, answer, get the profile" (feature brief)

> Brief for an AI coding assistant. Course IFCD0021-26, Sprint#11, class Fri 27/11/2026 (last day).
> Repo: <https://github.com/AlbertProfe/compassMap>, default branch **`master`**, read at commit `7ade8f5` (16/11/2026).
> Time is short: build the **smallest working end-to-end slice**. Do not refactor anything that is not needed for it.
> All design choices in this brief were **confirmed by Albert on 26/11/2026** (see §0). Treat them as requirements, not suggestions.

---

## 0. Decisions (confirmed by Albert, 26/11/2026)

| #   | Topic              | Decision                                                                                                                                                                       |
| --- | ------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| D1  | Scoring rule       | A dimension is **yes** when its yes answers carry **at least half** of its total weight (`yesWeight >= totalWeight / 2`); a **tie counts as yes**.                             |
| D2  | Profile dimensions | **Only `KNOWS_CODE` × `WANTS_TO_LEARN`** pick the profile. `KNOWS_ML`, `WANTS_TO_LEARN_ML` and `KNOWS_GIT` answers are **saved and scored** but do **not** affect the profile. |
| D3  | Demo data          | Before class: **seed roadmaps + profiles `P01`..`P04` through BackOffice**, and **clean the duplicate questions so the form shows exactly 5** (`q-001`..`q-005`). See §1.5.    |
| D4  | Persistence        | **Every submission is saved as a new `AssessmentView`**. **Never write** into the shared `P0x` `CustomerProfile` rows.                                                         |
| D5  | Git                | Branch **`feature/assessment-profile`** from `master`, **local only: never push to `origin`** (or any remote). Albert merges.                                                  |
| D6  | UI placement       | A **new `/assessment` page** with a nav link in `Layout.jsx`. Existing pages and stubs stay untouched.                                                                         |
| D7  | Frontend state     | **`useState` in the Provider**. `useReducer` is optional, not required.                                                                                                        |
| D8  | This brief         | Lives in Albert's **local notes only**. Do **not** add it to the repo (`docs/`) or link it from the wiki.                                                                      |

---

## 1. Context

### 1.1 Monorepo layout

| Folder              | What it is                                                                 |
| ------------------- | -------------------------------------------------------------------------- |
| `compassMap/`       | Spring Boot backend (Maven wrapper `mvnw`), package `com.example.demo`     |
| `compassMap-React/` | React + Vite frontend                                                      |
| `docs/`             | master docs, diagrams, mockups (`docs/mockup/*.json` are used for seeding) |
| `db/`               | H2 database files (`compassmapdb.mv.db`)                                   |

### 1.2 Stack (from `pom.xml` / `package.json`)

- **Backend:** Java 21, Spring Boot **4.1.1** (`spring-boot-starter-webmvc`, `-data-jpa`, `-thymeleaf`, `-h2console`, `-devtools`), H2 (file-based), Lombok, JavaFaker 1.0.2. Jackson 3 (Spring Boot 4).
- **Frontend:** React `^19.3.1`, axios `^1.21.0`, react-router-dom `^7.18.5` (listed under `devDependencies`, works as is; leave it there), Vite `^8.3.6` with the React Compiler babel preset, ESLint.

### 1.3 How to run

Backend:

```bash
cd compassMap
./mvnw spring-boot:run          # http://localhost:8080 (default port, not set in properties)
```

- `compassMap/src/main/resources/application.properties` points to an **absolute path on Albert's machine**:
  `spring.datasource.url=jdbc:h2:/home/albert/MyProjects/SpringProjects/compassMap/db/compassmapdb` (user `albert`, password `1234`, `ddl-auto=update`). On another machine change it locally to e.g. `jdbc:h2:../db/compassmapdb` but **do not commit that change**.
- `spring.jackson.deserialization.fail-on-null-for-primitives=false` is already set (needed when the request sends id-only stubs).
- H2 console: <http://localhost:8080/h2-console>.

Frontend:

```bash
cd compassMap-React
npm install
npm run dev                     # http://localhost:5173
```

- `vite.config.js` already proxies `'/api/v1'` → `http://localhost:8080`. **Use relative URLs (`/api/v1/...`) in axios.** There is no `@CrossOrigin` / CORS config in the backend, and none is needed in dev thanks to the proxy.

### 1.4 Seeding (important for this feature)

- `DemoApplication.run(...)` does nothing: the `BackOffice` console menu call is commented out.
- `utils/PopulatorDB.java` has the seed methods; `utils/BackOffice.java` is a console menu that calls them:
  - `loadAndSaveRoadMaps()` reads `docs/mockup/roadmaps.json` (ids `RM01`..`RM20`).
  - `loadAndSaveCustomerProfiles()` reads `docs/mockup/profiles.json` (ids **`P01`..`P04`**) and links each profile to the roadmaps whose name contains `(P0x)`. **Load roadmaps first.**
  - `createAndSaveCustomer(n)` (Faker customers), `createAndSaveQuestions(n)` (Faker lorem questions; do not use these for the demo).
- The REST `POST /api/v1/customerProfiles/create` and `/createAll` **overwrite the id with a UUID**, so profiles `P01`..`P04` can only be created with `PopulatorDB.loadAndSaveCustomerProfiles()` (BackOffice menu "3. Customer Profile" → "1").
- The `db/compassmapdb.mv.db` committed in the repo (checked on a copy) contains: 101 customers; 11 questions (`q-001`..`q-005` **each duplicated**, plus one `q-test`), with dimensions `KNOWS_CODE`, `WANTS_TO_LEARN`, `KNOWS_ML`, `WANTS_TO_LEARN_ML`, `KNOWS_GIT`; 5 profiles with UUID ids named Beginner/Architect/Non-Developer/Test profile (**not `P01`..`P04`**); 10 roadmaps; 3 assessments. Albert's live DB at his absolute path may differ.

### 1.5 Demo data preparation (required, D3; do it before class)

Run against the DB that will be used in class (Albert's `application.properties` path). These are data operations, not code changes: **commit nothing under `db/`**.

1. **Seed roadmaps and profiles via BackOffice.** Temporarily uncomment `BackOffice.startBackOffice(populatorDB);` in `DemoApplication.run(...)` (or call it once from the IDE), then: menu **2. Roadmap → 1** (loads `roadmaps.json`), then **3. Customer Profile → 1** (loads `profiles.json`, ids `P01`..`P04`, linked to their roadmaps). Revert `DemoApplication.java` afterwards; **do not commit** that change.
   Check: `GET /api/v1/customerProfiles/byId?id=P01` returns the profile with its `recommendedRoadmaps`.
2. **Clean the questions so exactly 5 remain**, one per name `q-001`..`q-005` (one per dimension, in order 1..5), and remove `q-test`.
   - `QuestionAndAnswer.question` is a `@ManyToOne` with no cascade, so a `Question` still referenced by a `QUESTION_AND_ANSWER` row **cannot be deleted** (FK error). **Keep the copy that is referenced**; delete the unreferenced copy with `DELETE /api/v1/questions/byId?id=…`.
   - Find references in the H2 console: `SELECT Q.NAME, QA.QUESTION_ID, COUNT(*) FROM QUESTION_AND_ANSWER QA JOIN QUESTION Q ON Q.ID = QA.QUESTION_ID GROUP BY Q.NAME, QA.QUESTION_ID;`
   - In the committed DB copy the referenced copies are: `q-001` `7504a93c-a16c-4438-a140-4ae7dd326631`, `q-002` `e0041c35-8a76-49af-8745-b9d0c128ebec`, `q-003` `813ff44d-9e58-455c-908a-b2cac485c0f5` (keep these); `q-004` and `q-005` copies are unreferenced (keep either one of each). Albert's live DB may differ: re-run the query there.
   - `q-test` (`2ce94432-…` in the committed copy) is referenced only by the test assessment `b31ddd98-…` (profile `P-TEST-…`). Delete that assessment first with `DELETE /api/v1/assessments/byId?id=…` (cascades its Q&A rows and answers), then delete `q-test`.
   - Check: `GET /api/v1/questions/all` returns exactly 5 questions.

---

## 2. Goal and user flow

**Goal:** a user picks an existing customer, answers the questionnaire, submits, and sees the computed `CustomerProfile` (with its recommended roadmaps).

Flow:

1. Page loads → frontend fetches customers and questions from the backend.
2. User picks one customer in a `<select>` (shows `firstName lastName`, value = `id`).
3. User answers every question Yes/No (`Answer.value` is a `Boolean`).
4. User clicks **Get my profile** → one `POST` with customer id + answers.
5. Backend computes the profile, saves one `AssessmentView` (customer + Q&A rows + profile), returns it.
6. Frontend shows the profile: `name`, `codeProfile`, `description`, `goal`, and the list of `recommendedRoadmaps` (`name`, `description`, `steps`), plus a **Start again** button.

---

## 3. What already exists (real code)

### 3.1 Backend model (`compassMap/src/main/java/com/example/demo/model/`)

| Class                | Key fields / notes                                                                                                                                                                                                                                                                               |
| -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `Customer`           | `id` (String UUID), `firstName`, `lastName`, `email`, `phone`, `address`                                                                                                                                                                                                                         |
| `Question`           | `id`, `name` (e.g. `q-001`), `text`, `dimension` (`Dimension`), `order` (column `question_order`), `weight` (double)                                                                                                                                                                             |
| `Answer`             | `id`, `value` (`Boolean`, column `answer_value`)                                                                                                                                                                                                                                                 |
| `QuestionAndAnswer`  | `id`, `question` (@ManyToOne), `answer` (@OneToOne cascade ALL), `scoreContribution` (Double), `assessmentView` (@ManyToOne, `@JsonIgnore`, owning side)                                                                                                                                         |
| `AssessmentView`     | `id`, `customer` (@ManyToOne), `timestamp` (Instant), `qtyQuestions` (int), `questionsAndAnswers` (@OneToMany cascade ALL, **`@JsonProperty(WRITE_ONLY)`**: accepted in requests, not returned), `profile` (@ManyToOne `CustomerProfile`, shared, not owned). Helper `addQuestionAndAnswer(...)` |
| `CustomerProfile`    | `id`, `codeProfile` (`CodeProfile`), `name`, `description`, `goal`, `profileAnswers` (`Map<Dimension, Boolean>`, @ElementCollection), `customer` (@ManyToOne), `recommendedRoadmaps` (@ManyToMany EAGER)                                                                                         |
| `RoadMap`            | `id`, `name`, `description`, `stepsItemsSummary` (Map), `steps`, `completed`; `profiles` is `@JsonIgnore`                                                                                                                                                                                        |
| `Dimension` (enum)   | `KNOWS_CODE`, `WANTS_TO_LEARN`, `KNOWS_ML`, `WANTS_TO_LEARN_ML`, `KNOWS_GIT`                                                                                                                                                                                                                     |
| `CodeProfile` (enum) | `BEGINNER`, `ARCHITECT`, `NON_DEVELOPER`                                                                                                                                                                                                                                                         |

### 3.2 Repositories (`repository/`)

`CustomerRepository` (CrudRepository), `QuestionRepository` (JpaRepository), `QuestionAndAnswerRepository` (JpaRepository, `findByAssessmentViewId`), `AssessmentViewRepository` (JpaRepository, `findByCustomerId`), `CustomerProfileRepository` (CrudRepository, `Optional<CustomerProfile> findByCustomerId`), `RoadMapRepository` (CrudRepository).

### 3.3 Services (`service/`)

- `AssessmentViewService.createAssessmentView(AssessmentView)`: sets UUID + timestamp, resolves `customer` and `profile` by id (unknown id → `IllegalArgumentException`), rebuilds the Q&A list (requires an existing `question.id`, loads the real `Question`, gives the `Answer` a UUID, links with `addQuestionAndAnswer`), sets `qtyQuestions`, saves once (cascade). **It does NOT compute `scoreContribution` or the profile.** Also `getTotalScore(id)` (sum of `scoreContribution`).
- `CustomerService.getAllCustomers()`, `QuestionService.getAllQuestions()`, `CustomerProfileService.getCustomerProfileById(id)`, etc.
- Style: field injection with `@Autowired`, `System.out.println("Service ...")` logging, entities returned directly (no DTO classes exist), `null` returned when not found.

### 3.4 Existing endpoints used by this feature

| Method | Path                                                                                          | Returns                                                                                                                                                                    |
| ------ | --------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| GET    | `/api/v1/customers/all`                                                                       | `Customer[]`                                                                                                                                                               |
| GET    | `/api/v1/questions/all`                                                                       | `Question[]` (**unsorted**; sort by `order` on the client)                                                                                                                 |
| GET    | `/api/v1/customerProfiles/byId?id=P01`                                                        | `CustomerProfile`                                                                                                                                                          |
| POST   | `/api/v1/assessments/create`                                                                  | `AssessmentView`. Body: `{customer:{id}, profile:{id}, questionsAndAnswers:[{question:{id}, answer:{value}, scoreContribution}]}`. The client must send the profile itself |
| GET    | `/api/v1/assessments/all`, `/byId`, `/byCustomerId`, `/questionsAndAnswers?id=`, `/score?id=` | already used by `CompassMap.jsx` (`/all`)                                                                                                                                  |

All controllers are in `controller/` (`AssessmentViewRestController`, `CustomerRestController`, `QuestionRestController`, ...).

### 3.5 Profile rule documented in the repo

`docs/mockup/4-1.md` + `docs/mockup/profiles.json` + `docs/compassMap_v1.md` ("two core dimensions: whether they already know how to program and whether they want to learn"):

| Profile id | `codeProfile` | name                                 | KNOWS_CODE | WANTS_TO_LEARN |
| ---------- | ------------- | ------------------------------------ | ---------- | -------------- |
| `P01`      | ARCHITECT     | Improve programming skills           | Yes        | Yes            |
| `P02`      | ARCHITECT     | Use AI to create projects            | Yes        | No             |
| `P03`      | BEGINNER      | Starting from scratch in programming | No         | Yes            |
| `P04`      | NON_DEVELOPER | General introductory course          | No         | No             |

Score convention already used in `PopulatorDB`: `scoreContribution = answer ? question.weight : 0.0`.
(`docs/mockup/4-2.md` (ML) and `16.md` (16 profiles) are other matrices and are **not** used here.)

### 3.6 Frontend (`compassMap-React/src/`)

- `main.jsx` → `App.jsx` (`BrowserRouter`; routes under `Layout`: index `Home`, `compassmap` → `CompassMap`, `openlibrary` → `OpenLibrary`, `*` → `NoPage`).
- `pages/Layout.jsx`: `<nav>` with `Link`s + `<main className="app"><Outlet/></main>`.
- `pages/CompassMap.jsx`: axios `GET /api/v1/assessments/all` in `useEffect`, `isLoading` state, renders `assessment-card` list. This is the style to follow.
- `pages/Questions.jsx` and `pages/Answers.jsx`: **empty stubs** (only an `<h2>`), shown by `Home.jsx`'s view switcher.
- **No Context, no api module, no components folder yet.** Axios is called inline in components.
- The class pattern for Context (Sprint#11, `sculptures` / `cartShoppingContext`) is: (1) api service module, (2) `createContext`, (3) Provider with `useState` + load in `useEffect`, (4) actions passed in the Provider `value`, (5) custom hook (`useXxx`) with a guard when used outside the Provider.

---

## 4. What is missing

1. A backend method that **computes the profile from the answers** and saves the assessment in one call.
2. An endpoint for it.
3. Readable 4xx errors (today, `IllegalArgumentException` → HTTP 500).
4. Frontend: api module, `AssessmentContext`, customer select, questionnaire, profile result, route + nav link.
5. Demo data: profiles `P01`..`P04` (and roadmaps) seeded and questions cleaned to 5 (§1.5, D3).

---

## 5. Backend tasks

### 5.1 Endpoint

`POST /api/v1/assessments/submit` in `AssessmentViewRestController`.

Request body: **same shape as the existing `/create`, without `profile` and without `scoreContribution`** (so no new DTO class is needed):

```json
{
  "customer": { "id": "26256f77-06ea-4c6e-bba3-4754bced74e8" },
  "questionsAndAnswers": [
    { "question": { "id": "7504a93c-a16c-4438-a140-4ae7dd326631" }, "answer": { "value": true } },
    { "question": { "id": "e0041c35-8a76-49af-8745-b9d0c128ebec" }, "answer": { "value": false } }
  ]
}
```

Response `200`: the saved `AssessmentView` (as `/create` returns it; `questionsAndAnswers` is not serialized):

```json
{
  "id": "…uuid…",
  "customer": { "id": "26256f77-…", "firstName": "Lashunda", "lastName": "Jacobs", "email": "…", "phone": "…", "address": "…" },
  "timestamp": "2026-11-27T09:30:00Z",
  "qtyQuestions": 5,
  "profile": {
    "id": "P02", "codeProfile": "ARCHITECT", "name": "Use AI to create projects",
    "description": "…", "goal": "Use AI to create projects", "profileAnswers": {}, "customer": null,
    "recommendedRoadmaps": [ { "id": "RM0x", "name": "… (P02)", "description": "…", "stepsItemsSummary": {}, "steps": 5, "completed": false } ]
  }
}
```

(The score stays available through the existing `GET /api/v1/assessments/score?id=...`.)

### 5.2 Service: `AssessmentViewService.submitAssessment(AssessmentView incoming)`

Reuse `createAssessmentView(...)`; do not duplicate its rebuild loop.

1. **Validate:** `customer.id` present; `questionsAndAnswers` non-empty; each item has `question.id` and a non-null `answer.value`. Otherwise throw `IllegalArgumentException` with a clear message.
2. For each item, load the `Question` (`questionRepository.findById`, unknown → `IllegalArgumentException("Question not found: …")`) and set `scoreContribution = value ? question.getWeight() : 0.0`.
3. **Per-dimension result:** for `KNOWS_CODE` and `WANTS_TO_LEARN`, `yesWeight = Σ weight of "true" answers`, `totalWeight = Σ weight of all answers` in that dimension; the dimension is **true if `yesWeight >= totalWeight / 2`** (weighted majority; a tie counts as yes). **Required (D1).** With one question per dimension (the cleaned demo data) it is simply that answer.
   If either dimension has no answered question → `IllegalArgumentException("No answer for dimension KNOWS_CODE")`.
   Other dimensions (`KNOWS_ML`, `WANTS_TO_LEARN_ML`, `KNOWS_GIT`) are saved and scored, but **do not affect the profile** (D2, table 3.5).
4. **Map to profile id** using table 3.5: (true,true)→`P01`, (true,false)→`P02`, (false,true)→`P03`, (false,false)→`P04`. Put the mapping in one small private method (e.g. `resolveProfileId(boolean knowsCode, boolean wantsToLearn)`).
5. Load it with `customerProfileRepository.findById(id)`. If missing → `IllegalStateException("CustomerProfile P0x not found: seed profiles.json first")`.
6. `incoming.setProfile(profile)` (id stub is enough, `createAssessmentView` resolves it) and return `createAssessmentView(incoming)`.
7. **Never modify the shared `CustomerProfile` rows** (don't write `profileAnswers` or `customer` into `P0x`, don't save the profile). They are a catalog shared by all customers. Each submission is stored only as a new `AssessmentView` (D4).

### 5.3 Errors (minimal)

In the controller `submit` method only, return `ResponseEntity<?>`:

- `IllegalArgumentException` → `ResponseEntity.badRequest().body(Map.of("message", e.getMessage()))` (400)
- `IllegalStateException` → `ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()))` (409)
- success → `ResponseEntity.ok(savedAssessmentView)`

Use an explicit body rather than `ResponseStatusException`: Spring Boot's default error JSON leaves `message` empty unless `server.error.include-message` is set, and the frontend needs the text. An unknown customer id already throws `IllegalArgumentException` in `createAssessmentView`, so it becomes 400. Do not add a global `@ControllerAdvice` (out of scope).

### 5.4 Backend test (small)

Add one test in `compassMap/src/test/java/com/example/demo/DemoApplicationTests.java`, following `assessmentViewWithQuestionAndAnswers`: create a customer, two questions (`KNOWS_CODE`, `WANTS_TO_LEARN`), profile `P02` if absent, submit (true, false), assert `profile.id == "P02"` and `getTotalScore == weight of q1`. Clean up what you created. Optional if time is short.

---

## 6. Frontend tasks (`compassMap-React/src/`)

Follow the existing style: function components, `export default function X`, axios, `isLoading` flags, plain CSS classes in `App.css`. **No new npm packages.**

### 6.1 `src/api/compassMapApi.js` (axios module)

```js
import axios from 'axios';
const API = '/api/v1';
export const getCustomers = () => axios.get(`${API}/customers/all`).then(r => r.data);
export const getQuestions = () => axios.get(`${API}/questions/all`).then(r => r.data);
export const submitAssessment = (customerId, answers) =>
  axios.post(`${API}/assessments/submit`, {
    customer: { id: customerId },
    questionsAndAnswers: Object.entries(answers).map(([questionId, value]) => ({
      question: { id: questionId }, answer: { value },
    })),
  }).then(r => r.data);
```

### 6.2 `src/context/AssessmentContext.jsx`

The 5 elements, same as the class pattern:

- `AssessmentContext = createContext(null)`
- `AssessmentProvider` state (`useState`):
  - `customers` `[]`, `questions` `[]` (sorted by `order`), `isLoading` `true`, `error` `null`
  - `selectedCustomerId` `''`
  - `answers` `{}` map `questionId → true|false` (update immutably: `setAnswers(prev => ({ ...prev, [id]: value }))`)
  - `result` `null` (the returned `AssessmentView`), `isSubmitting` `false`
- `useEffect` on mount: `Promise.all([getCustomers(), getQuestions()])` → set data / `error`, then `isLoading=false`.
- Actions in `value`: `selectCustomer(id)`, `answerQuestion(questionId, value)`, `submit()` (calls `submitAssessment`, sets `result` or `error`; read the backend message from `err.response?.data?.message` with a generic fallback), `reset()` (clears answers, result, error; keeps the customer list).
- Derived: `canSubmit = selectedCustomerId && questions.length > 0 && questions.every(q => q.id in answers) && !isSubmitting`.
- `useAssessment()` custom hook: `useContext` + `throw new Error('useAssessment must be used inside AssessmentProvider')` if null.
- Use `useState` in the Provider (D7). `useReducer` (seen in class Tue 24/11) is optional, not required.

### 6.3 Components (`src/components/`)

- `CustomerSelect.jsx`: `<label>` + `<select>` with a placeholder `<option value="">-- choose a customer --</option>` and one option per customer (`key=id`, text `firstName lastName`). `onChange` → `selectCustomer`.
- `Questionnaire.jsx`: `<form>` listing questions in `order`; each shows `text` and two radio inputs Yes/No (`name=question.id`, `checked` from `answers`); submit button **Get my profile** disabled unless `canSubmit`; shows "Sending…" while `isSubmitting`. `onSubmit` → `e.preventDefault(); submit()`.
- `ProfileResult.jsx`: when `result` exists, shows customer name, `profile.name`, `profile.codeProfile`, `profile.description`, `profile.goal`, a list of `profile.recommendedRoadmaps` (`name`, `description`, `steps` steps), and **Start again** → `reset()`.

### 6.4 Page, route and nav

- `src/pages/Assessment.jsx`: wraps content in `<AssessmentProvider>`; renders loading ("Loading customers and questions..."), error message, then `CustomerSelect`, and either `Questionnaire` or `ProfileResult` (when `result` is set).
- `App.jsx`: add `<Route path="assessment" element={<Assessment />} />`.
- `Layout.jsx`: add `<li><Link to="/assessment">Assessment</Link></li>`.
- Leave `Home.jsx`, `Questions.jsx`, `Answers.jsx`, `CompassMap.jsx`, `OpenLibrary.jsx` untouched (D6: the feature lives only on the new `/assessment` page).

### 6.5 States to handle

Loading data, load error (backend down), empty customer list, empty question list ("No questions in the database"), submitting, submit error (400/409 message shown), result.

---

## 7. Acceptance criteria

1. `POST /api/v1/assessments/submit` with a valid body returns 200 and an `AssessmentView` whose `profile.id` follows table 3.5 and the 5.2 rule.
2. A new row is in `ASSESSMENT_VIEW` with `CUSTOMER_PROFILE_ID` set, and its `QUESTION_AND_ANSWER` rows have `score_contribution = weight` for "yes" and `0.0` for "no"; `GET /api/v1/assessments/score?id=…` returns their sum.
3. Unknown customer/question id, empty list, or missing value → 400 with `{"message": "…"}`. Missing `P0x` profile → 409.
4. `/assessment` page: select is filled from the backend; submit is disabled until a customer is picked and every question answered; the profile and its roadmaps are shown after submit; Start again works.
5. `npm run lint` passes; `./mvnw test` still passes (or at least compiles); nothing else changed.

## 8. Manual test checklist

- [ ] Seed (§1.5): BackOffice → 2. Roadmap → 1, then 3. Customer Profile → 1. `GET /api/v1/customerProfiles/byId?id=P01` returns a profile with roadmaps.
- [ ] Questions cleaned (§1.5): `GET /api/v1/questions/all` returns exactly 5 (`q-001`..`q-005`); the form shows 5 questions.
- [ ] After a submit, the `P0x` row is unchanged (no `customer`, empty `profileAnswers`) and a new `ASSESSMENT_VIEW` row exists.
- [ ] `GET /api/v1/customers/all` and `/api/v1/questions/all` return data.
- [ ] curl/Postman: KNOWS_CODE yes + WANTS_TO_LEARN yes → `P01`; yes/no → `P02`; no/yes → `P03`; no/no → `P04`.
- [ ] Bad customer id → 400; empty `questionsAndAnswers` → 400.
- [ ] UI: pick customer, answer all, submit → profile + roadmaps; network tab shows one POST to `/api/v1/assessments/submit` via the Vite proxy.
- [ ] UI with backend stopped → error message, no crash.
- [ ] `/compassmap` page lists the new assessment with its profile name.

## 9. Constraints

- Minimal change set; follow the existing code style (field `@Autowired`, `System.out.println` logs, entities as JSON, axios with relative `/api/v1` URLs).
- No new libraries (backend or frontend) unless strictly needed.
- Work on the local branch **`feature/assessment-profile`**, created from `master` (`git switch master && git switch -c feature/assessment-profile`), with small commits using the repo's prefixes (`feat:`, `feat-react:`). Suggested commits: (1) service + endpoint, (2) test, (3) api + context, (4) components + route.
- **NO PUSH. LOCAL ONLY.** Do **not** run `git push` to `origin` or any other remote, do not open PRs or issues, do not create remote branches. The branch stays on Albert's machine (D5).
- **Never commit to or merge into `master`; Albert alone merges.** Do not commit the local change to `application.properties`, the temporary `DemoApplication.java` BackOffice change, or the `db/` files.
- Do **not** add this brief to the repo (`docs/` or elsewhere) and do not link it from the wiki (D8).

## 10. Open questions

1. **Old sample assessments in Albert's live DB.** If his DB has several sample assessments pointing to *different* copies of the same question name (so neither copy can be deleted), either delete those sample assessments (`DELETE /api/v1/assessments/byId`) or keep the referenced copy and skip the clean-up for that name. Default: delete only the sample assessments that block the clean-up (in the committed copy that is just the `q-test` test assessment). Confirm with Albert before deleting anything else.
