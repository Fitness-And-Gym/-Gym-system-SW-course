Feature: Account Management
  As a client
  I want to create and customize my profile
  So that I can manage my personal details and preferences

  Scenario: Add personal details to the profile
    Given I am a registered client
    When I update my profile with name "Manal batta"
    Then my profile should be with userName "Manal batta"

  Scenario: Add dietary preferences or restrictions
    Given I am a registered client
    When I add dietary preferences "Vegetarian"
    Then my profile should include "Vegetarian" as my dietary preference