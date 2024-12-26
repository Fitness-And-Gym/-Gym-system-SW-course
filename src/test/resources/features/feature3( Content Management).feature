Feature:  Content Management


Scenario: Approve or reject wellness articles, tips, or recipes shared by instructors.
    Given There is Articles pending for approval in the system
    When  I get a pending article from the database
    And  I approve the article and publish it
    Then The article will be available for users
    When I reject the article and discard
    Then Then the article will be drop form system

Scenario: Handle user feedback , complaints and suggestions
    Given There is feedbacks  ,complaints and suggestions in the system need to see as admin
    When  I ask for clients last feedbacks
    Then  Then I get all clients feedbacks

# Content Management
# ● Approve or reject wellness articles, tips, or recipes shared by instructors.
# ● Approve articles or tips shared on health and wellness.(duplicate)
# ● Handle user feedback and complaints.
# may add a review check if I had time.