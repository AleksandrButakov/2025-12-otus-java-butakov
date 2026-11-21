# Homework 17 — gRPC Client-Server Application

This module contains the solution for the seventeenth homework assignment of the OTUS Java Developer course.

### 🎯 Homework Goal

* Master the principles of designing and developing network client-server applications using **gRPC** and **Protocol
  Buffers**.
* Understand and implement **Server Streaming** RPC communication patterns over HTTP/2.
* Learn how to synchronize asynchronous network I/O threads with main application processing loops safely without data
  races.

### 📦 Module Information

**Module name:** `hw17-multiprocess`  
**Topic:** Multiprocess Applications, gRPC, Protobuf, Server Streaming, and Thread Synchronization

### 🧩 Implementation Details

* **Strict Contract-First Design (`NumbersService.proto`):** Defined a strongly typed Protobuf v3 schema specifying
  `NumberRequest`, `NumberResponse`, and the server-streaming RPC method `generateNumbers` to ensure efficient binary
  serialization.
* **Server-Side Stream Generator (`NumbersServiceImpl` & `NumbersServer`):** Implemented a gRPC service bound to port
  `8190`. Upon receiving a client range request (`firstValue` to `lastValue`), it asynchronously pushes sequential
  integer increments every 2 seconds via `StreamObserver.onNext()`.
* **Thread-Safe Value Exchange (`ClientStreamObserver`):** Built a dedicated asynchronous stream observer running on the
  gRPC executor thread pool. It captures incoming server numbers and provides a synchronized `getLastValueAndReset()`
  method, guaranteeing that each server-emitted value is consumed strictly once by the client loop.
* **Decoupled Client Loop (`NumbersClient`):** Designed a non-blocking client that initiates the stream asynchronously
  (`newStub`) while executing a 51-step main processing loop at 1-second intervals, dynamically applying the formula
  `currentValue = currentValue + lastServerValue + 1`.

### 🔍 Core Concepts: gRPC Streaming and Concurrency

To maintain clean multi-threaded architecture and ensure predictable execution across separate JVM processes, this
implementation adheres to the following fundamentals:

* **Asynchronous Non-Blocking Stubs:** Replaced blocking unary calls with asynchronous `NumbersServiceStub` paired with
  `StreamObserver`, allowing the client's main thread to operate independently of the server's 2-second emission
  cadence.
* **Atomic Read-and-Reset Semantics:** Encapsulated the shared `lastValue` state inside `ClientStreamObserver` using
  intrinsic monitor synchronization (`synchronized`), preventing race conditions between the background
  `grpc-default-executor` thread and the `main` thread.
* **Clean Lifecycle Management:** Properly configured `ManagedChannel` shutdown sequences
  (`shutdown().awaitTermination()`) on the client and graceful `awaitTermination()` on the server to prevent resource
  leaks.

### 🧪 How to Run

* Open the project in **IntelliJ IDEA** and execute a Gradle sync.
* To generate Protobuf classes and build the module, open a terminal and execute:
  ```bash
  ./gradlew :hw17-multiprocess:build
  ```
* Start the **Server** first by locating and running the `main` method in:
  `ru.anbu.hw17.multiprocess.server.NumbersServer`
* Without stopping the server, start the **Client** in a separate process by running the `main` method in:
  `ru.anbu.hw17.multiprocess.client.NumbersClient`
* Observe the synchronized console logs across both processes as server values dynamically boost the client's counter.

### 🛠 Technologies Used

* Java 21
* Gradle (Gradle Wrapper)
* gRPC (`grpc-netty`, `grpc-protobuf`, `grpc-stub`)
* Protocol Buffers (`protobuf-java`, `com.google.protobuf` Gradle plugin)
* SLF4J / Logback (Logging Framework)

### ✅ Result

* Built a fully functional, multi-process gRPC client-server application utilizing server-side streaming.
* Verified thread-safe, single-consumption state synchronization between asynchronous gRPC network callbacks and a
  high-frequency main processing loop.