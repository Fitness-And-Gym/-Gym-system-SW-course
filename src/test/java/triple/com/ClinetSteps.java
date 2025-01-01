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
    }

    @When("I delete the {string} dietary preference")
    public void I_delete_the_dietary_preference(String s) {
        client.deleteDietaryPreference(s);
    }

    @Then("my dietary preference will be updated to not include {string}")
    public void my_dietary_preference_will_be_updated_to_not_include(String deletedPreference) {
        assertFalse(client.getDietaryPreferences().contains(deletedPreference));
        // make sure you can print the preferences
        try {
            client.printDietaryPreferences();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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

    // SCENARIO 3

    String summery;

    @When("I want to see my profile summery")
    public void I_want_to_see_my_profile_summery() {
        client.updateName("Ali");
        try {
            summery = client.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Then("All {string} information will be displayed without errors")
    public void All_information_will_be_displayed_without_errors(String name) {
        assertTrue(summery.contains(name));
    }

    PlanClient planClient;

    // SCENARIO 4
    @When("I subscribe to a paid  plan")
    public void iSubscribeToAPaidPlan() {
        planClient = DatabaseService.getPlansClient().get(1);
        client.changeSubscription(planClient);
    }

    @Then("My subscription will be changed from Basic")
    public void mySubscriptionWillBeChangedFromBasic() {
        assertSame(client.getPlan(), planClient);
    }

    @When("I cancel my subscription")
    public void iCancelMySubscription() {
        client.deleteSubscription();
    }

    @Then("The plan will be Basic")
    public void thePlanWillBeBasic() {
        assertSame(DatabaseService.getBasicPlanClient(), client.getPlan());
    }

    // SCENARIO 5
    String actual;

    @When("I set {string} Goal with initial value {double} and target value {double}")
    public void iSetGoalWithInitialValueAndTargetValue(String title, Double current, Double target) {
        client.setGoal(title, current, target);
    }

    @Then("I can see the {string} Goal in my profile")
    public void I_can_see_the_Goal_in_my_profile(String title) {
        actual = client.displayGaolProgressIn(0);
        assertTrue(actual.contains(title));
    }

    @When("I update the goal progress to {double}")
    public void iUpdateTheGoalProgressTo(double update) {
        client.updateGoalProgress(0, update);
    }

    @Then("I can see current value of {int} and progress percent calculated")
    public void iCanSeeCurrentValueOfAndProgressPercentCalculated(Integer int1) {
        actual = client.displayGaolProgressIn(0);
        assertTrue(actual.contains(int1.toString()));

    }

}
