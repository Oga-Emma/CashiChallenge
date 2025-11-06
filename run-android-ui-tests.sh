#!/bin/bash

# -----------------------------
# Configuration
# -----------------------------
echo "Running android ui tests."

FAIL=0

echo "Running Android instrumented tests..."
./gradlew :androidApp:connectedAndroidTest --info || FAIL=1

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
