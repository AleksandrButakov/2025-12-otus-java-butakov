## Homework 01 — Gradle Basics

This module contains the solution for the first homework assignment
of the OTUS Java Developer course.

### 🎯 Homework Goal
- Get familiar with Gradle basics
- Create a multi-module Gradle project
- Add an external dependency
- Build and run a fat (thick) JAR

### 📦 Module Information
**Module name:** `hw01-gradle`  
**Topic:** Gradle project setup and dependency management

### 🧩 Implementation Details
- The module is implemented as a separate Gradle subproject
- The Google Guava library is added as an external dependency
- A simple `HelloOtus` class demonstrates usage of a Guava method
- A fat JAR is built that includes all required dependencies

### 🧪 How to Run

### Build the module
```bash
Build thin JAR
./gradlew :hw01-gradle:jar

Build fat JAR
./gradlew :hw01-gradle:shadowJar

Run fat JAR
java -jar hw01-gradle/build/libs/gradleHelloWorld-0.1.jar
```

### 🛠 Technologies Used
- Java 21
- Gradle (Gradle Wrapper) 8.5
- Google Guava

### ✅ Result
- Dependency added successfully
- Guava method usage implemented
- Fat JAR built and verified locally