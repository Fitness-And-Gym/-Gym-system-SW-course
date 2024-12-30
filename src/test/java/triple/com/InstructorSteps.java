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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class InstructorSteps {
    private Instructor instructor;
    private Admin admin;
    private Message message;
    private Message reply;
    private Client client;

    @Before
    public void createInstructor() {
        instructor = new Instructor("instructor", "123");
        instructor.sendRequest();
        admin = new Admin();
        admin.login("admin", "admin");
        admin.acceptInstructor(instructor.getId());
    }

    @Given("I am an Instructor")
    public void iAmAnInstructor() {
        instructor = instructor.login("instructor", "123");
    }

    Program program;

    @When("I create new program with  fees,  title,  duration and  difficulty")
    public void iCreateNewProgramWithFeesTitleDurationAndDifficulty() {
        program = instructor.createProgram(12, "Welcome to sports world", 2, "beginner");
    }

    @Then("New program related to me is added to the database")
    public void newProgramRelatedToMeIsAddedToTheDatabase() {
        Program programInDB = DatabaseService.getProgramById(program.getProgramId());
        assert (program.getProgramId() == programInDB.getProgramId());
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

    @When("I delete the program")
    public void I_delete_the_program() {
        System.out.println("before deletion//////////////////////////////");
        admin.TrackActivePrograms();

        instructor.deleteMyProgram(program.getProgramId());
    }

    @Then("It will return null when searching for it.")
    public void It_will_return_null_when_searching_for_it() {

        System.out.println("after deletion//////////////////////////////////");
        admin.TrackActivePrograms();
        assertNull(DatabaseService.getProgramById(program.getProgramId()));
    }

    @Given("I am an Instructor Again")
    public void iAmAnInstructorAgain() {
        instructor = new Instructor("instructor", "123");
        client = new Client("client", "123");
        client.enrollInProgram("P1"); // Assuming the program ID is "P1"
        admin.acceptInstructor(instructor.getId());
        instructor = instructor.login("instructor", "123");

    }

    @When("I send a message to the client with a title and content")
    public void iSendAMessageToTheClientWithATitleAndContent() {
        message = instructor.sendMessage(client, "Welcome", "Hello, welcome to the program!");
    }

    @Then("The message is added to the client's inbox")
    public void theMessageIsAddedToTheClientsInbox() {
        assertNotNull(message);
        assertTrue(client.getInbox().contains(message));
    }

    @When("the client replies to the message")
    public void the_client_replies_to_the_message() {
        reply = client.replyToMessage(message, "Thank you for the welcome!");
    }

    @Then("The reply is added to my inbox")
    public void theReplyIsAddedToMyInbox() {
        assertNotNull(reply);
        assertTrue(instructor.getInbox().contains(reply));
    }

    @When("I delete a message from my inbox")
    public void iDeleteAMessageFromMyInbox() {
        instructor.deleteMessage(message);
    }

    @Then("The message no longer appears in my inbox")
    public void theMessageNoLongerAppearsInMyInbox() {
        assertFalse(instructor.getInbox().contains(message));
    }

    @Then("The message remains in the client's inbox")
    public void theMessageRemainsInTheClientsInbox() {
        assertTrue(client.getInbox().contains(message));
    }

}
