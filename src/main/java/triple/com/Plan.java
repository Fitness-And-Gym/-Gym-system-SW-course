package triple.com;

import java.util.ArrayList;

import triple.com.Client;
import triple.com.Instructor;

public class Plan {
    private String name; // Name of the plan ( Basic, Premium)
    private String type; // Type of plan (Paid, Free)
    private double price; // Price of the plan
    private ArrayList<String> benefits; // List of benefits associated with the plan

    // Constructor to initialize the Plan
    public Plan(String name, String type, double price, String basicBenefit) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.benefits = new ArrayList<String>();
        this.benefits.add(basicBenefit);
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for type
    public String getType() {
        return type;
    }

    // Getter for price
    public double getPrice() {
        return price;
    }

    // Getter for benefits
    public ArrayList<String> getBenefits() {
        return benefits;
    }

    // Setter for benefits
    public void setBenefits(ArrayList<String> benefits) {
        this.benefits = benefits;
    }

    // Method to add a benefit
    public void addBenefit(String benefit) {
        this.benefits.add(benefit);
    }

    // Method to remove a benefit
    public void removeBenefit(String benefit) {
        this.benefits.remove(benefit);
    }

    // Method to print plan details
    // public void printPlanDetails() {
    // System.out.println("Plan Name: " + name);
    // System.out.println("Plan Type: " + type);
    // System.out.println("Price: $" + price);
    // System.out.println("Benefits: ");
    // for (String benefit : benefits) {
    // System.out.println("- " + benefit);
    // }
    // System.out.println("------------------------------------");

    // }

}
