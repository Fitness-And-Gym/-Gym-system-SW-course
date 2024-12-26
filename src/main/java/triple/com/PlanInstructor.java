package triple.com;

import java.util.ArrayList;

public class PlanInstructor extends Plan {

    private ArrayList<Instructor> subscribersInstructor;

    // Constructor to initialize the Plan
    public PlanInstructor(String name, String type, double price, String basicBenefit) {
        super(name, type, price, basicBenefit);
        this.subscribersInstructor = new ArrayList<Instructor>();
    }

    public void subscribeInstructor(Instructor instructor) {
        subscribersInstructor.add(instructor);
    }

    public void cancelInstructorSubscription(Instructor instructor) {
        subscribersInstructor.remove(instructor);
    }

    // Method to print plan details
    public String getPlanDetails() {
        StringBuilder planDetails = new StringBuilder();

        planDetails.append("Plan Name: ").append(getName()).append("\n");
        planDetails.append("Plan Type: ").append(getType()).append("\n");
        planDetails.append("Price: $").append(getPrice()).append("\n");
        planDetails.append("Subscribers: ").append(subscribersInstructor.size()).append("\n");
        planDetails.append("Benefits: \n");

        for (String benefit : getBenefits()) {
            planDetails.append("- ").append(benefit).append("\n");
        }
        planDetails.append("--------------------------------------------------------------\n");

        return planDetails.toString();
    }

}
