package triple.com;

import java.util.ArrayList;

/**
 * The Plan class represents a subscription plan for clients or instructors.
 * It contains the name, type (paid or free), price, and associated benefits of the plan.
 */
public class Plan {
    private String name; // Name of the plan (e.g., Basic, Premium)
    private String type; // Type of plan (e.g., Paid, Free)
    private double price; // Price of the plan
    private ArrayList<String> benefits; // List of benefits associated with the plan

    /**
     * Constructs a new plan with the specified name, type, price, and a basic benefit.
     *
     * @param name the name of the plan (e.g., "Basic" or "Premium")
     * @param type the type of the plan (e.g., "Paid" or "Free")
     * @param price the price of the plan
     * @param basicBenefit a basic benefit included in the plan
     */
    public Plan(String name, String type, double price, String basicBenefit) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.benefits = new ArrayList<String>();
        this.benefits.add(basicBenefit);
    }

    /**
     * Returns the name of the plan.
     *
     * @return the name of the plan
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of the plan.
     *
     * @return the type of the plan (e.g., "Paid", "Free")
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the price of the plan.
     *
     * @return the price of the plan
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the list of benefits associated with the plan.
     *
     * @return a list of benefits
     */
    public ArrayList<String> getBenefits() {
        return benefits;
    }

    /**
     * Sets the list of benefits associated with the plan.
     *
     * @param benefits a list of benefits
     */
    public void setBenefits(ArrayList<String> benefits) {
        this.benefits = benefits;
    }

    /**
     * Adds a new benefit to the plan.
     *
     * @param benefit a new benefit to be added
     */
    public void addBenefit(String benefit) {
        this.benefits.add(benefit);
    }

    /**
     * Removes a benefit from the plan.
     *
     * @param benefit the benefit to be removed
     */
    public void removeBenefit(String benefit) {
        this.benefits.remove(benefit);
    }


    // /**
    //  * Prints the details of the plan.
    //  */
    // public void printPlanDetails() {
    //     System.out.println("Plan Name: " + name);
    //     System.out.println("Plan Type: " + type);
    //     System.out.println("Price: $" + price);
    //     System.out.println("Benefits: ");
    //     for (String benefit : benefits) {
    //         System.out.println("- " + benefit);
    //     }
    //     System.out.println("------------------------------------");
    // }
}
