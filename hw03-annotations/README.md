## Homework 03 — Annotations

This module contains the solution for the third homework assignment
of the OTUS Java Developer course.

### 🎯 Homework Goal
- Get familiar with Java annotations
- Implement a simple test framework using reflection
- Learn how to create and process custom annotations
- Run automated tests programmatically

### 📦 Module Information
**Module name:** `hw03-annotations`  
**Topic:** Java annotations and custom test framework

### 🧩 Implementation Details
- The module implements a custom test framework that discovers and executes methods annotated with `@Test`, `@Before`, and `@After`.
- A `Main` class is provided to run all tests in the module.
- The framework reports the results in the console, including passed and failed tests.
- Tests are self-contained and do not require any external library for execution.
- Logging is implemented using SLF4J with Logback.

### 🧪 How to Run
- All homework tests are executed using the custom test framework.
- To run the tests, simply execute the `Main` class:

```java
ru.anbn.hw03.Main.java
```
- The framework will automatically discover all annotated test methods, execute them, and print the results in the console.

### 🧪 Test Run Example
```java
Starting test run for class: ru.anbn.hw03.SimpleTests
Before test setup
Test will fail now
FAILED: failedTest
After test cleanup
Before test setup
Test OK
After test cleanup
Test run finished
Total tests: 2, Passed: 1, Failed: 1
```

### 🛠 Technologies Used
- Java 21
- Gradle (Gradle Wrapper) 8.5
- Logback (for logging)
- SLF4J API

### ✅ Result
- Custom annotation framework implemented
- Tests execute correctly and logging works
- Dependencies managed cleanly via BOM