## Homework 06 — ATM Emulator

This module contains the solution for the sixth homework assignment
of the OTUS Java Developer course.

### 🎯 Homework Goal

* Design an ATM emulator with proper architecture
* Apply SOLID principles
* Focus on clean domain modeling
* Avoid unnecessary infrastructure (UI, users, accounts, etc.)

### 📦 Module Information

**Module name:** `hw06-solid`
**Topic:** Object-oriented design and SOLID principles

### 🧩 Implementation Details

* The `ATM` class acts as the main facade for all operations.
* Each banknote denomination is stored in a separate cassette.
* The ATM supports:

    * Accepting banknotes of supported denominations
    * Withdrawing a requested amount using the minimal number of banknotes
    * Returning the total balance
* If a requested denomination is unsupported, an exception is thrown.
* If the requested amount cannot be dispensed, an exception is thrown.
* Domain logic is separated into:

    * `core` — main ATM logic
    * `model` — domain entities and interfaces
    * `implementation` — concrete cassette implementation
    * `exception` — custom domain exceptions
* Logging is implemented using SLF4J with Logback.

### 🧪 How to Run

To demonstrate the ATM behavior, execute the `Main` class:

```java
ru.anbn.atm.Main.java
```

The application will simulate ATM operations and print logs to the console.

### 🧪 Test Run Example
```console
20:15:05: Executing ':hw06-solid:ru.anbn.atm.Main.main()'…

> Task :hw06-solid:processResources NO-SOURCE
> Task :spotlessInternalRegisterDependencies UP-TO-DATE
> Task :hw06-solid:spotlessJava
> Task :hw06-solid:spotlessJavaApply
> Task :hw06-solid:spotlessApply
> Task :hw06-solid:compileJava
> Task :hw06-solid:classes

> Task :hw06-solid:ru.anbn.atm.Main.main()
20:15:05.698 [main] INFO ru.anbn.atm.Main -- Общий баланс: 150500
20:15:05.700 [main] INFO ru.anbn.atm.Main -- Снятие наличных: 12550
20:15:05.701 [main] INFO ru.anbn.atm.core.ATM -- Выдано: {RUB_5000=2, RUB_1000=2, RUB_500=1, RUB_50=1}
20:15:05.701 [main] INFO ru.anbn.atm.core.ATM -- Остаток после выдачи: 137950
20:15:05.701 [main] INFO ru.anbn.atm.Main -- Снятие наличных: 1550
20:15:05.701 [main] INFO ru.anbn.atm.core.ATM -- Выдано: {RUB_1000=1, RUB_500=1, RUB_50=1}
20:15:05.701 [main] INFO ru.anbn.atm.core.ATM -- Остаток после выдачи: 136400
20:15:05.701 [main] INFO ru.anbn.atm.Main -- Снятие наличных: 1100
20:15:05.701 [main] INFO ru.anbn.atm.core.ATM -- Выдано: {RUB_1000=1, RUB_50=2}
20:15:05.701 [main] INFO ru.anbn.atm.core.ATM -- Остаток после выдачи: 135300
20:15:05.701 [main] INFO ru.anbn.atm.Main -- Снятие наличных: 200000
20:15:05.701 [main] WARN ru.anbn.atm.Main -- Операция снятия наличных отклонена: Невозможно выдать запрошенную сумму
20:15:05.701 [main] INFO ru.anbn.atm.Main -- Остаток после отклонения операции снятия наличных: 135300
20:15:05.701 [main] INFO ru.anbn.atm.Main -- Внесение наличных: RUB_1000=2
20:15:05.701 [main] INFO ru.anbn.atm.core.ATM -- Внесено: RUB_1000=2
20:15:05.701 [main] INFO ru.anbn.atm.core.ATM -- Остаток после внесения: 137300
20:15:05.701 [main] INFO ru.anbn.atm.Main -- Внесение наличных: RUB_500=5
20:15:05.701 [main] INFO ru.anbn.atm.core.ATM -- Внесено: RUB_500=5
20:15:05.702 [main] INFO ru.anbn.atm.core.ATM -- Остаток после внесения: 139800

BUILD SUCCESSFUL in 571ms
5 actionable tasks: 4 executed, 1 up-to-date
20:15:05: Execution finished ':hw06-solid:ru.anbn.atm.Main.main()'.
```

### 🛠 Technologies Used

* Java 21
* Gradle (Gradle Wrapper)
* SLF4J API
* Logback

### ✅ Result

* ATM emulator implemented
* Clean architecture applied
* SOLID principles respected
* Domain logic isolated from infrastructure