Feature: Program Monitoring
  As an admin,
  I want to monitor programs and see statistics.

#Scenario One
  Scenario:  View statistics on the most popular programs by enrollment.
    Given I am an admin
    Then I can retrieve the most popular programs in my system.

#Scenario Two

  #Generate reports on revenue, attendance.
  Scenario: Generate reports on revenue ,attendance,and client progress.
    Given I have several programs created.
    When I ask for a specific program report.
    Then I see reports about the program .

 #Show the client progress In a specific goal .
  Scenario: Check client progress for Weight Loss Goal
    Given a client named "Alice" with password "password123"
    And the client has a weight loss goal from 80kg to 65kg
    When I update the client's weight to 75kg
    Then the progress should show "33.33%"
    When empty progress list in some client
    Then display Goal progress will print error message


#Scenario three

#Track active and completed programs.
  Scenario:Track active and completed programs.
    Given There is completed and Active programs in the system
    When I Track Active Programs
    Then It should include the programs and an abstract about each program
    When I Track Completed Program
    Then It should include the completed programs and an abstract about each program



