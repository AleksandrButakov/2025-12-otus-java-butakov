# Homework 15 — Thread Pools and Executors

This module contains the solution for the fifteenth homework assignment of the OTUS Java Developer course.

### 🎯 Homework Goal

* Understand the core mechanics of thread pools and concurrent task execution in Java
* Master the use cases for standard `Executors` and the `Fork/Join` framework in the JDK
* Implement precise thread coordination and strict execution ordering using low-level synchronization primitives
* Prevent common concurrency pitfalls such as race conditions, deadlocks, and spurious wake-ups

### 📦 Module Information

**Module name:** `hw15-executors`  
**Topic:** Java Concurrency, Monitors, Thread Synchronization, and Cooperative Cancellation

### 🧩 Implementation Details

* **Strict Thread Sequencing:** Implemented a deterministic ping-pong style coordination mechanism ensuring that two
  independent threads alternate execution steps to print a palindromic number sequence (from 1 to 10 and back to 1).
* **Deterministic Initialization:** Configured the shared state controller to guarantee that Thread 1 always initiates
  the sequence execution, forcing Thread 2 into a blocking wait state upon application startup.
* **Isolated Thread State:** Allocated individual local counters (`currentNumber`, `movingUp`) inside each thread's
  stack context. This architectural choice completely eliminates data race conditions and the need for complex
  concurrent collections.
* **Spurious Wake-up Protection:** Wrapped condition-based waiting mechanisms within defensive `while` loops, ensuring
  threads verify state validation criteria before proceeding post-awakening.
* **Optimal Lock Holding:** Limited the critical section to the absolute minimum code required for state modification.
  Threads execute non-blocking time delays (`TimeUnit.MILLISECONDS.sleep`) strictly outside the synchronized block to
  prevent artificial throughput degradation.

### 🔍 Core Concepts: Thread Coordination and Primitives

To maintain professional concurrency standards and ensure predictable execution flows, this implementation adheres to
the following multi-threading fundamentals:

* **Cooperative Cancellation Model:** Thread termination logic respects the standard Java interruption policy.
  Interrupted exceptions are caught gracefully, and the interrupt status is restored via
  `Thread.currentThread().interrupt()`.
* **Monitor-Driven Inter-Thread Communication:** Leveraged intrinsic locks and `Object`-level signaling (`wait()` /
  `notifyAll()`) to hand over execution rights directly from one thread context to another.
* **Missed Signal Prevention:** Coordinated the relationship between thread sleeping intervals and lock acquisition
  states to prevent lost wake-up scenarios where a notify signal is dispatched into a void while the companion thread is
  not yet parked.
* **Stack vs Heap Allocation:** Thread-local primitives reside securely in private execution stacks, decoupling
  data-processing algorithms from shared memory management in the JVM Heap.

### 🧪 How to Run

* Open the project in **IntelliJ IDEA** and execute a Gradle sync
* Locate the application entry point: `ru.anbn.hw15.executors.NumberSequence.java`
* Run the application by executing the **`main` method**
* Observe the alternating, thread-safe sequence output printed directly to the IDE console

### 🛠 Technologies Used

* Java 21
* Gradle (Gradle Wrapper)
* SLF4J / Logback (Logging Framework)

### ✅ Result

* Created a rock-solid, deadlock-free thread coordination engine that strictly satisfies the sequential requirements of
  the assignment.
* Demonstrated a deep understanding of low-level synchronization primitives, memory barriers, and state encapsulation
  best practices in a high-concurrency JVM environment.
