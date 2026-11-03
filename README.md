# GymApp — Native Android Workout Engine

A robust, offline-first mobile workout tracking engine built with modern **Android Native (Kotlin)** and **Jetpack Compose**. Designed with strict unidirectional data flow (UDF) and relational data integrity to handle complex workout progression without cloud latency.

---

## 🛠️ Architecture & Tech Stack

- **UI Layer:** Jetpack Compose, Material 3, Custom Drag-and-Drop Gestures
- **Architecture Pattern:** MVVM with Unidirectional Data Flow (UDF)
- **State & Concurrency:** Kotlin Coroutines, StateFlow / SharedFlow
- **Data Persistence:** Android Room (SQLite), TypeConverters, DAO Pattern
- **Build System:** Gradle (Kotlin DSL), KSP

---

## 🏗️ Key Engineering Highlights

### 1. Normalized 3-Tier Relational Database
Implemented an offline-first storage engine utilizing **Room (SQLite)** with strict foreign-key integrity:
- **Relational Schema:** Multi-tier relations connecting `WorkoutRoutine` $\rightarrow$ `ExerciseSession` $\rightarrow$ `MetricSet` (reps, weight, RPE).
- **Atomic Transactions:** Wrapped multi-table operations inside `@Transaction` blocks to prevent partial state writes during live workout sessions.
- **Pre-seeded Dataset:** Bundled with pre-indexed exercise catalogs optimized for indexed sub-millisecond querying.

### 2. Reactive Unidirectional Data Flow (UDF)
- Decoupled business logic entirely into ViewModels exposing immutable `StateFlow<WorkoutUiState>` to the UI layer.
- Thread-safe mutation handling for asynchronous timer management, set logging, and background persistence.

### 3. Gesture-Driven Queue Manipulation
- Interactive exercise ordering utilizing low-level pointer input and reorderable layout states in Jetpack Compose.

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug / Meerkat or later
- JDK 17+
- Android SDK 34+

# GymApp — Native Android Workout Engine

A robust, offline-first mobile workout tracking engine built with modern **Android Native (Kotlin)** and **Jetpack Compose**. Designed with strict unidirectional data flow (UDF) and relational data integrity to handle complex workout progression without cloud latency.

---

## 🛠️ Architecture & Tech Stack

- **UI Layer:** Jetpack Compose, Material 3, Custom Drag-and-Drop Gestures
- **Architecture Pattern:** MVVM with Unidirectional Data Flow (UDF)
- **State & Concurrency:** Kotlin Coroutines, StateFlow / SharedFlow
- **Data Persistence:** Android Room (SQLite), TypeConverters, DAO Pattern
- **Build System:** Gradle (Kotlin DSL), KSP

---

## 🏗️ Key Engineering Highlights

### 1. Normalized 3-Tier Relational Database
Implemented an offline-first storage engine utilizing **Room (SQLite)** with strict foreign-key integrity:
- **Relational Schema:** Multi-tier relations connecting `WorkoutRoutine` $\rightarrow$ `ExerciseSession` $\rightarrow$ `MetricSet` (reps, weight, RPE).
- **Atomic Transactions:** Wrapped multi-table operations inside `@Transaction` blocks to prevent partial state writes during live workout sessions.
- **Pre-seeded Dataset:** Bundled with pre-indexed exercise catalogs optimized for indexed sub-millisecond querying.

### 2. Reactive Unidirectional Data Flow (UDF)
- Decoupled business logic entirely into ViewModels exposing immutable `StateFlow<WorkoutUiState>` to the UI layer.
- Thread-safe mutation handling for asynchronous timer management, set logging, and background persistence.

### 3. Gesture-Driven Queue Manipulation
- Interactive exercise ordering utilizing low-level pointer input and reorderable layout states in Jetpack Compose.

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug / Meerkat or later
- JDK 17+
- Android SDK 34+

### Build & Run
```bash
git clone [https://github.com/rewindn/gymapp.git](https://github.com/rewindn/gymapp.git)
cd gymapp
./gradlew assembleDebug
