#!/bin/bash


echo "Running appium automation tests."

nvm use 18

# Check if Appium is running
if pgrep -x "appium" > /dev/null
then
  echo "Appium is already running."
else
  echo "Starting Appium..."
  # Start Appium in the background and redirect output to a log file
  appium > appium.log 2>&1 &
  APP_PID=$!
  echo "Appium started with PID $APP_PID"

  # Give Appium a few seconds to start up
  sleep 5
fi

# --- Run Gradle tests ---
echo "Running automation tests..."
./gradlew :automationTests:test
TEST_EXIT_CODE=$?

# --- Shut down Appium ---
if is_appium_running; then
  echo "Stopping Appium..."
  # Try graceful shutdown first
  pkill -f "appium"
  sleep 2
  # Kill leftover process if still alive
  if is_appium_running; then
    echo "Force-killing Appium process..."
    lsof -ti :$APPIUM_PORT | xargs kill -9
  fi
  echo "Appium stopped."
fi

# --- Exit with the same code as Gradle test ---
exit $TEST_EXIT_CODE
