## Homework 11 — Reference Types and Caching

This module contains the solution for the eleventh homework assignment
of the OTUS Java Developer course.

### 🎯 Homework Goal

* Understand the different types of references in Java and their use cases
* Learn how cache systems work and the principles behind their lifecycle management
* Design and implement a self-made, thread-safe cache engine using `WeakHashMap`
* Integrate caching transparently into an existing database service architecture

### 📦 Module Information

**Module name:** `hw11-cache`  
**Topic:** Reference types, custom caching engines, and Flyway/Hibernate database synchronization

### 🧩 Implementation Details

* **Custom Cache Engine (`MyCache`):** A fully thread-safe implementation of `HwCache` leveraging `WeakHashMap`
  where keys automatically expire when their strong references are removed outside the cache.
* **Memory Leak Protection:** Implements the Observer pattern using `WeakReference` for `HwListener` instances.
  This ensures that forgotten listeners are cleaned up by the Garbage Collector, avoiding permanent memory leaks.
* **Data Mutation Safeguard:** The caching service uses the deep cloning method (`client.clone()`) when
  reading/writing entries. This isolates the internal state of the cache from unexpected mutations in business logic.
* **Transparent Architecture:** Implements the **Decorator Pattern** via `CachedDBServiceClient`, leaving the
  original underlying `DbServiceClientImpl` database service code completely untouched (SOLID Single
  Responsibility compliance).

### 🔍 Core Concepts: Java Reference Type Differences

To satisfy the requirements of managing memory and preventing `OutOfMemoryError`, this implementation relies on
the nuances of Java's special references:

* **Strong Reference (Default):** The regular objects we create (e.g., `Object obj = new Object();`). They are
  never collected by the GC as long as they are reachable, which can eventually cause an `OutOfMemoryError`.
* **SoftReference:** Designed for memory-sensitive caches. Soft references are retained in memory by the JVM as
  long as there is enough free space. The GC guarantees to clear them **only right before** throwing an
  `OutOfMemoryError`.
* **WeakReference:** Designed for canonical mappings like metadata or cache keys. Weak references are highly
  volatile; the GC will reclaim them during the **very next garbage collection cycle** if no strong references to
  the object remain. This is the foundation of `WeakHashMap`.
* **PhantomReference:** Used for pre-mortem cleanup actions. Calling `.get()` on a phantom reference always
  returns `null`. It is used exclusively alongside a `ReferenceQueue` to determine precisely when an object has
  been removed from memory so that low-level resources can be safely released.

### 🧪 How to Run

* Ensure your **PostgreSQL** database is running (configured on port `5430` as defined in your environment)
* Open the project in **IntelliJ IDEA**
* Run the demonstration class by clicking the **run icon (▶) next to `HwCacheDemo.java`**

### 🛠 Technologies Used

* Java 21
* Gradle (Gradle Wrapper)
* Hibernate ORM
* Flyway Database Migrations
* PostgreSQL

### ✅ Result

* Built a high-performance, generic, and thread-safe key-value cache engine.
* Demonstrated a dramatic performance increase where retrieving data from `MyCache` (**Cache Hit**) is hundreds of
  times faster than fetching it directly from the database (**Cache Miss**).
* Achieved zero memory leaks on cached data and observer listeners by leveraging proper JVM garbage collection
* mechanics.
