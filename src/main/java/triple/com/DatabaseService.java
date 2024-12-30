package triple.com;

import java.util.ArrayList;

import javax.xml.crypto.Data;
/**
 * The DatabaseService class serves as a middle layer
 * to let other classes interact with the real Database
 * DatabaseService is the only class with direct access to Database class
 */
public class DatabaseService {
    public static PlanClient getClientPlanByNumber(int number) {
        return Database.getClientPlanByNumber(number);
    }

    public static ArrayList<Program> getPrograms() {
        return Database.getPrograms();
    }

    public static void addDeclinedInstructors(Instructor instructor) {
        Database.addDeclinedInstructors(instructor);
    }

    public static void printInstructorsTable() {
        Database.printInstructorsTable();
    }

    public static void printDeclinedInstructorsTable() {
        Database.printDeclinedInstructorsTable();
    }


    public static void deleteProgram(Program program) {
        Database.deleteProgram(program);
    }

    public static ArrayList<Instructor> getInstructors() {
        return Database.getInstructors();
    }

    public static ArrayList<PlanClient> getPlansClient() {
        return Database.getPlansClient();
    }

    public static ArrayList<PlanInstructor> getPlansInstructors() {
        return Database.getPlansInstructors();
    }

    public static void addPlanToClient(PlanClient plan) {
        Database.addPlanToClient(plan);
    }

    public static void addPlanToInstructors(PlanInstructor plan) {
        Database.addPlanToInstructors(plan);
    }

    public static void addFeed(Feedback feedback) {
        Database.addFeed(feedback);
    }

    public static ArrayList<Feedback> getFeeds() {
        return Database.getFeeds();
    }

    public static ArrayList<Client> getAllClients() {
        return Database.getAllClients();
    }

    public static void addClient(Client client) {
        Database.addClient(client);
    }

    public static Client getClientByName(String clientName) {
        return Database.getClientByName(clientName);
    }

    public static Instructor getInstructorByName(String instructorName) {
        return Database.getInstructorByName(instructorName);
    }

    public static Client getClientById(String clientId) {
        return Database.getClientById(clientId);
    }

    public static ArrayList<Instructor> getAllRequests() {
        return Database.getAllRequests();
    }

    public static void sendRequest(Instructor req) {
        Database.sendRequest(req);
    }

    public static Instructor processRequest() {
        return Database.processRequest();
    }

    public static Instructor getInstructorRequestById(String instructorId) {
        return Database.getInstructorRequestById(instructorId);
    }

    public static Instructor getInstructorById(String instructorId) {
        return Database.getInstructorById(instructorId);
    }

    public static void addInstructor(Instructor newInstructor) {
        Database.addInstructor(newInstructor);
    }

    public static ArrayList<Program> getPopularPrograms() {
        return Database.getPopularPrograms();
    }

    public static Program getProgramById(String programId) {
        return Database.getProgramById(programId);
    }

    public static void addProgram(Program program) {
        Database.addProgram(program);
    }

    public static Article searchArticleById(String id) {
        return Database.searchArticleById(id);
    }



    public static void addArticle(Article article) {
        Database.addArticle(article);
    }

    public static void printArticles() {
        Database.printArticles();
    }

    public static void printArticlesRequests() {
        Database.printArticlesRequests();
    }

    public static void printClientsTable() {
        Database.printClientsTable();
    }

    public static void createMockData() {
        Database.createMockData();
    }

    public static void populateMockPrograms() {
        Database.populateMockPrograms();
    }

    public static void populateMockCompletedPrograms() {
        Database.populateMockCompletedPrograms();
    }

    public static void addEnrollmentsToProgram(Program program) {
        Database.addEnrollmentsToProgram(program);
    }

    public static ArrayList<Program> getCompletedPrograms() {
        return Database.getCompletedPrograms();
    }

    // added for solving the public fields in Database
    public static PlanClient getBasicPlanClient() {
        return Database.getBasicPlanClient();
    }

    public static PlanInstructor getBasicPlanInstructor() {
        return Database.getBasicPlanInstructor();
    }

    public static ArrayList<Article> getArticleRequests() {
        return Database.getArticleRequests();
    }





    public static void removeArticleRequest(Article article) {
        Database.removeArticleRequest(article);
    }



    public static void removeProgram(Program program) {
        Database.removeProgram(program);
    }

    public static void addCompletedProgram(Program program) {
        Database.addCompletedProgram(program);
    }

    public static void addArticleRequest(Article article) {
        Database.addArticleRequest(article);
    }

    // public static Article getArticleById(String articleId) {
    //     return Database.getArticleById(articleId);
    // }

    public static Article getArticleRequestById(String articleId) {
        return Database.getArticleRequestById(articleId);
    }

    public static Article getLastArticleRequest() {
        return Database.getLastArticleRequest();
    }

    // mock
    public static void populateMockArticles() {
        Database.populateMockArticles();
    }

    public static void populateMockFeedbacks() {
        Database.populateMockFeedbacks();
    }

    public static void populateMockPlans() {
        Database.populateMockPlans();
    }

    public static void populateMockProgramsWithGoals() {
        Database.populateMockProgramsWithGoals();
    }

}
