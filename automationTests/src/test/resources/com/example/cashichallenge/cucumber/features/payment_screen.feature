
  Feature: Payment screen tests

    Scenario: Validate payment screen is launched
      Given User opens the app 1
      When User clicks send payment
      Then App should navigate to send payment screen
