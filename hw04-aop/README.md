## Homework 04 — Automatic Logging (AOP)

This module contains the solution for the fourth homework assignment
of the OTUS Java Developer course.

### 🎯 Homework Goal
- Understand the basics of AOP (Aspect-Oriented Programming)
- Learn how to implement automatic method logging without changing business logic
- Explore technical tools: Java Proxy, Instrumentation, ASM

### 📦 Module Information
**Module name:** `hw04-aop`  
**Topic:** Automatic logging using Proxy and ASM

### 🧩 Implementation Details

#### 1️⃣ Proxy Approach
- Uses Java dynamic proxy (`java.lang.reflect.Proxy`) to intercept method calls
- Logs methods annotated with `@Log` automatically
- Runtime logging includes method name and parameters
- Key classes:
    - `ProxyFactory` — creates a proxy for the target class
    - `LogInvocationHandler` — handles method calls and inserts logging
    - `DemoProxy` — demonstrates usage

#### 2️⃣ ASM / Instrumentation Approach
- Uses Java Instrumentation API and ASM library to modify bytecode at class load time
- Automatically inserts logging into methods annotated with `@Log`
- Logging happens at runtime without modifying source code
- Double insertion occurs due to `EXPAND_FRAMES + COMPUTE_FRAMES` settings in ASM (explained in comments)
- Key classes:
    - `LogAgent` — Java agent entry point
    - `LogClassTransformer` — bytecode transformer
    - `LogClassVisitor` / `LogMethodVisitor` — ASM visitors that insert logging instructions
    - `DemoAsm` — demonstrates usage

#### Project Structure
```bash
hw04-aop/
├─── README.md
├─── src/main/java/ru/anbn/logging/common/
│  ├──── Log.java
│  ├──── MethodSignature.java
│  └──── TestLoggingInterface.java
├─── src/main/java/ru/anbn/logging/target/
│  └──── TestLogging.java
├──── src/main/java/ru/anbn/logging/proxy/
│  ├──── LogInvocationHandler.java
│  ├──── ProxyFactory.java
│  └──── DemoProxy.java
├─── src/main/java/ru/anbn/logging/asm/
│  ├──── LogAgent.java
│  ├──── LogClassTransformer.java
│  ├──── LogClassVisitor.java
│  ├──── LogMethodVisitor.java
│  └──── DemoAsm.java
└─── build/...
````

### 🧪 How to Run DemoProxy
- To run the DemoProxy, simply execute the `Main` class:
```java
ru/anbn/logging/demo/DemoProxy.java
```

### 🧪 How to Run DemoAsm
- ASM / Instrumentation approach
```bash
1. Build the agent JAR:
./gradlew :hw04-aop:clean :hw04-aop:jar 

2. Add VM Options:
-javaagent:/home/anbn/dev/learning/java/2025-12-otus-java-butakov/hw04-aop/build/libs/hw04-aop-1.0.jar 
````

- To run the DemoAsm, simply execute the `Main` class:
```java
ru/anbn/logging/demo/DemoAsm.java
```

### 🧪 Example with Proxy
```console
**14:05:45: Executing ':hw04-aop:ru.anbn.logging.demo.DemoProxy.main()'…

> Task :hw04-aop:processResources NO-SOURCE
> Task :spotlessInternalRegisterDependencies UP-TO-DATE
> Task :hw04-aop:spotlessJava
> Task :hw04-aop:spotlessJavaApply
> Task :hw04-aop:spotlessApply
> Task :hw04-aop:compileJava
> Task :hw04-aop:classes

> Task :hw04-aop:ru.anbn.logging.demo.DemoProxy.main()
14:05:49.005 [main] INFO ru.anbn.logging.proxy.LogInvocationHandler -- executed method: calculation, params: [6]
14:05:49.010 [main] INFO ru.anbn.logging.proxy.LogInvocationHandler -- executed method: calculation, params: [3, 4]

BUILD SUCCESSFUL in 3s
5 actionable tasks: 4 executed, 1 up-to-date
14:05:49: Execution finished ':hw04-aop:ru.anbn.logging.demo.DemoProxy.main()'.**
```

### 🧪 Example with ASM
```console
14:07:47: Executing ':hw04-aop:ru.anbn.logging.demo.DemoAsm.main()'…

> Task :spotlessInternalRegisterDependencies UP-TO-DATE
> Task :hw04-aop:processResources NO-SOURCE
> Task :hw04-aop:spotlessJava
> Task :hw04-aop:spotlessJavaApply
> Task :hw04-aop:spotlessApply
> Task :hw04-aop:compileJava
> Task :hw04-aop:classes

> Task :hw04-aop:ru.anbn.logging.demo.DemoAsm.main()
14:07:48.497 [main] INFO ru.anbn.logging.target.TestLogging -- executed method: calculation
14:07:48.499 [main] INFO ru.anbn.logging.target.TestLogging -- executed method: calculation
14:07:48.499 [main] INFO ru.anbn.logging.target.TestLogging -- executed method: calculation
14:07:48.499 [main] INFO ru.anbn.logging.target.TestLogging -- executed method: calculation

BUILD SUCCESSFUL in 497ms
5 actionable tasks: 4 executed, 1 up-to-date
14:07:48: Execution finished ':hw04-aop:ru.anbn.logging.demo.DemoAsm.main()'.
```

### ⚡ Notes
Proxy logs method name and parameters at runtime
ASM logs method name at runtime; parameter logging can be added by enhancing ASM visitor
ASM double logging is caused by EXPAND_FRAMES + COMPUTE_FRAMES in ASM configuration
This setup demonstrates two approaches to AOP-style automatic logging:
Proxy — simpler, limited to interfaces
ASM / Instrumentation — more powerful, works at bytecode level, can handle any class

### 🛠 Technologies Used
- Java 21
- Gradle (Gradle Wrapper) 8.5
- SLF4J + Logback
- ASM 9.9.1

### ✅ Result
- Automatic logging implemented using two approaches
- Demonstrates method interception without modifying business logic
- JVM instrumentation and ASM bytecode manipulation explored