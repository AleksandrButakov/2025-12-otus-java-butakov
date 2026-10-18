## Homework 12 — Web Server and Servlet API

This module contains the solution for the twelfth homework assignment of the OTUS Java Developer course.

### 🎯 Homework Goal

* Understand the core mechanics of embedded web servers using Jetty
* Learn the basics of the Servlet API specification and the servlet lifecycle
* Design and implement secure server-side and user-side HTTP administration interfaces
* Integrate a web-facing frontend seamlessly into an existing Hibernate ORM database layer

### 📦 Module Information

**Module name:** `hw12-webserver`  
**Topic:** Embedded Jetty 12 Web Server, Servlet API, Jakarta EE10, and Freemarker templates

### 🧩 Implementation Details

* **Embedded Jetty 12 Server:** Built as an autonomous web application running on port `8080` directly from the `main`
  method, eliminating the need for standalone servlet containers.
* **Modern EE10 Integration:** Fully migrated to Jetty 12's `ee10` environment, ensuring compliance with the modern
  `jakarta.servlet` specification.
* **Secure Admin Layout:** Implemented form-based authentication via `LoginServlet` and isolated management controls
  inside `AdminServlet` using `HttpSession` verification.
* **Dynamic Freemarker Templates:** Separated presentation logic from Java source code by placing `.ftl` files inside
  the application's resources path, automatically compiling lists of clients into optimized HTML structures.
* **Clean DI Pattern:** Injected the pre-configured `DBServiceClient` Hibernate bean straight into the servlet context,
  establishing clean data routing between HTTP requests and PostgreSQL.

### 🔍 Core Concepts: Jetty 12 and Servlet API

To maintain professional code standards and ensure smooth handling of stateless web requests, this implementation
adheres to the following JVM web fundamentals:

* **Embedded Web Server:** Unlike traditional `.war` deployments into standalone Tomcats, an embedded server starts
  within the JVM lifecycle of a main thread, enabling simple application packaging and instant startups.
* **Jetty 12 EE10 Architecture:** In Jetty 12, the legacy `jetty-servlet` artifact is obsolete. It is replaced by
  `jetty-ee10-servlet`, shifting context handlers into the `org.eclipse.jetty.ee10.servlet.*` package space to support
  modern Jakarta EE specs.
* **Servlet Lifecycle:** Managed natively by the server container:
    1. *Instantiation & Initialization (`init()`):* Executed once per servlet instance to load static resources or
       configurations.
    2. *Request Handling (`service()` -> `doGet()` / `doPost()`):* Triggered multiple times concurrently inside
       dedicated threads from the Jetty thread pool. Must be strictly **stateless and thread-safe**.
    3. *Destruction (`destroy()`):* Triggered during server shutdown to gracefully clear references and close
       connections.
* **State Preservation via Sessions:** Because HTTP is a stateless protocol, user state is preserved by attaching a
  unique `JSESSIONID` cookie to the browser session. Activating `ServletContextHandler(ServletContextHandler.SESSIONS)`
  enables the container to transparently authenticate subsequent administrative requests.

### 🧪 How to Run

* Ensure your **PostgreSQL** database container is running (configured on port `5430` as defined in your environment)
* Open the project in **IntelliJ IDEA** and execute a Gradle sync
* Run the application by clicking the **run icon (▶) next to `DbServiceDemo.java`**
* Open your browser and navigate to `http://localhost:8080/`
* Use the credentials **Login:** `admin` / **Password:** `admin` to access the control panel

### 🛠 Technologies Used

* Java 21
* Gradle (Gradle Wrapper)
* Embedded Jetty 12 (EE10 Architecture)
* Freemarker Template Engine 2.3
* Hibernate ORM & Flyway Migrations
* PostgreSQL

### ✅ Result

* Created a clean, responsive, and secure administrative web console.
* Realized a scalable HTTP layout capable of creating new complex client aggregates (Names, Addresses, and multi-line
  Phone Arrays) and listing them dynamically from the database.
* Adhered strictly to Best Practices by leaving the underlying DB logic untainted, utilizing original BOM dependencies,
  and implementing native multi-threaded servlet architectures without memory leaks.
