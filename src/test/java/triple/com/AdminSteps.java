package triple.com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.Before;

import triple.com.Admin;

public class AdminSteps {
    private static Admin adminUser = new Admin();
    private Client newClient;
    private boolean actualLog;
    private Instructor instructorUser;
    private Progress progress;

    // Constructor must be public and no-arg
    public AdminSteps() {
        System.out.println("AdminSteps initialized successfully.");
    }

    // SCENARIO ONE

    // Try To login as admin
    @Given("I am an admin")
    public void iAmAnAdmin() {
        if (adminUser == null) {
            throw new IllegalStateException("Admin object not initialized.");
        }
    }

    @When("I input userName {string} and Password {string}")
    public void iInputUserNameAndPassword(String userName, String password) {
        adminUser.login(userName, password);
        actualLog = adminUser.getLogin();
        System.out.println("When step: Login attempt completed with status: " + actualLog);
    }

    @Then("I will be signed in as Admin")
    public void iWillBeSignedInAsAdmin() {
        boolean idealLog = true;
        assertEquals("Admin login failed.", idealLog, actualLog);
        System.out.println("Then step: Admin successfully logged in.");
    }

    @When("I input wrong userName {string} and Password {string}")
    public void iInputWrongUserNameAndPassword(String userName, String password) {
        adminUser.login(userName, password);
        actualLog = adminUser.getLogin();
    }

    @Then("I will not be signed in with wrong userName")
    public void iWillNotBeSignedIn() {
        boolean idealLog = false;
        assertEquals("Admin login failed.", idealLog, actualLog);
    }

    @When("I input  userName {string} and wrong Password {string}")
    public void iInputUserNameAndWrongPassword(String userName, String password) {
        adminUser.login(userName, password);
        actualLog = adminUser.getLogin();
    }

    @Then("I will not be signed in with wrong password")
    public void iWillNotBeSignedInWithWrongPassword() {
        boolean idealLog = false;
        assertEquals("Admin login failed.", idealLog, actualLog);
    }

    // Add client
    @When("I create new Client with name {string} and new  Password {string}")
    public void createNewUserNameAndPassword(String clientName, String password) {
        adminUser.login("admin", "admin");
        newClient = adminUser.createClientAccount(clientName, password);
    }

    @Then("New user Account will be stored in the database")
    public void NewUserAccountWillBeStoredInTheDatabase() {
        Client clientFromDataBase = DatabaseService.getClientByName(newClient.getClientName());

        if (clientFromDataBase != null) {
            String actualPassword = clientFromDataBase.getPassword();
            String expectedPassword = newClient.getPassword();

            assertEquals("client is not added", actualPassword, expectedPassword);
            System.out.println("Then A new client account is created successfully");
        } else {
            System.out.println("Then A new client account creation failed");
        }

    }

    // Update Client
    @Given("A client account with name {string} and password {string} exists")
    public void aClientAccountWithNameAndPasswordExists(String clientName, String password) {
        newClient = adminUser.createClientAccount(clientName, password);// here the id should be sent not the username
    }

    @When("I update the client info to name {string} and password {string}")
    public void iUpdateTheClientInfoToNameAndPassword(String clientName, String password) {
        // update this test to allow admin to update more things on user

        adminUser.updateClient(newClient.getClientId(), clientName, password);// this method takes a prev client name
                                                                              // ,new client name and new password
    }

    @Then("The client info should have name {string} and password {string}")
    public void theClientInfoShouldHaveNameAndPassword(String clientName, String password) {
        assertEquals(newClient.getClientName(), clientName);
        assertEquals(newClient.getPassword(), password);// does this should be secure and done from the admin or client
                                                        // side
    }

    @When("I deactivate the client account")
    public void iDeactivateTheClientAccount() {
        adminUser.deactivateClient(newClient.getClientId());
    }

    @Then("The client account should be marked as deactivated")
    public void theClientAccountShouldBeMarkedAsDeactivated() {
        adminUser.deactivateClient("wrong id");// wrong id is not a problem
        assertEquals(newClient.getStatus(), "invalid");

    }

    // Add Instructor
    @When("I create new instructor with name {string} and new  Password {string}")
    public void iCreateNewInstructorWithNameAndNewPassword(String instructorName, String instructorPassword) {
        instructorUser = adminUser.createInstructorAccount(instructorName, instructorPassword);// Automatically accepted
                                                                                               // no pending
    }

    @Then("New instructor Account will be stored in the database")
    public void newInstructorAccountWillBeStoredInTheDatabase() {
        Instructor actualInstructor = DatabaseService.getInstructorById(instructorUser.getId());
        assertEquals(instructorUser.getId(), actualInstructor.getId());

        // Check existing by name
        instructorUser = adminUser.createInstructorAccount("unique name", "123");
        Instructor actualInstructor2 = DatabaseService.getInstructorByName("unique name");

        assertNotNull(actualInstructor2);
        assertEquals(instructorUser.getId(), actualInstructor2.getId());

    }

    @Given("A instructor account with name {string} and password {string} exists")
    public void aInstructorAccountWithNameAndPasswordExists(String name, String password) {
        instructorUser = adminUser.createInstructorAccount(name, password);
    }

    @When("I update the instructor info to name {string} and password {string}")
    public void iUpdateTheInstructorInfoToNameAndPassword(String newName, String newPassword) {
        instructorUser.updateName(newName);// before any update the status should be checked
        instructorUser.updatePassword(newPassword);
    }

    @Then("The instructor info should have name {string} and password {string}")
    public void theInstructorInfoShouldHaveNameAndPassword(String expectedName, String expectedPassword) {
        System.out.println("actual name:" + instructorUser.getName());
        assertEquals(instructorUser.getName(), expectedName);
        assertEquals(instructorUser.getPassword(), expectedPassword);
    }

    @When("I deactivate the instructor account")
    public void iDeactivateTheInstructorAccount() {
        instructorUser.setStatus("invalid");
    }

    @Then("The instructor account should be marked as deactivated")
    public void theInstructorAccountShouldBeMarkedAsDeactivated() {
        assertEquals("invalid", instructorUser.getStatus());
    }

    // SCENARIO TWO

    // Approve new instructor registrations.
    @When("a new instructor registration request is received")
    public void aNewInstructorRegistrationRequestIsReceived() {
        instructorUser = new Instructor("Ali", "123");
        instructorUser.sendRequest();

    }

    @Then("I approve the instructor account status to valid.")
    public void iSetTheInstructorAccountStatusToValid() {
        if (instructorUser == null) {
            throw new Error("null instructor Request");
        }
        adminUser.acceptInstructor(instructorUser.getId());
        System.out.println("Instructor request accepted is " + instructorUser.getName() + " and its status = "
                + instructorUser.getStatus());

        assert instructorUser.getStatus().equals("valid") : "Failed approval";
    }

    @When("another instructor registration request is received")
    public void anotherInstructorRegistrationRequestIsReceived() {
        instructorUser = new Instructor("Alma", "123");
        instructorUser.sendRequest();
    }

    @Then("I reject the instructor account status to invalid.")
    public void iRejectTheInstructorAccountStatusToInvalid() {
        adminUser.rejectInstructor(instructorUser.getId());
        adminUser.rejectInstructor("wrong id");
        assert instructorUser.getStatus().equals("invalid") : "Failed approval";

        instructorUser = new Instructor("Alma", "123");
        instructorUser.sendRequest();

        instructorUser = adminUser.pullInstructorRequest();
        assertNotNull(instructorUser);

    }

    @Then("I can retrieve the most popular programs in my system.")
    public void iCanRetrieveTheMostPopularProgramsInMySystem() {

        adminUser.login("admin", "admin");
        DatabaseService.createMockData();// mock data is stored in the database the popular program has 3 enrollments
        ArrayList<Program> retrievedPrograms = adminUser.seeStatistics();

        assertNotNull("Retrieved programs list is null.", retrievedPrograms);
        assertTrue("Programs list is empty.", !retrievedPrograms.isEmpty());

    }

    // Retrieve Program report
    private Program program;

    @Given("I have several programs created.")
    public void I_have_several_programs_created() {
        // Create a mock program and add it to the DatabaseService
        instructorUser = new Instructor("John Doe", "I123");
        instructorUser.setStatus("valid");
        DatabaseService.addInstructor(instructorUser);
        program = new Program(instructorUser, 12, "Fit in your pants", 4, "Beginner");

        // Add the program to the mock database
        DatabaseService.addProgram(program);
        DatabaseService.addEnrollmentsToProgram(program);
        program.viewAllEnrollments();
    }

    String programReport;

    @When("I ask for a specific program report.")
    public void iAskForASpecificProgramReport() {
        // Retrieve the program report
        programReport = program.generateReport();
    }

    @Then("I see reports about the program .")
    public void iSeeReportsAboutTheProgram() {
        // Validate the report content
        assertNotNull("The program report should not be null", programReport);
        assertTrue("The report should contain the program name", programReport.contains("Fit in your pants"));
        assertTrue("The report should contain the instructor name", programReport.contains("John Doe"));
        System.out.println(programReport);
        program.viewAttendance();
    }

    // Client progress report in a goal
    @Given("a client named {string} with password {string}")
    public void a_client_named_with_password(String name, String password) {
        adminUser.login("admin", "admin");// there must be an Admins list in the database and we check loogin from the
                                          // list or only one admin with username=admin and password=admin
        newClient = adminUser.createClientAccount(name, password);
    }

    @Given("the client has a weight loss goal from 80kg to 65kg")
    public void the_client_has_a_weight_loss_goal_from_kg_to_kg() {
        progress = newClient.setGoal("weight loss", 80, 65);
    }

    @When("I update the client's weight to 75kg")
    public void I_update_the_client_s_weight_to_kg() {
        progress.updateProgress(75);// the main should show all the client progress and then the client can choose
                                    // the update a progress from the list
    }

    @Then("the progress should show {string}")
    public void the_progress_should_show(String expectedProgress) {

        String actualProgress = progress.getProgressSummary();
        System.out.println(actualProgress + "and expected to be " + expectedProgress);
        assertTrue(actualProgress.contains(expectedProgress));

        actualProgress = progress.calcProgress();
        assertEquals(expectedProgress, actualProgress);

        // Check the functionality of progress from enrolled Programs works
        instructorUser = new Instructor("John Doe", "I123");
        instructorUser.setStatus("valid");
        DatabaseService.addInstructor(instructorUser);
        program = new Program(instructorUser, 12, "Fit in your pants", 4, "Beginner");

        DatabaseService.addProgram(program);
        DatabaseService.addEnrollmentsToProgram(program);
        Progress tempProgress = new Progress("fitness", 100, 50);
        program.addProgramGoal(tempProgress);
        newClient.enrollInProgram(program.getProgramId());
        newClient.displayGoalProgress();
        System.out.println("passed the progress test.");

        DatabaseService.populateMockCompletedPrograms();
        adminUser.TrackActivePrograms();
        adminUser.TrackCompletedPrograms();
    }

    @When("empty progress list in some client")
    public void emptyProgressListInSomeClient() {
      newClient=new Client("Test user","123");
    }
    @Then("display Goal progress will print error message")
    public void displayGoalProgressWillPrintErrorMessage() {
       try{
           newClient.displayGoalProgress();
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
    }

    // Senario 3 feature 2
    String report;

    @Given("There is completed and Active programs in the system")
    public void thereIsCompletedAndActiveProgramsInTheSystem() {
        DatabaseService.populateMockCompletedPrograms();

    }

    @When("I Track Active Programs")
    public void iTrackActivePrograms() {
        report = adminUser.TrackActivePrograms();

    }

    @Then("It should include the programs and an abstract about each program")
    public void itShouldIncludeTheProgramsAndAnAbstractAboutEachProgram() {

        assertTrue(report.contains("----- Program Report -----"));
        assertTrue(report.contains("Program: Full body burn"));
        assertTrue(report.contains("Program ID: P1"));
        assertTrue(report.contains("Instructor: John"));
        assertTrue(report.contains("Program: Fit in your pants"));
        assertTrue(report.contains("Program ID: P5"));
        assertTrue(report.contains("Instructor: John Doe"));
        assertTrue(report.contains("Enrollments: 6"));
    }

    @When("I Track Completed Program")
    public void iTrackCompletedProgram() {
        System.out.println("I reached here from the valliy what to do now");

        report = adminUser.TrackCompletedPrograms();
    }

    @Then("It should include the completed programs and an abstract about each program")
    public void itShouldIncludeTheCompletedProgramsAndAnAbstractAboutEachProgram() {
        assertTrue(report.contains("----- Program Report -----"));
        assertTrue(report.contains("Program: Yoga Basics"));
        assertTrue(report.contains("Instructor: ins"));
        assertTrue(report.contains("Program: Weightlifting Pro"));
    }

    // Feature 3
    // Scenario 1
    Article article;

    @Given("There is Articles pending for approval in the system")
    public void thereIsArticlesPendingForApprovalInTheSystem() {
        DatabaseService.populateMockArticles();

    }

    @When("I get a pending article from the database")
    public void iGetAPendingArticleFromTheDatabase() {
        article = adminUser.getPendingArticle();
    }

    @When("I approve the article and publish it")
    public void iApproveTheArticleAndPublishIt() {
        adminUser.acceptArticle(article.getId());

    }

    @Then("The article will be available for users")
    public void theArticleWillBeAvailableForUsers() {
        assertTrue(article.isApproved());
        Article actual = DatabaseService.searchArticleById(article.getId());
        assertNotNull("Article should not be null", actual);

    }

    @When("I reject the article and discard")
    public void iRejectTheArticleAndDiscard() {
        article = adminUser.getPendingArticle();

        adminUser.rejectArticle(article.getId());

    }

    @Then("Then the article will be drop form system")
    public void thenTheArticleWillBeDropFormSystem() {
        assertFalse(article.isApproved());
        Article actual = DatabaseService.searchArticleById(article.getId());
        assertNull("Article should be null", actual);

    }

    // Scenario 3
    String actualOutput;

    @Given("There is feedbacks  ,complaints and suggestions in the system need to see as admin")
    public void thereIsFeedbacksComplaintsAndSuggestionsInTheSystemNeedToSeeAsAdmin() {
        DatabaseService.populateMockFeedbacks();
    }

    @When("I ask for clients last feedbacks")
    public void iAskForClientsLastFeedbacks() {
        actualOutput = adminUser.readFeedbacks();
    }

    @Then("Then I get all clients feedbacks")
    public void thenIGetAllClientsFeedbacks() {

        // Assert specific parts of the string
        assertTrue("Feedback type not found.", actualOutput.contains("Feedback Type  : complaint"));
        assertTrue("Client name not found.", actualOutput.contains("Client Name    : Alice"));
        assertTrue("Feedback message not found.",
                actualOutput.contains("Hello, I want to express my sadness for losing only 2kg."));
        assertTrue("Timestamp not found.", actualOutput.contains("Timestamp"));

    }

    // Feature 4
    // SCENARIO 1
    String actual;

    @Given("The clients and Instructors have plans subscription.")
    public void theClientsAndInstructorsHavePlansSubscription() {
        adminUser.login("admin", "admin");
        DatabaseService.populateMockPlans();
    }

    @When("I want to see  plans for clients .")
    public void iWantToSeePlansForClients() {
        actual = adminUser.seePlansForClient();
    }

    @Then("A list of clients available plan's info show.")
    public void aListOfClientsAvailablePlanSInfoShow() {
        assert (actual.contains("Plan Name: Basic"));
    }

    @When("I want to see  plans for instructors .")
    public void iWantToSeePlansForInstructors() {
        actual = adminUser.seePlansForInstructors();

    }

    @Then("A list of instructors available plan's info show.")
    public void aListOfInstructorsAvailablePlanSInfoShow() {
        assert (actual.contains("Plan Name: Basic"));

    }

    // SCENARIO 2
    @When("I create new client plan with new details.")
    public void iCreateNewClientPlanWithNewDetails() {
        adminUser.createClientPlan("Test plan", "free", 0, "benefit one");
    }

    @Then("Plan is available for clients in the database.")
    public void planIsAvailableForClientsInTheDatabase() {
        PlanClient planClient = DatabaseService.getClientPlanByNumber(0);
        assertEquals(planClient.getName(), "Test plan");
    }

    // SCENARIO 3

    @When("I create new instructor plan with new details.")
    public void iCreateNewInstructorPlanWithNewDetails() {
        adminUser.createInstructorPlan("Test plan", "free", 0, "benefit one");

    }

    @Then("Plan is available for instructor in the database.")
    public void planIsAvailableForInstructorInTheDatabase() {
        PlanInstructor planInstructor = DatabaseService.getPlansInstructors().get(0);
        assertEquals(planInstructor.getName(), "Test plan");
    }

    // Tests For Uncovered Lines

    @When("Admin logout")
    public void Admin_logout() {
        adminUser.logout();
    }

    @Then("Admin is not allowed to create Client account")
    public void adminNotAuthenticated() {
        assertNull(adminUser.createClientAccount("client", "123"));

    }

    @Then("Admin is not allowed to see statistics")
    public void Admin_is_not_allowed_to_see_statistics() {
        assertNull(adminUser.seeStatistics());
    }

    @Then("Admin is not auth to pull Instructor Request")
    public void Admin_is_not_auth_to_pull_Instructor_Request() {
        instructorUser = adminUser.pullInstructorRequest();
        assertNull(instructorUser);
        adminUser.login("admin", "admin");// to not effect other tests

    }

    @Then("Print function executes without Exceptions")
    public void Print_function_executes_without_Exceptions() {
        DatabaseService.populateMockArticles();
        DatabaseService.populateMockPrograms();
        try {
            DatabaseService.printInstructorsTable();
            DatabaseService.printDeclinedInstructorsTable();
            DatabaseService.printArticles();
            DatabaseService.printArticlesRequests();
            DatabaseService.printClientsTable();
        } catch (Exception e) {
            throw new AssertionError("Method threw an exception: " + e.getMessage());
        }
    }

}
