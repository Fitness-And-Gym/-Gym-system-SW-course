Feature:Program Management

Scenario: Create, update, or delete fitness programs with the following details
        Given I am an Instructor
        When I create new program with  fees,  title,  duration and  difficulty
        Then New program related to me is added to the database
        When I update the program title and fees.
        Then The update will be reflected on the system
        When I delete the program
        Then It will return null when searching for it.

# // Program Management
# // ● Create, update, or delete fitness programs with the following details:
# // ● Program title, duration, difficulty level, and goals.
# // ● Video tutorials, images, or documents.
# // ● Price (if applicable).
# // ● Set schedules for group sessions (e.g., online or in-person).


# Client Interaction
# ● Communicate with enrolled clients via messaging or discussion forums.
# ● Provide feedback or progress reports to clients.


