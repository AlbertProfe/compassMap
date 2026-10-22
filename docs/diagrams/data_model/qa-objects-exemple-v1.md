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
