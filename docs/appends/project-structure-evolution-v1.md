# Project structure

## Wed Sep 23 11:48:48 — Initial flat structure

Initial Spring Boot project layout. All classes live in a single `demo` package: the `DemoApplication` entry point, `HomeController`, the `Customer` and `RoadMap` classes, `CustomerRepository`, and `CustomerService`. Two Thymeleaf templates (`home1.html`, `home2.html`) under `templates`, plus `DemoApplicationTests`.

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

## Tue Sep 29 08:21:00 — Package refactor

No new classes — pure restructuring. Classes were moved out of the flat `demo` package into layered packages: `controller` (`HomeController`), `model` (`Customer`, `RoadMap`), `repository` (`CustomerRepository`), and `service` (`CustomerService`).

```
[Tue Sep 29 08:21:00] albert@albert-VirtualBox:~/MyProjects/SpringProjects/compassMap/compassMap/src (master)
$ tree
.
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── demo
│   │               ├── DemoApplication.java
│   │               ├── controller
│   │               │   └── HomeController.java
│   │               ├── model
│   │               │   ├── Customer.java
│   │               │   └── RoadMap.java
│   │               ├── repository
│   │               │   └── CustomerRepository.java
│   │               └── service
│   │                   └── CustomerService.java
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

18 directories, 10 files
```

## Mon Oct 19 11:52:23 — REST controller and utils package

New `CustomerRestController` added to `controller`. New `utils` package introduced with `BackOffice` and `PopulatorDB` (database seeding helpers).

```
[Mon Oct 19 11:52:23] albert@albert-VirtualBox:~/MyProjects/SpringProjects/compassMap/compassMap/src (master)
$ tree
.
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── demo
│   │               ├── controller
│   │               │   ├── CustomerRestController.java
│   │               │   └── HomeController.java
│   │               ├── DemoApplication.java
│   │               ├── model
│   │               │   ├── Customer.java
│   │               │   └── RoadMap.java
│   │               ├── repository
│   │               │   └── CustomerRepository.java
│   │               ├── service
│   │               │   └── CustomerService.java
│   │               └── utils
│   │                   ├── BackOffice.java
│   │                   └── PopulatorDB.java
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

19 directories, 13 files
```

## Fri Oct 23 08:17:29 — Assessment domain model

Major expansion of the domain model: new entities `Answer`, `AssessmentView`, `CodeProfile`, `CustomerProfile`, `Dimension`, `Question`, and `QuestionAndAnswer` in `model`. New REST controllers `CustomerProfileRestController` and `RoadMapRestController`, new repositories `CustomerProfileRepository` and `RoadMapRepository`, and new services `CustomerProfileService` and `RoadMapService`.

```
[Fri Oct 23 08:17:29] albert@albert-VirtualBox:~/MyProjects/SpringProjects/compassMap/compassMap/src (master)
$ tree
.
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── demo
│   │               ├── controller
│   │               │   ├── CustomerProfileRestController.java
│   │               │   ├── CustomerRestController.java
│   │               │   ├── HomeController.java
│   │               │   └── RoadMapRestController.java
│   │               ├── DemoApplication.java
│   │               ├── model
│   │               │   ├── Answer.java
│   │               │   ├── AssessmentView.java
│   │               │   ├── CodeProfile.java
│   │               │   ├── Customer.java
│   │               │   ├── CustomerProfile.java
│   │               │   ├── Dimension.java
│   │               │   ├── QuestionAndAnswer.java
│   │               │   ├── Question.java
│   │               │   └── RoadMap.java
│   │               ├── repository
│   │               │   ├── CustomerProfileRepository.java
│   │               │   ├── CustomerRepository.java
│   │               │   └── RoadMapRepository.java
│   │               ├── service
│   │               │   ├── CustomerProfileService.java
│   │               │   ├── CustomerService.java
│   │               │   └── RoadMapService.java
│   │               └── utils
│   │                   ├── BackOffice.java
│   │                   └── PopulatorDB.java
│   └── resources
│       ├── application.properties
│       ├── static
│       └── templates
│           ├── home1.html
│           └── home2.html
└── test


19 directories, 26 files
```

## Fri Oct 23 13:43:53 — Question and answer CRUD layer

Completed the question/answer stack: new controllers `QuestionRestController` and `QuestionAndAnswerRestController`, new repositories `QuestionRepository` and `QuestionAndAnswerRepository`, and new services `QuestionService` and `QuestionAndAnswerService`. The `test` tree again shows `DemoApplicationTests`.

```
[Fri Oct 23 13:43:53] albert@albert-VirtualBox:~/MyProjects/SpringProjects/compassMap/compassMap/src (master)
$ tree
.
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── demo
│   │               ├── controller
│   │               │   ├── CustomerProfileRestController.java
│   │               │   ├── CustomerRestController.java
│   │               │   ├── HomeController.java
│   │               │   ├── QuestionAndAnswerRestController.java
│   │               │   ├── QuestionRestController.java
│   │               │   └── RoadMapRestController.java
│   │               ├── DemoApplication.java
│   │               ├── model
│   │               │   ├── Answer.java
│   │               │   ├── AssessmentView.java
│   │               │   ├── CodeProfile.java
│   │               │   ├── Customer.java
│   │               │   ├── CustomerProfile.java
│   │               │   ├── Dimension.java
│   │               │   ├── QuestionAndAnswer.java
│   │               │   ├── Question.java
│   │               │   └── RoadMap.java
│   │               ├── repository
│   │               │   ├── CustomerProfileRepository.java
│   │               │   ├── CustomerRepository.java
│   │               │   ├── QuestionAndAnswerRepository.java
│   │               │   ├── QuestionRepository.java
│   │               │   └── RoadMapRepository.java
│   │               ├── service
│   │               │   ├── CustomerProfileService.java
│   │               │   ├── CustomerService.java
│   │               │   ├── QuestionAndAnswerService.java
│   │               │   ├── QuestionService.java
│   │               │   └── RoadMapService.java
│   │               └── utils
│   │                   ├── BackOffice.java
│   │                   └── PopulatorDB.java
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

19 directories, 32 files

```
