## Homework 13 — Custom IoC Container

This module contains the solution for the thirteenth homework assignment
of the OTUS Java Developer course.

### 🎯 Homework Goal

* Understand the fundamental principles of Inversion of Control (IoC)
* Learn how Dependency Injection (DI) works internally
* Understand how the Spring container manages components
* Implement a simplified version of an IoC container manually

### 📦 Module Information

**Module name:** `hw13-ioc-container`
**Topic:** IoC container, Dependency Injection, reflection, annotations

### 🧩 Implementation Details

* Implemented processing of configuration classes annotated from the `appcontainer` package
* Added automatic creation and registration of application components
* Implemented dependency resolution between components
* Added support for retrieving components:

    * by class
    * by component name
* The container independently manages:

    * object creation
    * dependency injection
    * component lifecycle

### 🧠 Key Concepts

* **IoC (Inversion of Control)** — object creation and dependency management are delegated to the container
* **DI (Dependency Injection)** — dependencies are injected automatically through constructors or methods
* **Application Context** — a container storing and managing application components (beans)
* **Reflection & Annotations** — used to scan configuration classes and create objects dynamically

### 🧪 How to Run

* Open the project in **IntelliJ IDEA**
* Run the application or automated tests
* The application context will automatically create and link all components

### 🛠 Technologies Used

* Java 21
* Gradle (Gradle Wrapper)
* Reflection API
* Custom annotations
* JUnit 5

### ✅ Result

* A working custom IoC container was implemented
* Dependencies are resolved automatically between components
* Components can be requested from the context by type or name
* The project demonstrates the core ideas behind the Spring Framework and Dependency Injection