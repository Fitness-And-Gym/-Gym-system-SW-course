package triple.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Program {
    // TO DO : add videos and images array for program added by instructor
    // TO DO : have a schedule for how many session in a week and in which day and
    // time
    private static int idCounter = 1;
    private int enrollments;
    private Instructor instructor;
    private Set<Client> enrolledClients;
    private final String programId;
    public String title;
    private String difficulty;// Beginner Intermediate Advanced
    private int duration;// in weeks
    public int fees;
    private Map<Client, List<Boolean>> attendanceRecords;// assuming one session per week
    private List<Progress> programGoals;// The Ideal clients that are going to enroll and the outcome
    private boolean completed;

    public Program(Instructor instructor, int fees, String title, int duration, String difficulty) {
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be null.");
        }

        if (!instructor.getStatus().equals("valid")) {
            throw new IllegalStateException("Instructor is not authorized to create a program.");
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
        Database.addProgram(this);

        // system.out.println("Program created successfully with ID: " + this.id);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateFees(int fees) {
        this.fees = fees;
    }

    public void markAsCompleted() {
        this.completed = true;
        Database.getPrograms().remove(this);
        Database.CompletedPrograms.add(this);
    }

    public List<Progress> getProgramGoals() {
        return programGoals;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void addProgramGoal(Progress progress) {
        programGoals.add(progress);
    }

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

    public Set<Client> getEnrolledClients() {
        return enrolledClients;
    }

    public int popularity() {
        return enrollments;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public String getProgramId() {
        return programId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getDuration() {
        return duration;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setEnrollments(int enrollments) {
        this.enrollments = enrollments;
    }

    public int getEnrollments() {
        return enrollments;
    }

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

    // Get Attendance of the client //Not Used Yet May be deleted
    public List<Boolean> getAttendance(Client client) {
        if (!enrolledClients.contains(client)) {
            System.out.println("Client is not enrolled in this program.");
            return Collections.emptyList();
        }
        return attendanceRecords.get(client);
    }

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

    public String generateReport() {
        StringBuilder report = new StringBuilder();

        double revenue = enrollments * fees;
        double attendancePercent = calculateProgramAttendance();

        // Calculate overall goal progress (average progress of all clients)
        // double overallProgress = calculateOverallGoalProgress();

        report.append("----- Program Report -----\n")
                .append("Program: ").append(title).append("\n")
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

                // Calculate attendance statistics
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

        report.append("-------------------------");

        return report.toString();
    }

    public String generateReportForAdmin() {
        StringBuilder report = new StringBuilder();

        double revenue = enrollments * fees;
        double attendancePercent = calculateProgramAttendance();

        // Calculate overall goal progress (average progress of all clients)
        // double overallProgress = calculateOverallGoalProgress();

        report.append("----- Program Report -----\n")
                .append("Program: ").append(title).append("\n")
                .append(" - Program ID: ").append(programId).append("\n")
                .append(" - Instructor: ").append(instructor.getName()).append("\n")
                .append(" - Enrollments: ").append(enrollments).append("\n")
                .append(" - Revenue: $").append(revenue).append("\n")
                .append(" - Attendance: %").append(attendancePercent).append("\n");

        report.append("-------------------------");

        return report.toString();
    }

}
