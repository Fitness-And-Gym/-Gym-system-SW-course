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




# Subscription Management
# ● Manage subscription plans for clients and instructors (e.g., Basic, Premium).
