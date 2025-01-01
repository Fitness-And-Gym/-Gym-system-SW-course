Feature: Account Management
  As a client
  I want to create and customize my profile
  So that I can manage my personal details and preferences

  Scenario: Add personal details to the profile
    Given I am a registered client
    When I update my profile with name "Manal batta"
    Then my profile should be with userName "Manal batta"

  Scenario: Add personal details to the profile
        Given I am a registered client
        When I subscribe to a paid  plan
        Then My subscription will be changed from Basic
        When I cancel my subscription
        Then The plan will be Basic

  Scenario: Add dietary preferences or restrictions
    Given I am a registered client
    When I add dietary preferences "Vegetarian"
    Then my profile should include "Vegetarian" as my dietary preference
    When I delete the "Vegetarian" dietary preference
    Then my dietary preference will be updated to not include "Vegetarian"

    Scenario: Display  information I request as a client
        Given I am a registered client
        When I want to see my profile summery
        Then All "Ali" information will be displayed without errors

    Scenario: Add and Update Goals
      Given I am a registered client
      When I set "Build Muscles" Goal with initial value 1 and target value 10
      Then I can see the "Build Muscles" Goal in my profile
      When I update the goal progress to 3
      Then I can see current value of 3 and progress percent calculated


