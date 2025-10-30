# Cashi Challenge

This project is a simple Android application built with Kotlin Multiplatform (KMP) that demonstrates a basic payment flow, allowing users to send payments and view their transaction history.

## Architecture

The project follows a modern Android architecture, leveraging Kotlin Multiplatform to share business logic between potential platforms (like iOS) and a clean, scalable structure for the Android-specific code.

### Kotlin Multiplatform (KMP)

Kotlin Multiplatform allows for sharing code across different platforms, such as Android, iOS, and even web or desktop. In this project, the KMP architecture is structured as follows:

- **`shared` module:** This is the core of the KMP architecture. It contains platform-independent code, including:
    - **Business Logic:** Use cases and domain models that define the core functionality of the app.
    - **Data Layer:** Repositories and data sources that handle data operations.
    - **Networking:** Network clients for making API calls.
- **`androidApp` module:** This module contains the Android-specific implementation, including:
    - **UI:** All Jetpack Compose screens, ViewModels, and navigation.
    - **Dependency Injection:** Koin setup for Android-specific dependencies.
    - **Platform-Specific APIs:** Any code that requires the Android SDK.

The primary benefit of this approach is code reusability. The entire business and data logic in the `shared` module can be compiled and used on other platforms like iOS without any changes, significantly reducing development time and ensuring consistency.

### MVVM (Model-View-ViewModel)

The UI in the `androidApp` module is built using the MVVM pattern:
- **View:** The Jetpack Compose screens (`HomeScreen`, `SendPaymentScreen`, `TransactionHistoryScreen`,).
- **ViewModel:** The ViewModels (`SendPaymentViewModel`, `TransactionHistoryViewModel`) that hold the UI state and handle user actions.
- **Model:** The data classes and domain models from the `shared` module.

### Dependency Injection

Dependency injection is managed using **Koin**, which helps to decouple components and makes the codebase more modular and testable.

## Project Setup

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    ```
2.  **Open in Android Studio:** Open the cloned project in the latest stable version of Android Studio.
3**Sync Gradle:** Let Android Studio sync the Gradle files.

## How to Run the App

You can run the application on an emulator or a physical device:
1.  Select the **`androidApp`** run configuration in Android Studio.
2.  Choose your target device from the dropdown menu.
3.  Click the **Run** button.

## How to Run Tests

The project includes a suite of tests to ensure code quality and correctness.

### Unit Tests

Unit tests are located in `shared/src/commonTest` and `androidApp/src/test`. They test individual components like UseCases and ViewModels.

- **To run all unit tests:**
  ```bash
  ./gradlew test
  ```
- **To run a specific test:** Open the test file in Android Studio and click the green play icon in the gutter next to the class or test function name.

### UI / Instrumentation Tests

UI tests are located in `androidApp/src/androidTest`. They test the UI components and user flows.

- **To run all UI tests:**
- First start a device emulator
- Then run the following:
  ```bash
  ./gradlew connectedAndroidTest
  ```
- **To run a specific test:** Open the test file (e.g., `MainActivityTest.kt`) and run it from the gutter icon.

### BDD (Behavior-Driven Development) Tests

The project is set up with Cucumber for BDD tests. These tests are also part of the instrumentation tests.

- **To run BDD tests:** The `connectedAndroidTest` Gradle task will also execute your Cucumber feature files. Make sure you have a test runner configured correctly in your `build.gradle.kts` file.

### Performance Tests

*(Note: No performance tests are currently implemented in this project.)*

If you were to add performance tests (e.g., using Jetpack Macrobenchmark), you would typically run them from a separate module using a dedicated Gradle command:

```bash
./gradlew :macrobenchmark:connectedAndroidTest
```
