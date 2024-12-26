package triple.com;

import java.util.ArrayList;




public class PlanClient extends Plan{

    private ArrayList<Client> subscribersClient;


    public PlanClient(String name, String type, double price, String basicBenefit) {
        super(name,type,price,basicBenefit);
        this.subscribersClient=new ArrayList<Client>();
    }



    public void subscribeClient(Client client){
        subscribersClient.add(client);
    }

    public void cancelClientSubscription(Client client){
        subscribersClient.remove(client);
    }


    // Method to print plan details
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
