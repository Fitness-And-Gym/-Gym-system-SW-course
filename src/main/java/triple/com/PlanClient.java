package triple.com;

import java.util.ArrayList;

/**
 * The PlanClient class extends the Plan class and represents a subscription plan for clients.
 * It adds functionality to manage subscribers and provides a method to view plan details.
 */
public class PlanClient extends Plan {

    private ArrayList<Client> subscribersClient; // List of clients subscribed to this plan

    /**
     * Constructs a new PlanClient with the specified name, type, price, and basic benefit.
     * Initializes the list of subscribers to an empty list.
     *
     * @param name the name of the plan (e.g., "Basic" or "Premium")
     * @param type the type of the plan (e.g., "Paid" or "Free")
     * @param price the price of the plan
     * @param basicBenefit a basic benefit included in the plan
     */
    public PlanClient(String name, String type, double price, String basicBenefit) {
        super(name, type, price, basicBenefit);
        this.subscribersClient = new ArrayList<Client>();
    }

    /**
     * Subscribes a client to this plan.
     *
     * @param client the client to be subscribed
     */
    public void subscribeClient(Client client) {
        subscribersClient.add(client);
    }

    /**
     * Cancels a client's subscription to this plan.
     *
     * @param client the client whose subscription will be canceled
     */
    public void cancelClientSubscription(Client client) {
        subscribersClient.remove(client);
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
        planDetails.append("Subscribers: ").append(subscribersClient.size()).append("\n");
        planDetails.append("Benefits: \n");

        // Loop through benefits and append each one
        for (String benefit : getBenefits()) {
            planDetails.append("- ").append(benefit).append("\n");
        }
        planDetails.append("--------------------------------------------------------------\n");

        return planDetails.toString();
    }
}
