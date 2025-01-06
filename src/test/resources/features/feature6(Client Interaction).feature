Feature: Client-Instructor Messaging
  As an instructor, I want to communicate with my enrolled clients via messaging.

  Scenario: Send, receive,and Reply to  messages
    Given I am an Instructor Again
    When I send a message to the client titeled "Welcome Message" and content "Welcome To our community"
    Then The "Welcome Message" is added to the client's inbox
    When the client replies to "Welcome Message" by message with content "Thanks I am excited"
    Then The "Thanking" reply is added to my inbox
    Then "Welcome Message" will be deleted from client inbox
    Then I can view my messages with clients
    When I have messages from a client
    Then I can see and reply to that message



