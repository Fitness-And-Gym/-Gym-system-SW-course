package triple.com;

import java.util.ArrayList;

/**
 * The PlanInstructor class extends the Plan class and represents a subscription plan for instructors.
 * It adds functionality to manage instructors who subscribe to the plan and provides a method to view plan details.
 */
public class PlanInstructor extends Plan {

    private ArrayList<Instructor> subscribersInstructor; // List of instructors subscribed to this plan

    /**
     * Constructs a new PlanInstructor with the specified name, type, price, and basic benefit.
     * Initializes the list of subscribers to an empty list.
     *
     * @param name the name of the plan (e.g., "Basic" or "Premium")
     * @param type the type of the plan (e.g., "Paid" or "Free")
     * @param price the price of the plan
     * @param basicBenefit a basic benefit included in the plan
     */
    public PlanInstructor(String name, String type, double price, String basicBenefit) {
        super(name, type, price, basicBenefit);
        this.subscribersInstructor = new ArrayList<Instructor>();
    }

    /**
     * Subscribes an instructor to this plan.
     *
     * @param instructor the instructor to be subscribed
     */
    public void subscribeInstructor(Instructor instructor) {
        subscribersInstructor.add(instructor);
    }

    /**
     * Cancels an instructor's subscription to this plan.
     *
     * @param instructor the instructor whose subscription will be canceled
     */
    public void cancelInstructorSubscription(Instructor instructor) {
        subscribersInstructor.remove(instructor);
    }

    /**
     * Returns a string representation of the plan details, including name, type, price,
     * number of subscribers, and associated benefits.
     *
     * @return a string containing the details of the plan
     */
    public String getPlanDetails() {
        StringBuilder planDetails = new StringBuilder();

        // Append details to StringBuilder
        planDetails.append("Plan Name: ").append(getName()).append("\n");
        planDetails.append("Plan Type: ").append(getType()).append("\n");
        planDetails.append("Price: $").append(getPrice()).append("\n");
        planDetails.append("Subscribers: ").append(subscribersInstructor.size()).append("\n");
        planDetails.append("Benefits: \n");

        // Loop through benefits and append each one
        for (String benefit : getBenefits()) {
            planDetails.append("- ").append(benefit).append("\n");
        }
        planDetails.append("--------------------------------------------------------------\n");

        return planDetails.toString();
    }
}
