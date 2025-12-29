## OTUS Java Developer — Homework Repository

This repository contains completed homework assignments for the  
**OTUS Java Developer** course.

### 👤 Author
- Full name: Aleksandr Butakov
- Course: Java Developer  
- Group: 2025-12  

### 🧱 Project Structure
This repository is organized as a **multi-module Gradle project**.

- The root project contains shared Gradle configuration
- Each homework assignment is implemented as a separate Gradle module
- Every module contains:
  - its own `build.gradle.kts`
  - a `README.md` describing the task and solution

### 📂 Homework Modules

| Module | Description |
|------|-------------|
| `hw01-gradle` | Gradle basics and project setup |
| `hw02-logging` | Addition to gradle. Logging |
| `hw03-testing` | QA and testing |
| `hw04-algorithms` | Containers and algorithms |

### ▶️ Build Instructions

### Build the entire project
```bash
./gradlew build

Build a specific homework module
./gradlew :hw01-gradle:build

Run tests for a specific module
./gradlew :hw01-gradle:test
```

### 🛠 Requirements
- Java 21 or higher
- Gradle Wrapper (included)