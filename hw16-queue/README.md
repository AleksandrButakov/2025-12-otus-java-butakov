# Homework 16 — Queues and Buffered Processing

This module contains the solution for the sixteenth homework assignment of the OTUS Java Developer course.

### 🎯 Homework Goal

* Master the application of concurrent queues for robust inter-thread communication.
* Understand and implement the Producer-Consumer pattern with bounded memory limits.
* Learn how to design a high-throughput, thread-safe buffering system that minimizes critical section bottlenecks.

### 📦 Module Information

**Module name:** `hw16-queue`  
**Topic:** Java Concurrency, Bounded Buffers, Thread-Safe Queues, and Atomic Processing

### 🧩 Implementation Details

* **Thread-Safe Priority Ordering:** Leveraged an ordered queue mechanics (`PriorityQueue` combined with extrinsic
  synchronization) to guarantee that sensor measurements are processed and flushed strictly in chronological order
  (`measurementTime`), even when produced out-of-order by parallel sensor threads.
* **Atomic Buffer Management:** Implemented an exact batch-draining protocol within the `flush()` method. This
  completely prevents race conditions on `size()` checks and guarantees that data overflows trigger a flush of a
  precisely bounded chunk size (`bufferSize`), satisfying strict multi-threaded verification constraints.
* **Optimal Critical Section Separation:** Separated internal memory management from expensive external I/O execution.
  The slow `writer.writeBufferedData()` operation is invoked strictly outside of the synchronized monitor, allowing
  producer threads to continue queuing new entries without throughput degradation.
* **Graceful Lifecycle Termination:** Designed a reliable drain loop inside `onProcessingEnd()`. It safely flushes
  remaining residual sensor data chunks out of the queue during application shutdown, ensuring zero data loss.

### 🔍 Core Concepts: Producer-Consumer and Batch Processing

To maintain professional concurrency standards and ensure predictable execution flows under heavy load, this
implementation adheres to the following multi-threading fundamentals:

* **Extrinsic Monitor Synchronization:** Utilized a unified monitor-based synchronization wrapper over the underlying
  data-structure to ensure atomic size-evaluation and item extraction (`poll()`), eliminating data splitting between
  racing flusher threads.
* **Lock-Splitting Mindset:** Minimizes the lock-holding time to microseconds by merely shifting object references from
  a shared heap queue to a thread-local stack array slice (`List<SensorData>`), bypassing global locks during heavy
  downstream writes.
* **Data Race & Starvation Defense:** Ensured that empty buffer state checks immediately bypass writer execution,
  protecting the system from redundant flush invocations and preventing thread starvation under massive write
  contentions.

### 🧪 How to Run

* Open the project in **IntelliJ IDEA** and execute a Gradle sync.
* To run the automated concurrency test suite, open a terminal and execute:
  ```bash
  ./gradlew :hw16-queue:test --tests "ru.anbn.hw16.queue.processors.SensorDataProcessorBufferedTest"
  ```
* Locate the application entry point to see it in action with a live sensor grid simulator:
  `ru.anbn.hw16.queue.Main.java`
* Run the application by executing the **`main` method** and observe the console logs.

### 🛠 Technologies Used

* Java 21
* Gradle (Gradle Wrapper)
* AssertJ & Mockito (Testing Frameworks)
* SLF4J / Logback (Logging Framework)

### ✅ Result

* Created a bulletproof, deadlock-free buffered processing engine that successfully passes 100% of high-concurrency race
  condition test cycles.
* Demonstrated deep proficiency in blending sorting algorithms with concurrent synchronization mechanisms to process
  streaming thread-local data in a highly stable, non-blocking JVM environment.
