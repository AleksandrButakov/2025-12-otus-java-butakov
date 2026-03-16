## Homework 08 — JSON Processor

This module contains the solution for the eighth homework assignment
of the OTUS Java Developer course.

### 🎯 Homework Goal

* Learn to read and write JSON files using Jackson
* Process lists with streams, grouping, aggregation, and sorting
* Work with temporary directories in automated tests

### 📦 Module Information

**Module name:** `hw08-serialization`  
**Topic:** JSON processing, file I/O, and Java Streams

### 🧩 Implementation Details

* **Reading input JSON** from `resources` into `Measurement` records
* **Processing data:** group by `name` and sum `value` fields in a single stream
* **Serialization:** aggregated results saved as JSON to a temporary file (`@TempDir`)
* **Automated tests** verify list size, aggregated map, and output file contents

### 🧪 How to Run

* Open the project in **IntelliJ IDEA**
* Run the automated tests by clicking the **run icon (▶) next to the test class**

### 🛠 Technologies Used

* Java 21
* Gradle (Gradle Wrapper)
* Jackson Databind
* JUnit 5

### ✅ Result

* JSON input is read from `resources`
* Data is grouped and aggregated by `name`
* Output JSON is serialized in a collection **sorted in ascending order by value**
* Temporary files are used during tests and automatically cleaned up