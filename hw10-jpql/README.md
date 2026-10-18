## Homework 10 — Database Design, JPQL, and Hibernate ORM

This module contains the solution for the tenth homework assignment of the OTUS Java Developer course.

### 🎯 Homework Goal

* Understand the core concepts of Object-Relational Mapping (ORM) and JPA specifications
* Design a complex database schema with structured table relationships using Hibernate annotations
* Learn to write optimized, high-performance database queries using JPQL and Criteria API
* Eliminate database overhead issues, specifically preventing the notorious N+1 select problem

### 📦 Module Information

**Module name:** `hw10-jpql`  
**Topic:** Hibernate ORM, JPQL, Entity Mapping (`@OneToOne`, `@OneToMany`), and Flyway schema migrations

### 🧩 Implementation Details

* **Complex Aggregate Mapping:** Designed a robust structural mapping for the `Client` entity, which acts as an
  aggregate root containing a single `Address` (`@OneToOne`) and a dynamic collection of `Phone` records (`@OneToMany`).
* **Cascade and Lifecycle Management:** Configured deep lifecycle cascading (`CascadeType.ALL`) and automated orphan
  removal (`orphanRemoval = true`). Deleting or updating a client automatically synchronizes their corresponding address
  and phone records in the database.
* **Deep Cloning via Prototype:** Implemented a secure deep-cloning mechanism (`client.clone()`) to safeguard entity
  instances from unintentional data mutation across business boundaries.
* **Flyway Schema Integration:** Automated foundational DDL generation using Flyway versioned migrations
  (`V1__initial_schema.sql`), ensuring strict separation of database schema state management from the Java codebase.

### 🔍 Core Concepts: JPQL, Mapping, and Fetch Optimization

To guarantee high efficiency and clean transactions during persistence operations, this implementation relies on key
Hibernate architectural strategies:

* **Object-Relational Mapping (ORM):** Bridges the gap between object-oriented Java models and relational SQL databases.
  Meta-information is configured purely using standard `jakarta.persistence` annotations.
* **Bidirectional Relationships:** Configured explicit mapping rules using the `mappedBy` attribute to assign
  relationship ownership to the child entities (`Address` and `Phone`), avoiding the generation of redundant,
  inefficient junction tables.
* **The N+1 Select Problem:** A major performance trap where fetching N parent objects triggers N additional separate
  database queries to load their children.
* **Eager vs Lazy Fetching:** By default, collections use `FetchType.LAZY` to protect system memory. To bypass lazy
  initialization exceptions outside of active sessions, explicit data loading is achieved using targeted `JOIN FETCH`
  queries.
* **Query Alternatives:** Demonstrates versatile data loading mechanics, utilizing both the compile-safe Object-Oriented
  **Criteria API** (`DataTemplateHibernate`) and raw **JPQL** expressions with fetch-joins (`findAllFull()`).

### 🧪 How to Run

* Ensure your **PostgreSQL** database container is running (configured on port `5430` as defined in your environment)
* Open the project in **IntelliJ IDEA** and execute a Gradle sync
* Run the validation demonstration class by executing the **main method in `DbServiceDemo.java`**
* To verify entire system compliance, run the test suite via **`DbServiceDemoTests.java`**

### 🛠 Technologies Used

* Java 21
* Gradle (Gradle Wrapper)
* Hibernate ORM (Core engine)
* Flyway Database Migrations
* PostgreSQL Driver
* Lombok & Slf4j/Logback

### ✅ Result

* Engineered a production-grade relational database schema comprising 3 highly synchronized tables (`client`, `address`,
  `phone`).
* Implemented a fully generic, reusable repository layer (`DataTemplateHibernate`) using the JPA Criteria API.
* Optimized data fetching pipelines to pull intricate relational graphs in a single, high-performance SQL query via
  custom JPQL fetch-joins, successfully matching all rigorous OTUS course benchmarks.
