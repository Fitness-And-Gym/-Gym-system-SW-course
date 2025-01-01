package triple.com;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class ClinetSteps {
    private Instructor instructor;
    private Admin admin;
    private Message message;
    private Message reply;
    private Client client;

    @Before
    public void setUp() {
        client = new Client("John Doe", "password123");
        instructor = new Instructor("instructor", "123");
        instructor.sendRequest();
        admin = new Admin();
        admin.login("admin", "admin");
        admin.acceptInstructor(instructor.getId());
    }

    @Given("I am a registered client")
    public void iAmARegisteredClient() {
        client = new Client("John Doe", "password123");
    }

    @When("I update my profile with age {int}")
    public void iUpdateMyProfileWithAgeAndFitnessGoals(int age, String fitnessGoals) {
        client.updateName("John Doe");
    }

    @When("I update my profile with name {string}")
    public void I_update_my_profile_with_name(String s) {
        client.updateName(s);
    }

    @Then("my profile should be with userName {string}")
    public void my_profile_should_be_with_userName(String s) {
        String actual = client.getClientName();
        assertEquals(actual, s);
    }

    @When("I add dietary preferences {string}")
    public void iAddDietaryPreferences(String preference) {
        client.addDietaryPreference(preference); // Assuming dietary preferences are stored as status for this mock
    }

    @Then("my profile should include {string} as my dietary preference")
    public void myProfileShouldIncludeAsMyDietaryPreference(String expectedPreference) {
        assertTrue(client.getDietaryPreferences().contains(expectedPreference));
        System.out.println("hello from client");
    }

    // feature 10
    ArrayList<Program> programs;

    @When("I filter programs by difficulty {string}") // we are here still didn't implement the feature10
    public void iFilterProgramsByDifficulty(String string) {
        System.out.println("after printing;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");// it
                                                                                                                      // does
                                                                                                                      // not
                                                                                                                      // see
                                                                                                                      // this
                                                                                                                      // test

        DatabaseService.populateMockProgramsWithGoals();
        programs = client.filterProgramsByDifficulty("Beginner");// (B)Beginner (I)Intermediate (A)Advanced
        client.printPrograms(programs);

    }

    @Then("I should see  programs with Beginner difficulty level")
    public void iShouldSeeProgramsWithBeginnerDifficultyLevel() {
        for (Program program : programs) {
            assertTrue(program.getDifficulty().equals("Beginner"));
        }
    }

    @When("I filter programs by goal {string}")
    public void iFilterProgramsByGoal(String goal) {
        programs = client.filterProgramsByGoal(goal);
        client.printPrograms(programs);
    }

    @Then("I should see  programs have the goal {string}")
    public void iShouldSeeProgramsHaveTheGoal(String goal) {
        for (Program program : programs) {
            boolean isGoal = false;
            for (Progress progress : program.getProgramGoals())
                if (progress.getGoalType().equalsIgnoreCase(goal)) {
                    isGoal = true;
                    break;
                }
            assertTrue(isGoal);
        }
    }

    Program program;

    @Given("I have program I want to enroll in titled {string}.")
    public void iHaveProgramIWantToEnrollInTitled(String title) {
        program = new Program(instructor, 100, title, 4, "Beginner");
    }

    @When("I enroll in the program titled {string}")
    public void iEnrollInTheProgramTitled(String title) {
        client.enrollInProgram(program.getProgramId());
    }

    @Then("I should be successfully enrolled")
    public void iShouldBeSuccessfullyEnrolled() {
        programs = client.getClientPrograms();
        assertTrue(programs.contains(program));
    }

    @Then("the program {string} should show {int} enrollment")
    public void theProgramShouldShowEnrollment(String title, Integer enrollments) {
        assertTrue(program.getEnrollments() == 1);
    }

    //SCENARIO 3
    @When("I want to display a Goal")
    public void iWantToDisplayAGoal() {
       client.setGoal("Test goal",10,50);//ensure there is at least one goal
    }
    @Then("A print function performed with no exceptions")
    public void aPrintFunctionPerformedWithNoExceptions() {
        try {
            client.displayGaolProgressIn(0);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
