Feature: User Management
        As Admin I will Add, update, and deactivate accounts for instructors and clients.

#Scenario One
  Scenario: Login as Admin
    When I input userName "admin" and Password "admin"
    Then I will be signed in as Admin
    When I input wrong userName "ali" and Password "admin"
    Then I will not be signed in with wrong userName
    When I input  userName "admin" and wrong Password "123"
    Then I will not be signed in with wrong password


  Scenario: Add client Account
    Given I am an admin
    When I create new Client with name "user" and new  Password "user"
    Then New user Account will be stored in the database


  Scenario: Update client information
    Given A client account with name "user" and password "user" exists
    When I update the client info to name "updatedUser" and password "newPassword"
    Then The client info should have name "updatedUser" and password "newPassword"


  Scenario: Deactivate client account
    Given A client account with name "user" and password "user" exists
    When I deactivate the client account
    Then The client account should be marked as deactivated



 Scenario: Add instructor Account
    When I create new instructor with name "instructor" and new  Password "instructor"
    Then New instructor Account will be stored in the database


  Scenario: Update instructor information
    Given A instructor account with name "instructor" and password "instructor" exists
    When I update the instructor info to name "updatedName" and password "updatedPassword"
    Then The instructor info should have name "updatedName" and password "updatedPassword"


  Scenario: Deactivate instructor account
    Given A instructor account with name "instructor" and password "instructor" exists
    When I deactivate the instructor account
    Then The instructor account should be marked as deactivated


#Scenario Two

  #Approve new instructor registrations.
  Scenario: Approve a new instructor registration
      When a new instructor registration request is received
      Then I approve the instructor account status to valid.


#Scenario Three

# Monitor user activity and engagement statistics.


