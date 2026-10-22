# compassMap model v1

Here are 3 example objects of each class (true/false style):

### Dimension (enum values)

1. `KNOWS_CODE`
2. `WANTS_TO_LEARN`
3. `KNOWS_GIT`

### Question

```text
Question {
  id: "q-001"
  text: "Have you written code in any language before?"
  dimension: KNOWS_CODE
  order: 1
  weight: 1.0
}

Question {
  id: "q-002"
  text: "Do you want to learn how to program?"
  dimension: WANTS_TO_LEARN
  order: 2
  weight: 1.0
}

Question {
  id: "q-003"
  text: "Have you used Git or any version control system?"
  dimension: KNOWS_GIT
  order: 3
  weight: 0.8
}
```

### Answer

```text
Answer {
  id: "a-001"
  value: true
}

Answer {
  id: "a-002"
  value: true
}

Answer {
  id: "a-003"
  value: false
}
```

### QuestionAndAnswer

```text
QuestionAndAnswer {
  question: Question { id: "q-001", text: "Have you written code in any language before?", ... }
  answer: Answer { id: "a-001", value: true }
  scoreContribution: 1.0
}

QuestionAndAnswer {
  question: Question { id: "q-002", text: "Do you want to learn how to program?", ... }
  answer: Answer { id: "a-002", value: true }
  scoreContribution: 1.0
}

QuestionAndAnswer {
  question: Question { id: "q-003", text: "Have you used Git or any version control system?", ... }
  answer: Answer { id: "a-003", value: false }
  scoreContribution: 0.0
}
```



### Customer

```text
Customer {
  id: "c-001"
  firstName: "Ana"
  lastName: "García"
  email: "ana.garcia@email.com"
  phone: "+34 600 111 222"
  address: "Calle Mayor 12, Madrid"
}

Customer {
  id: "c-002"
  firstName: "Luis"
  lastName: "Martínez"
  email: "luis.martinez@email.com"
  phone: "+34 600 333 444"
  address: "Avenida Diagonal 45, Barcelona"
}

Customer {
  id: "c-003"
  firstName: "Marta"
  lastName: "López"
  email: "marta.lopez@email.com"
  phone: "+34 600 555 666"
  address: "Plaza del Ayuntamiento 8, Valencia"
}
```

### Answer

```text
Answer {
  id: "a-001"
  value: true
}

Answer {
  id: "a-002"
  value: true
}

Answer {
  id: "a-003"
  value: false
}
```

### QuestionAndAnswer

```text
QuestionAndAnswer {
  question: Question { id: "q-001", text: "Have you written code in any language before?", dimension: KNOWS_CODE, order: 1, weight: 1.0 }
  answer: Answer { id: "a-001", value: true }
  scoreContribution: 1.0
}

QuestionAndAnswer {
  question: Question { id: "q-002", text: "Do you want to learn how to program?", dimension: WANTS_TO_LEARN, order: 2, weight: 1.0 }
  answer: Answer { id: "a-002", value: true }
  scoreContribution: 1.0
}

QuestionAndAnswer {
  question: Question { id: "q-003", text: "Have you used Git or any version control system?", dimension: KNOWS_CODE, order: 3, weight: 0.8 }
  answer: Answer { id: "a-003", value: false }
  scoreContribution: 0.0
}
```

### CustomerProfile

```text
CustomerProfile {
  id: "cp-001"
  codeProfile: BEGINNER
  name: "Beginner"
  description: "Wants to start learning programming from scratch"
  goal: "Learn basic programming concepts and build a first project"
  answers: { KNOWS_CODE: false, WANTS_TO_LEARN: true }
  recommendedRoadmap: RoadMap { id: "rm-001", name: "Beginner Path", ... }
}

CustomerProfile {
  id: "cp-002"
  codeProfile: ARCHITECT
  name: "Architect"
  description: "Already knows how to code and wants to deepen skills"
  goal: "Design better systems and learn advanced architecture"
  answers: { KNOWS_CODE: true, WANTS_TO_LEARN: true }
  recommendedRoadmap: RoadMap { id: "rm-002", name: "Architect Path", ... }
}

CustomerProfile {
  id: "cp-003"
  codeProfile: NON_DEVELOPER
  name: "Non-Developer"
  description: "Does not know how to code and is not interested in learning"
  goal: "Understand high-level concepts without writing code"
  answers: { KNOWS_CODE: false, WANTS_TO_LEARN: false }
  recommendedRoadmap: RoadMap { id: "rm-003", name: "Non-Developer Path", ... }
}
```

### RoadMap

```text
RoadMap {
  id: "rm-001"
  name: "Beginner Path"
  description: "Start from zero with fundamentals of programming"
  steps: 8
  completed: false
}

RoadMap {
  id: "rm-002"
  name: "Architect Path"
  description: "Advanced design patterns and system architecture"
  steps: 12
  completed: false
}

RoadMap {
  id: "rm-003"
  name: "Non-Developer Path"
  description: "High-level overview of technology without coding"
  steps: 5
  completed: true
}
```

### AssessmentView

```text
AssessmentView {
  id: "av-001"
  customer: Customer { id: "c-001", firstName: "Ana", lastName: "García", ... }
  timestamp: 2026-10-22T10:30:00Z
  qtyQuestions: 3
  questionsAndAnswers: [
    QuestionAndAnswer { question: q-001, answer: true, scoreContribution: 1.0 },
    QuestionAndAnswer { question: q-002, answer: true, scoreContribution: 1.0 },
    QuestionAndAnswer { question: q-003, answer: false, scoreContribution: 0.0 }
  ]
  profile: CustomerProfile { id: "cp-001", codeProfile: BEGINNER, ... }
}

AssessmentView {
  id: "av-002"
  customer: Customer { id: "c-002", firstName: "Luis", lastName: "Martínez", ... }
  timestamp: 2026-10-22T11:15:00Z
  qtyQuestions: 3
  questionsAndAnswers: [
    QuestionAndAnswer { question: q-001, answer: true, scoreContribution: 1.0 },
    QuestionAndAnswer { question: q-002, answer: true, scoreContribution: 1.0 },
    QuestionAndAnswer { question: q-003, answer: true, scoreContribution: 0.8 }
  ]
  profile: CustomerProfile { id: "cp-002", codeProfile: ARCHITECT, ... }
}

AssessmentView {
  id: "av-003"
  customer: Customer { id: "c-003", firstName: "Marta", lastName: "López", ... }
  timestamp: 2026-10-22T12:00:00Z
  qtyQuestions: 3
  questionsAndAnswers: [
    QuestionAndAnswer { question: q-001, answer: false, scoreContribution: 0.0 },
    QuestionAndAnswer { question: q-002, answer: false, scoreContribution: 0.0 },
    QuestionAndAnswer { question: q-003, answer: false, scoreContribution: 0.0 }
  ]
  profile: CustomerProfile { id: "cp-003", codeProfile: NON_DEVELOPER, ... }
}
```

### CodeProfile (enum)

```text
BEGINNER
ARCHITECT
NON_DEVELOPER
```
