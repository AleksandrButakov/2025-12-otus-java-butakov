## Homework 09 — JDBC ORM

This module contains the solution for the ninth homework assignment
of the OTUS Java Developer course.

### 🎯 Homework Goal

* Understand how ORM works under the hood
* Learn to use Java Reflection for mapping entities
* Build SQL dynamically from Java classes
* Implement basic CRUD operations using JDBC

### 📦 Module Information

**Module name:** `hw09-jdbc`  
**Topic:** JDBC, Reflection-based ORM, SQL generation

---

### 🐘 Database (PostgreSQL via Docker)

Run PostgreSQL in a Docker container:

```bash
docker run --rm --name pg-docker \
-e POSTGRES_PASSWORD=pwd \
-e POSTGRES_USER=usr \
-e POSTGRES_DB=demoDB \
-p 5430:5432 \
postgres:13
```

Connect to the running container:

```bash
docker exec -it pg-docker psql -U usr -d demoDB
```

### 🧱 Migrations (Flyway)

Database schema is managed by Flyway.\
Migrations are executed automatically on application startup from:

```bash
resources/db/migration
```

They create tables such as:

* client
* manager
* test

### 🧩 Implementation Details

The project implements a simple ORM consisting of:

EntityClassMetaData extracts entity metadata via reflection
detects fields, constructors, and\
@Id EntitySQLMetaData generates SQL queries dynamically:\
SELECT BY ID\
SELECT ALL\
INSERT\
UPDATE\
DataTemplateJdbc executes SQL via JDBC maps ResultSet to Java objects\
DbExecutor low-level SQL execution layer

### 🧠 Key Idea

Java classes are treated as the source of truth.

The ORM layer:

analyzes entities via reflection
builds SQL automatically
maps database rows to Java objects

### 🧪 How to Run

* Start PostgreSQL container (see above)
* Run Flyway migrations automatically on startup
* Run application from HomeWork.main()

### 🛠 Technologies Used

* Java 21
* JDBC
* PostgreSQL
* Flyway
* Gradle
* Reflection API

### ✅ Result

* CRUD operations work via generated SQL
* Entities are mapped automatically from DB rows
* Reflection-based ORM replaces manual SQL mapping
* Database schema is versioned using Flyway