package triple.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Program class represents a training program offered by an instructor.
 * It manages the enrollment of clients, tracks attendance, and generates
 * reports
 * on program performance such as revenue and attendance.
 */
public class Program {
    private static int idCounter = 1;
    private int enrollments;
    private Instructor instructor;
    private Set<Client> enrolledClients;
    private final String programId;
    public String title;
    private String difficulty; // Beginner, Intermediate, Advanced
    private int duration; // in weeks
    public int fees;
    private Map<Client, List<Boolean>> attendanceRecords; // assuming one session per week
    private List<Progress> programGoals; // The Ideal clients that are going to enroll and the outcome
    private boolean completed;

    public int getFees() {
        return fees;
    }

    public Map<Client, List<Boolean>> getAttendanceRecords() {
        return attendanceRecords;
    }

    /**
     * Constructs a new Program with the specified details.
     *
     * @param instructor the instructor offering the program
     * @param fees       the cost of the program
     * @param title      the title of the program
     * @param duration   the duration of the program in weeks
     * @param difficulty the difficulty level of the program
     * @throws IllegalArgumentException if the instructor is null
     * @throws IllegalStateException    if the instructor is not authorized to
     *                                  create a program
     */
    public Program(Instructor instructor, int fees, String title, int duration, String difficulty) {
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be null.");
        }

        if (!instructor.getStatus().equals("valid")) {
            throw new IllegalStateException("Instructor is Invalid");
        }

        this.programId = "P" + idCounter++;
        this.enrollments = 0;
        this.instructor = instructor;
        this.enrolledClients = new HashSet<>();
        this.fees = fees;
        this.title = title;
        this.duration = duration;
        this.difficulty = difficulty;
        this.attendanceRecords = new HashMap<>();
        this.programGoals = new ArrayList<>();
        this.completed = false;
        instructor.addProgram(this);
        DatabaseService.addProgram(this);
    }

    /**
     * Updates the title of the program.
     *
     * @param title the new title for the program
     */
    public void updateTitle(String title) {
        this.title = title;
    }

    /**
     * Updates the fees of the program.
     *
     * @param fees the new fees for the program
     */
    public void updateFees(int fees) {
        this.fees = fees;
    }

    /**
     * Marks the program as completed.
     */
    public void markAsCompleted() {
        this.completed = true;
        instructor.deleteMyProgram(programId);
        DatabaseService.addCompletedProgram(this);
    }

    /**
     * Returns the list of program goals.
     *
     * @return a list of progress objects representing the program goals
     */
    public List<Progress> getProgramGoals() {
        return programGoals;
    }

    /**
     * Checks if the program has been completed.
     *
     * @return true if the program is completed, false otherwise
     */
    public boolean isCompleted() {
        return this.completed;
    }

    /**
     * Adds a goal to the program.
     *
     * @param progress the goal to add to the program
     */
    public void addProgramGoal(Progress progress) {
        programGoals.add(progress);
    }

    /**
     * Enrolls a client into the program.
     *
     * @param client the client to enroll
     */
    public void enrollClient(Client client) {
        if (!enrolledClients.contains(client)) {
            enrolledClients.add(client);
            enrollments++;
            client.enrollInProgram(this.programId);
            for (Progress progress : programGoals) {
                client.setGoalForAProgram(progress);
            }
            List<Boolean> attendance = new ArrayList<>();
            for (int i = 0; i < duration; i++) {
                attendance.add(false); // Default to "Absent" for all weeks
            }
            attendanceRecords.put(client, attendance);

            System.out.println(client.getClientName() + " has been enrolled and attendance records initialized.");
        } else {
            System.out.println(client.getClientName() + " is already enrolled.");
        }
    }

    /**
     * Returns the set of clients currently enrolled in the program.
     *
     * @return a set of clients enrolled in the program
     */
    public Set<Client> getEnrolledClients() {
        return enrolledClients;
    }

    /**
     * Returns the popularity of the program, based on the number of enrollments.
     *
     * @return the number of clients enrolled in the program
     */
    public int popularity() {
        return enrollments;
    }

    /**
     * Returns the instructor of the program.
     *
     * @return the instructor of the program
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Returns the program ID.
     *
     * @return the unique ID of the program
     */
    public String getProgramId() {
        return programId;
    }

    /**
     * Returns the title of the program.
     *
     * @return the title of the program
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the program.
     *
     * @param name the new title for the program
     */
    public void setTitle(String name) {
        this.title = name;
    }

    /**
     * Returns the duration of the program in weeks.
     *
     * @return the duration of the program
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the difficulty of the program.
     *
     * @return the difficulty level of the program
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the number of enrollments for the program.
     *
     * @param enrollments the new number of enrollments
     */
    public void setEnrollments(int enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Returns the number of enrollments in the program.
     *
     * @return the number of clients enrolled in the program
     */
    public int getEnrollments() {
        return enrollments;
    }

    /**
     * Displays the details of all enrollments in the program.
     */
    public void viewAllEnrollments() {
        if (enrollments == 0) {
            System.out.println("No enrollments found for the program: " + title);
            return;
        }

        System.out.println("Enrollments for the program: " + title);
        for (Client enrollment : enrolledClients) {
            System.out.println("Student Name: " + enrollment.getClientName() +
                    ", Enrollment ID: " + enrollment.getClientId());
        }
    }

    /**
     * Marks attendance for a client in a specific week.
     *
     * @param client   the client whose attendance is being marked
     * @param week     the week for which attendance is being marked
     * @param attended true if the client attended, false otherwise
     */
    public void markAttendance(Client client, int week, boolean attended) {
        if (!enrolledClients.contains(client)) {
            System.out.println("Client is not enrolled in this program.");
            return;
        }

        if (week < 1 || week > duration) {
            System.out.println("Invalid week number. Please provide a value between 1 and " + duration + ".");
            return;
        }

        List<Boolean> attendance = attendanceRecords.get(client);
        attendance.set(week - 1, attended);
        System.out.println("Attendance for " + client.getClientName() + " in week " + week + " has been marked as "
                + (attended ? "Present" : "Absent") + ".");
    }

    /**
     * Returns the attendance records of a client.
     *
     * @param client the client whose attendance records are being retrieved
     * @return a list of booleans representing the attendance in each week
     */
    public List<Boolean> getAttendance(Client client) {
        if (!enrolledClients.contains(client)) {
            System.out.println("Client is not enrolled in this program.");
            return Collections.emptyList();
        }
        return attendanceRecords.get(client);
    }

    /**
     * Displays the attendance details for all clients enrolled in the program.
     */
    public void viewAttendance() {
        if (enrolledClients.isEmpty()) {
            System.out.println("No clients enrolled in the program.");
            return;
        }

        System.out.println("Attendance for the program: " + title);
        for (Client client : enrolledClients) {
            System.out.println("Client: " + client.getClientName());
            List<Boolean> attendance = attendanceRecords.get(client);
            for (int i = 0; i < attendance.size(); i++) {
                System.out.println("  Week " + (i + 1) + ": " + (attendance.get(i) ? "Present" : "Absent"));
            }
        }
    }

    /**
     * Calculates the overall attendance percentage for the program.
     *
     * @return the attendance percentage for the entire program
     */
    public double calculateProgramAttendance() {
        int totalAttendedSessions = 0;
        int totalSessions = enrolledClients.size() * duration;

        for (Client client : enrolledClients) {
            List<Boolean> attendance = attendanceRecords.get(client);
            if (attendance != null) {
                for (Boolean attended : attendance) {
                    if (attended) {
                        totalAttendedSessions++;
                    }
                }
            }
        }

        double programAttendancePercentage = (totalAttendedSessions / (double) totalSessions) * 100;
        programAttendancePercentage = Math.round(programAttendancePercentage * 100.0) / 100.0;
        return programAttendancePercentage;
    }

    /**
     * Generates a report for the program, including details on revenue, attendance,
     * and client progress.
     *
     * @return a string representing the program's detailed report
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();

        double revenue = enrollments * fees;
        double attendancePercent = calculateProgramAttendance();

        report.append("----- Program Report -----\n")
                .append("Program: ").append(title).append("\n")
                .append(" - Duration(weeks): ").append(duration).append("\n")
                .append(" - Program ID: ").append(programId).append("\n")
                .append(" - Instructor: ").append(instructor.getName()).append("\n")
                .append(" - Enrollments: ").append(enrollments).append("\n")
                .append(" - Revenue: $").append(revenue).append("\n")
                .append(" - Attendance: %").append(attendancePercent).append("\n")
                .append("Client Details and Attendance:\n");

        if (enrolledClients.isEmpty()) {
            report.append(" - No clients enrolled.\n");
        } else {
            for (Client client : enrolledClients) {
                report.append(" - Client Name: ").append(client.getClientName()).append("\n")
                        .append("   Client ID: ").append(client.getClientId()).append("\n");

                List<Boolean> attendance = attendanceRecords.get(client);
                if (attendance != null) {
                    int attendedSessions = 0;
                    for (Boolean attended : attendance) {
                        if (attended) {
                            attendedSessions++;
                        }
                    }
                    double attendancePercentage = (attendedSessions / (double) duration) * 100;

                    report.append("   Attendance: ").append(attendedSessions).append(" out of ").append(duration)
                            .append(" sessions attended (")
                            .append(String.format("%.2f", attendancePercentage)).append("%).\n");
                } else {
                    report.append("   Attendance: No attendance records found.\n");
                }

            }
        }

        report.append("Program Goals:\n");
        if (programGoals.isEmpty()) {
            report.append(" - No program goals defined.\n");
        } else {
            for (Progress progress : programGoals) {
                report.append(" - ").append(progress.getProgressSummary()).append("\n");
            }
        }

        report.append("-------------------------");

        return report.toString();
    }

    /**
     * Generates a report for the program, suitable for admin use.
     *
     * @return a string representing a summary report for the program
     */
    public String generateReportForAdmin() {
        StringBuilder report = new StringBuilder();

        double revenue = enrollments * fees;
        double attendancePercent = calculateProgramAttendance();

        report.append("----- Program Report -----\n")
                .append("Program: ").append(title).append(" -" + difficulty).append("\n")
                .append(" - Program ID: ").append(programId).append("\n")
                .append(" - Instructor: ").append(instructor.getName()).append("\n")
                .append(" - Enrollments: ").append(enrollments).append("\n")
                .append(" - Revenue: $").append(revenue).append("\n")
                .append(" - Attendance: %").append(attendancePercent).append("\n");

        report.append("-------------------------");

        return report.toString();
    }

    public void insertAttendance(Map<Client, Boolean> attendance) {
        for (Map.Entry<Client, Boolean> entry : attendance.entrySet()) {
            Client client = entry.getKey();
            Boolean attended = entry.getValue();

            attendanceRecords.get(client).add(attended);

        }

    }

}
