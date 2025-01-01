Feature: Program Exploration and Enrollment


  Scenario: Browse programs by difficulty level
    When I filter programs by difficulty "Beginner"
    Then I should see  programs with Beginner difficulty level

  Scenario: Browse programs by program goal
    When I filter programs by goal "Muscle Building"
    Then I should see  programs have the goal "Muscle Building"

  Scenario: Enroll in a program
    Given I have program I want to enroll in titled "Weight Loss 101".
    When I enroll in the program titled "Weight Loss 101"
    Then I should be successfully enrolled
    And the program "Weight Loss 101" should show 1 enrollment



