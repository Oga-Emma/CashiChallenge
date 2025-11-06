#!/bin/bash

# -----------------------------
# Configuration
# -----------------------------

echo "Running all unit tests."

# -----------------------------
# Run Gradle tests in sequence
# -----------------------------
FAIL=0

echo "Running Android JVM unit tests..."
./gradlew :androidApp:testDebugUnitTest --info || FAIL=1

echo "Running server tests..."
./gradlew :server:test --info || FAIL=1

echo "Running shared module JVM tests..."
./gradlew :shared:cleanJvmTest && ./gradlew :shared:jvmTest --info || FAIL=1

# -----------------------------
# Exit status
# -----------------------------
if [ $FAIL -ne 0 ]; then
  echo "Some tests failed."
  exit 1
else
  echo "All tests passed."
  exit 0
fi
