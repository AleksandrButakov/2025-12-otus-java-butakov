# Homework 14 — Spring Data JDBC Web Application

This module contains the solution for the fourteenth homework assignment of the OTUS Java Developer course.

### 🎯 Homework Goal

* Understand the core mechanics of the lightweight Spring Data JDBC framework
* Master the differences between Spring Data JDBC, Spring Data JPA (Hibernate), and MyBatis
* Design and implement a CRUD-capable web application using Spring Boot and Thymeleaf template engine
* Align entity definitions and database interactions strictly with the Aggregate Root design pattern

### 📦 Module Information

**Module name:** `hw14-spring-data-jdbc`  
**Topic:** Spring Boot, Spring Data JDBC, Aggregate Roots, and Thymeleaf templates

### 🧩 Implementation Details

* **Spring Boot Migration:** Replaced the embedded Jetty server with Spring Boot (`spring-boot-starter-web`), leveraging
  the native autoconfigured Tomcat container running on port `8080`.
* **Spring Data JDBC Layer:** Fully migrated the persistence tier from Hibernate ORM to Spring Data JDBC. Eliminated
  persistence contexts, first-level caches, lazy loading, and proxy entities.
* **Aggregate Root Pattern:** Redesigned the domain models (`Client`, `Address`, `Phone`) around the strict Aggregate
  Root concept. `Client` acts as the sole entry point, managing unidirectional dependencies while `Address` and `Phone`
  do not contain back-references.
* **Dynamic Thymeleaf Templates:** Replaced Freemarker templates with a standard, responsive HTML5 implementation driven
  by Thymeleaf, rendering full object aggregates directly out of the database model.
* **Declarative Transactions:** Swapped manual Hibernate session management and custom transaction managers for standard
  Spring transaction management via `@Transactional`.

### 🔍 Core Concepts: Spring Data JDBC and Aggregate Roots

To maintain professional code standards and ensure smooth handling of stateless web requests, this implementation
adheres to the following JVM web and database fundamentals:

* **No Lazy Loading or Proxying:** Unlike Hibernate, Spring Data JDBC loads the entire aggregate root instantly when
  executing a query (`findAll()` or `findById()`). This makes SQL logs predictable and prevents
  `LazyInitializationException`.
* **Explicit List Indexing:** When mapping a `List<Phone>` in Spring Data JDBC, an explicit order tracking column
  (`client_id_key`) is required in the schema to store item positions.
* **Database-Driven ID Generation:** Without a stateful persistence context to fetch sequences beforehand, the database
  uses `DEFAULT nextval('...')` expressions to transparently populate Primary Keys during native `INSERT` actions.
* **Cascading in Base Engine:** By avoiding state tracking mechanisms (dirty checking), cascading deletes are cleanly
  offloaded directly onto the СУБД layer via `ON DELETE CASCADE` foreign key behaviors.

### 🧪 How to Run

* Ensure your **PostgreSQL** database container is running (configured on port `5430` as defined in `application.yml`)
* Open the project in **IntelliJ IDEA** and execute a Gradle sync
* Run the application by executing the **`Hw14Application.java` main method**
* Open your browser and navigate to `http://localhost:8080/admin` or `http://localhost:8080/` to access the client
  management board

### 🛠 Technologies Used

* Java 21
* Gradle (Gradle Wrapper)
* Spring Boot 3.x
* Spring Data JDBC
* Thymeleaf Template Engine
* Flyway Migrations
* PostgreSQL

### ✅ Result

* Created a clean, responsive client management console on Spring Boot.
* Built a lightweight database layer capable of writing and fetching complex client aggregates (Names, nested Addresses,
  and ordered Phone lists) in a thread-safe manner using Spring Data JDBC repositories.
* Adhered strictly to Best Practices by leaving the database logic free of unnecessary entity interceptors, removing
  obsolete state managers, and reducing code boilerplate via Spring Boot autoconfigurations.
