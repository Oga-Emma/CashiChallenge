
  Feature: Transactions screen tests

    Scenario: Validate transactions screen is launched
      Given User opens the app 2
      When User clicks view transactions
      Then App should navigate to transactions screen
