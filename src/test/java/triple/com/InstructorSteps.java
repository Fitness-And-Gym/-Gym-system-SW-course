package triple.com;

import triple.com.Admin;
import triple.com.DatabaseService;
import triple.com.Instructor;
import triple.com.Program;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;

public class InstructorSteps {
    private Instructor instructor;
    private Admin admin;
    private Message message;
    private Message reply;
    private Client client;
    private Article article;

    @Before
    public void createInstructor() {
        instructor = new Instructor("instructor", "123");
        instructor.sendRequest();
        admin = new Admin();
        admin.login("admin", "admin");
        admin.acceptInstructor(instructor.getId());
        instructor.login("instructor", "123");

    }

    @Given("I am an Instructor")
    public void iAmAnInstructor() {
        instructor = instructor.login("instructor", "123");
        try {
            instructor.toString();
        } catch (Exception e) {
            System.out.println("No error printing instructor should be thrown" + e.getMessage());
        }

    }

    Program program;

    @When("I create new program with  fees,  title,  duration and  difficulty")
    public void iCreateNewProgramWithFeesTitleDurationAndDifficulty() {
        program = instructor.createProgram(12, "Welcome to sports world", 2, "beginner");
    }

    @Then("New program related to me is added to the database")
    public void newProgramRelatedToMeIsAddedToTheDatabase() {
        Program programInDB = DatabaseService.getProgramById(program.getProgramId());
        assertTrue(instructor.getPrograms().contains(programInDB));
        assert (program.getProgramId() == programInDB.getProgramId());
        try {
            instructor.toString();
        } catch (Exception e) {
            System.out.println("No error printing instructor with programs should be thrown" + e.getMessage());
        }
    }

    @When("I update the program title and fees.")
    public void I_update_the_program_title_and_fees() {
        instructor.updateProgramTitle("updated title", program.getProgramId());
        instructor.updateProgramFees(55, program.getProgramId());
    }

    @Then("The update will be reflected on the system")
    public void The_update_will_be_reflected_on_the_system() {
        Program programInDB = DatabaseService.getProgramById(program.getProgramId());
        assert (programInDB.getProgramId() == program.getProgramId());
    }

    @Then("I can see my programs")
    public void I_can_see_my_programs() {
        try {
            instructor.viewPrograms();
        } catch (Exception e) {
            System.out.println("error calling viewPrograms method" + e.getMessage());
        }
    }

    @When("I add a goal of type {string} to my program with start value {int} kg and target value of {int} kg")
    public void iAddAGoalOfTypeToMyProgramWithStartValueAndTargetValue(String goalType, int startValue,
            int targetValue) {
        instructor.addGoalToProgram(program.getProgramId(), goalType, startValue, targetValue);
    }

    @Then("the {string} goal will be added to my program")
    public void the_goal_will_be_added_to_my_program(String expectedGoalType) {
        List<Progress> goals = program.getProgramGoals();

        boolean goalExists = false;

        for (Progress goal : goals) {
            if (goal.getGoalType().equals(expectedGoalType)) {
                goalExists = true;
                break; // Exit loop once goal is found
            }
        }

        assertTrue("The goal type " + expectedGoalType + " was not added to the program.", goalExists);
    }

    @When("Unautherized Instructor try to  delete the program")
    public void Unautherized_Instructor_try_to_delete_the_program() {
        new Instructor("unauth", "123").deleteMyProgram(program.getProgramId());
    }

    @Then("It will not be deleted")
    public void It_will_not_be_deleted() {
        assertNotNull(DatabaseService.getProgramById(program.getProgramId()));
    }

    @When("I delete the program")
    public void I_delete_the_program() {
        System.out.println("before deletion//////////////////////////////");
        admin.TrackActivePrograms();
        instructor.deleteMyProgram(program.getProgramId());

    }

    @Then("It will return null when searching for it.")
    public void It_will_return_null_when_searching_for_it() {
        // instructor with wrong values is not allowed
        assertNull(instructor.login("wrong", "false"));

        System.out.println("after deletion//////////////////////////////////");
        admin.TrackActivePrograms();
        assertNull(DatabaseService.getProgramById(program.getProgramId()));
    }

    // FEATURE
    @Given("I am an Instructor Again")
    public void iAmAnInstructorAgain() {
        instructor = new Instructor("instructor", "123");
        client = new Client("client", "123");
        client.enrollInProgram("P1"); // Assuming the program ID is "P1"
        admin.acceptInstructor(instructor.getId());
        instructor = instructor.login("instructor", "123");
    }

    @When("I send a message to the client titeled {string} and content {string}")
    public void iSendAMessageToTheClientTiteledAndContent(String title, String content) {
        message = instructor.sendMessage(client.getClientId(), title, content);
    }

    @Then("The {string} is added to the client's inbox")
    public void theIsAddedToTheClientSInbox(String string) {
        assertTrue(client.getInbox().contains(message));
    }

    @When("the client replies to {string} by message with content {string}")
    public void the_client_replies_to_by_message_with_content(String s, String s2) {
        reply = client.replyToMessage(message, s2);
    }

    @Then("The {string} reply is added to my inbox")
    public void theReplyIsAddedToMyInbox(String string) {
        assertTrue(instructor.getInbox().contains(reply));
    }

    @Then("{string} will be deleted from client inbox")
    public void willBeDeletedFromClientInbox(String string) {
        assertFalse(client.getInbox().contains(message));
    }

    @When("I have messages from a client")
    public void I_have_messages_from_a_client() {
        message = client.sendMessage(instructor.getId(), "Message", "from client one");
    }

    @Then("I can see and reply to that message")
    public void I_can_see_and_reply_to_that_message() {
        reply = instructor.replyToMessage(message, "Response test message");
        assertTrue(client.getInbox().contains(reply));
    }

    // FEATURE 11
    @When("I write an article with title {string}, content {string}, and type {string}")
    public void iWriteAnArticleWithTitleContentAndType(String title, String content, String type) {
        ArticleType articleType = ArticleType.valueOf(type.toUpperCase());
        article = instructor.writeArticle(title, content, articleType);
    }

    @Then("the article should be created with title {string}, content {string}, and type {string}")
    public void theArticleShouldBeCreatedWithTitleContentAndType(String expectedTitle, String expectedContent,
            String expectedType) {
        assertNotNull("The article should not be null.", article);
        assertEquals("The title should match.", expectedTitle, article.getTitle());
        assertTrue(expectedContent.equals(article.getContent()));
        assertEquals("The article's author should be the instructor.", instructor.getId(), article.getAuthor().getId());
    }

    @When("I change my subscription plan to a paid one")
    public void I_change_my_subscription_plan_to_a_paid_one() {
        instructor.changeSubscription(new PlanInstructor("Test plan", "paid", 100, "Create more than 50 programs"));

    }

    @Then("My plan will not be free")
    public void My_plan_will_not_be_free() {
        PlanInstructor planInstructor = instructor.getPlan();
        assertEquals("Test plan", planInstructor.getName());
        assertTrue(100 == planInstructor.getPrice());
    }

    @When("I delete my subscription plan")
    public void I_delete_my_subscription_plan() {
        instructor.deleteSubscription();
    }

    @Then("The Basic free plan will be my plan by default")
    public void The_Basic_free_plan_will_be_my_plan_by_default() {
        PlanInstructor planInstructor = instructor.getPlan();
        assertEquals("Basic", planInstructor.getName());
        assertTrue(0 == planInstructor.getPrice());
    }

    @Then("I can view my messages with clients")
    public void I_can_view_my_messages_with_clients() {
        try {
            instructor.viewMessages();
        } catch (Exception e) {
            System.out.println("error calling viewMessages method" + e.getMessage());
        }
    }

    // Logged out Instructor
    @When("I log out from my account and try to create a program")
    public void I_log_out_from_my_account_and_try_to_create_a_program() {
        instructor = new Instructor("ins", "123");
        program = instructor.createProgram(20, "title", 10, "BEGINNER");
    }

    @Then("The System will prevent me from createing Program.")
    public void The_System_will_prevent_me_from_createing_Program() {
        assertNull(program);
    }

    @Then("I can't see my programs.")
    public void I_can_t_see_my_programs() {
        instructor = new Instructor("ins", "123");

        try {
            instructor.viewPrograms(); // This should throw an error if not logged in
            fail("Expected Error to be thrown, but none was thrown.");
        } catch (Error e) {
            // Check that the message matches the expected one
            assertEquals("Unauthenticated Instructor", e.getMessage());
        }
    }

}
