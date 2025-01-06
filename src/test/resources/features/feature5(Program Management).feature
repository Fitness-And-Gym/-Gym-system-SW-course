Feature:Program Management

Scenario: Create, update, or delete fitness programs with the following details
        Given I am an Instructor
        When I create new program with  fees,  title,  duration and  difficulty
        Then New program related to me is added to the database
        When I update the program title and fees.
        When I add a goal of type "Be fit" to my program with start value 100 kg and target value of 70 kg
        Then the "Be fit" goal will be added to my program
        Then The update will be reflected on the system
        Then I can see my programs
        When Unautherized Instructor try to  delete the program
        Then It will not be deleted
        When I delete the program
        Then It will return null when searching for it.
        When I mark attendance for my program
        Then My program attendance will have records of attendance


Scenario: I want to delete my subscription
        When I change my subscription plan to a paid one
        Then My plan will not be free
        When I delete my subscription plan
        Then The Basic free plan will be my plan by default


Scenario: logged out Authorization check
        When I log out from my account and try to create a program
        Then The System will prevent me from createing Program.
        Then I can't see my programs.








