## Inheritance in Java

![](/home/albert/Desktop/interface/animal.png)

> **Inheritance** lets one class extend another, inheriting its accessible fields and methods. In the Animal example, `Duck`, `Fish`, and `Zebra` are kinds of `Animal`, so shared data or behavior—such as `age`, `gender`, or `mate()`—can be defined once in `Animal` and reused by those subclasses.[[docs.oracle](https://docs.oracle.com/javase/tutorial/java/IandI/index.html)]

```
class Animal {
    int age;
    String gender;

    void mate() { /* shared behavior */ }
}

class Duck extends Animal {
    void quack() { /* duck-specific behavior */ }
}
```

Here, `Animal` is the **superclass** (also called the parent class), and `Duck` is a **subclass** (child class). In Java, a class can extend only one superclass; `extends` expresses this inheritance relationship.

## Where Person Fits

In the earlier diagram, `Person` was shown as an **interface**. If you want `Person` to hold shared state like a name, it generally makes more sense as a regular class: `Student`, `Teacher`, and `Author` can extend `Person`, while `Animal` can remain a superclass for animals.

 A Java class cannot extend both `Animal` and `Person`, so a `Student` that must be both a person and an animal would need a different design.

```
class Person {
    String name;
}

class Student extends Person {
    void study() { /* student behavior */ }
}
```

## Why Use Interfaces?

> An **interface** defines a capability or <mark>contract</mark>: it says what methods a class must provide, without requiring the class to inherit implementation from that interface. 

This is useful when different kinds of classes share a capability but do not belong to the same “is-a” family; a class can implement multiple interfaces.[[docs.oracle](https://docs.oracle.com/javase/tutorial/java/IandI/index.html)]

For example, `Teacher` and `Student` might both implement a `Staff` interface if your model says both can perform some shared `work()` behavior. But if `work()` doesn’t meaningfully apply to students, choose a more appropriate capability—such as `HasAssignments` or `CanLearn`—instead. Interfaces help code work with a capability without depending on a specific class.[[cs61bl](https://cs61bl.org/labs/lab07/)]

```
interface Staff {
    void work();
}

class Teacher extends Person implements Staff {
    public void work() { /* teaching work */ }
}

class Student extends Person {
    void study() { /* student behavior */ }
}
```

**In short:** use a superclass for shared state and implementation in a genuine “is-a” hierarchy; use an interface for a capability or contract that multiple classes can adopt.
