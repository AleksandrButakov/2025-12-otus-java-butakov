## Homework 05 — Garbage Collector and Heap Size

This module contains the solution for the fifth homework assignment
of the OTUS Java Developer course.

### 🎯 Homework Goal

* Understand how different heap sizes affect application performance
* Analyze the behavior of the Garbage Collector (G1GC)
* Determine the optimal heap size
* Optimize the application to work efficiently with minimal heap size

### 📦 Module Information

**Module name:** `hw05-gc`
**Topic:** JVM Memory, Heap Sizing, Garbage Collection

---

### 📊 Experiment Results

The application (`CalcDemo`) was executed with different heap sizes
using equal values for `-Xms` and `-Xmx`.

Heap sizes tested:

* 16 MB
* 128 MB
* 256 MB
* 512 MB
* 1024 MB
* 2048 MB

## GC and Heap Experiment Results

| Xms / Xmx | PrevValue  | PrevPrevValue | SumLastThreeValues | SomeValue   | Sum        | Time (ms) |
|-----------|------------|---------------|------------------|------------|-----------|-----------|
| 16 MB     | 99,999,999 | 99,999,998    | 299,999,994       | 1,595,708,918 | -703,233,354 | 14,793    |
| 128 MB    | 99,999,999 | 99,999,998    | 299,999,994       | 690,122,276 | 738,091,456 | 10,143    |
| 256 MB    | 99,999,999 | 99,999,998    | 299,999,994       | 1,481,007,971 | 37,668,380 | 9,586    |
| 512 MB    | 99,999,999 | 99,999,998    | 299,999,994       | 1,353,826,586 | -1,634,159,153 | 9,783    |
| 1024 MB   | 99,999,999 | 99,999,998    | 299,999,994       | 85,393,993 | 1,993,916,936 | 9,618    |
| 2048 MB   | 99,999,999 | 99,999,998    | 299,999,994       | 2,085,378,840 | -1,810,692,967 | 9,698    |

> **Note:** Values were obtained by running `CalcDemo` with `counter = 100_000_000`. The collection was cleared\
> every 100,000 elements, and `Random` was used to add a random number to the sum.

Execution time ranged approximately between **9–15 seconds** across all configurations.

---

### 🔎 Analysis

1. **Execution time is almost independent of heap size**

   Increasing heap size from 16 MB up to 2048 MB did not significantly reduce execution time.\
   The difference between configurations was within ~1 second.

   This indicates that:

  * The application does not heavily depend on large memory allocation.
  * GC is not the primary performance bottleneck in this case.

2. **Stable deterministic values**

   The following values remained identical across all runs:

  * `PrevValue = 99,999,999`
  * `PrevPrevValue = 99,999,998`
  * `SumLastThreeValues = 299,999,994`

   These values depend only on the loop counter and are not affected by heap size.

3. **Random-dependent values**

   The values of:

  * `SomeValue`
  * `Sum`

   varied between runs because `SecureRandom` was used in calculations.
   Their variation is not related to heap size.

4. **Why small heap works**

   The collection inside the application is cleared periodically
   (every 100,000 elements), preventing excessive memory accumulation.

   As a result:

  * Even 16 MB of heap was sufficient.
  * No `OutOfMemoryError` occurred.
  * GC overhead remained manageable.

---

### 📌 Conclusion

* Increasing heap size beyond a certain threshold **does not improve performance** for this application.
* The optimal heap size is the **minimum size that allows stable execution without OOM**.
* In this experiment, even **16 MB** was sufficient.
* Application logic (clearing collections) has a much greater impact on memory behavior than heap size alone.

---

### 🧩 Implementation Details
- Original program calculates sums of numbers using a list of objects (`Data`) and random values
- Optimization changes made without altering logic:
   - All `Integer` replaced with `int` primitives to reduce memory allocation overhead
   - `SecureRandom` replaced with `ThreadLocalRandom.current()` for faster random number generation
   - `ArrayList<Data>` is periodically cleared to prevent memory accumulation
- These optimizations allow the program to run with very small heap sizes (starting from 7 MB) and significantly improve speed

### 🧪 Performance Measurements

| Heap Size | PrevValue | PrevPrevValue | SumLastThreeValues | SomeValue     | Sum          | Time, msec |
|-----------|-----------|---------------|------------------|---------------|--------------|------------|
| 7 MB      | 99,999,999 | 99,999,998   | 299,999,994      | 1,308,959,846 | -986,790,973 | 8,894      |
| 16 MB     | 99,999,999 | 99,999,998   | 299,999,994      | 2,077,972,718 | -157,576,967 | 2,181      |
| 64 MB     | 99,999,999 | 99,999,998   | 299,999,994      | 576,914,571   | 1,150,541,808| 1,049      |
| 128 MB    | 99,999,999 | 99,999,998   | 299,999,994      | 1,564,255,431 | -776,220,329 | 1,046      |
| 256 MB    | 99,999,999 | 99,999,998   | 299,999,994      | 530,999,381   | 490,111,263  | 1,020      |
| 512 MB    | 99,999,999 | 99,999,998   | 299,999,994      | 1,573,384,782 | -1,435,020,124 | 1,124    |
| 1024 MB   | 99,999,999 | 99,999,998   | 299,999,994      | 2,135,561,938 | -437,328,408 | 1,326      |
| 2048 MB   | 99,999,999 | 99,999,998   | 299,999,994      | 2,135,561,938 | -437,328,408 | 1,326      |

> ⚡ Observations:
> - The program starts working with as little as 7 MB heap
> - Using primitives and `ThreadLocalRandom` significantly reduces memory usage and CPU overhead
> - After 128 MB, further increase in heap has negligible effect on execution time

### ✅ Result
- Optimized program runs with minimal heap (7 MB)
- Execution time reduced compared to unoptimized version
- Memory footprint controlled via clearing the list every 100,000 elements
- Demonstrates how to measure GC and heap effects in practice

### 🖥 JVM Options for Running the Program

To reproduce the results and experiments with heap optimization, use the following VM options:
````markdown
-Xms256m
-Xmx256m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/heapdump.hprof
-XX:+UseG1GC
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
````

> 💡 Notes:
>
> * `-Xms` / `-Xmx` specify the initial and maximum heap size
> * `-XX:+HeapDumpOnOutOfMemoryError` creates a heap dump if OOM occurs
> * `-XX:+UseG1GC` enables the G1 Garbage Collector
> * `-Xlog:gc=debug` logs GC events with detailed info to file

### 🛠 Technologies Used
- Java 21
- Gradle 8.5
- SLF4J for logging
- ThreadLocalRandom for efficient random number generation