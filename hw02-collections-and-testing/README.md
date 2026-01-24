## Homework 02 — Collections and Testing

This module contains the solution for the second homework assignment
of the OTUS Java Developer course.

### 🎯 Homework Goal
- Get familiar with Java Collections Framework
- Work with `TreeMap` and `Deque`
- Implement custom logic for adding, retrieving, and iterating over elements
- Write unit tests to verify functionality

### 📦 Module Information
**Module name:** `hw02-collections-and-testing`  
**Topic:** Java Collections, Map.Entry, and Unit Testing

### 🧩 Implementation Details
- `Customer` class represents a customer with `id`, `name`, and `scores`
- `CustomerService` class manages customers using:
    - a `TreeMap<Customer, String>` for storing customers sorted by score
    - a `Deque<Customer>` (implemented as `ArrayDeque`) for iterating customers in reverse order
- Methods implemented:
    - `add(Customer, String)` — adds a customer and associated data to the TreeMap
    - `getSmallest()` — retrieves the customer with the smallest score from the TreeMap
    - `getNext(Customer)` — retrieves the next customer in ascending order by score from the TreeMap
- Returned `Map.Entry` objects from TreeMap methods contain **copies of keys** to avoid mutating the internal TreeMap
- The `Deque` is used to support reverse-order iteration for specific tests
- Unit tests verify sorting, iteration, and retrieval logic for both collections

### 🧪 How to Run

```bash
Run unit tests
./gradlew :hw02-collections-and-testing:test

View test reports test
./gradlew :hw02-collections-and-testing:test --info
```

### 🛠 Technologies Used
- Java 21
- Gradle (Gradle Wrapper) 8.5
- JUnit 5
- AssertJ

### ✅ Result
- CustomerService implemented correctly
- TreeMap used for sorted storage
- getSmallest() and getNext() methods work as expected
- All unit tests pass