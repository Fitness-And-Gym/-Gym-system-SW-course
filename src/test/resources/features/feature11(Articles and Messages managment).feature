Feature: Instructor publishes Articles/Recipes and chats with clients


    Scenario: Write an article
        Given I am an Instructor
        When I write an article with title "Healthy Recipes", content "Here are some tips for healthy eating.", and type "RECIPE"
        Then the article should be created with title "Healthy Recipes", content "Here are some tips for healthy eating.", and type "RECIPE"



