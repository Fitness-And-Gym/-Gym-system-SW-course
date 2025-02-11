 Feature:  Subscription Management



# # Manage subscription plans for clients and instructors (e.g., Basic, Premium).
# Scenario: Manage subscription plans for clients
#     Given I am and Admin
#     And There is subscription plans for clients in the database
#     #remove a client form the premium plan
#     When I ask for A specific client plan from the database
#     Then I set the client plan to Basic


Scenario: Manage subscription plans for clients and instructors
    Given The clients and Instructors have plans subscription.
    When I want to see  plans for clients .
    Then A list of clients available plan's info show.
    When I want to see  plans for instructors .
    Then A list of instructors available plan's info show.

Scenario: Create client plan
    When I create new client plan with new details.
    Then Plan is available for clients in the database.



Scenario: Create instructor plan
    When I create new instructor plan with new details.
    Then Plan is available for instructor in the database.



# Subscription Management
# ● Manage subscription plans for clients and instructors (e.g., Basic, Premium).
