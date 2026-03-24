## Homework 07 — Message Processor

This module contains the solution for the homework assignment  
of the OTUS Java Developer course.

### 🎯 Homework Goal

* Apply common design patterns in Java
* Process messages through a configurable pipeline
* Handle errors gracefully
* Keep message history without modifying original data (Memento pattern)

### 📦 Module Information

**Module name:** `hw07-design-patterns`  
**Topic:** Message processing with Chain of Responsibility, Observer, and Memento

### 🧩 Implementation Details

* **Message object:**
    - Has fields `field1`…`field13`, with `field13` being immutable
    - Built using a Builder for easy creation

* **Processors:**
    - Combine or transform message fields
    - Swap values, uppercase text, or throw exceptions on certain conditions
    - Can log messages while processing

* **Processor chain:**
    - Messages pass through processors in order (**Chain of Responsibility**)
    - Errors are handled flexibly via a passed-in function (**Strategy**)
    - Listeners are notified after processing (**Observer**)

* **Listeners:**
    - Print messages to console
    - Keep safe snapshots of messages for history (**Memento**)

* **Deep copy** ensures history is not affected by later changes to messages.

### 🧪 How to Run

* Open the project in **IntelliJ IDEA**
* Run `HomeWork.java` to see the processor in action
* Automated tests verify processing and history handling

### 🛠 Technologies Used

* Java 21
* Gradle
* SLF4J + Logback
* JUnit 5

### ✅ Result

* Messages are processed step by step through the chain
* Errors are logged without breaking the pipeline
* Message history is stored safely and can be queried anytime  