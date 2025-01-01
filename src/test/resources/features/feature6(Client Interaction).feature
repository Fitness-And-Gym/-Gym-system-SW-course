Feature: Client-Instructor Messaging
  As an instructor, I want to communicate with my enrolled clients via messaging.

  Scenario: Send, receive, and delete messages
    Given I am an Instructor Again
    When I send a message to the client with a title and content
    Then The message is added to the client's inbox
    When the client replies to the message
    Then The reply is added to my inbox
    When I delete a message from my inbox
    Then The message no longer appears in my inbox
    And The message remains in the client's inbox


