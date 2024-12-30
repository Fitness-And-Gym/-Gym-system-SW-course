package triple.com;

/**
 * Represents a client's progress towards a specific fitness goal.
 * The goal can be related to weight loss, muscle building, flexibility, etc.
 */
public class Progress {
    private String goalType; // Type of the goal, e.g., Weight Loss, Muscle Building, Flexibility
    private double startValue; // The starting value for the goal (e.g., initial weight or flexibility level)
    private double targetValue; // The target value the client aims to reach
    private double currentValue; // The current progress towards the goal

    /**
     * Constructs a new Progress object with the specified goal type, start value, and target value.
     *
     * @param goalType    The type of the goal (e.g., Weight Loss, Muscle Building).
     * @param startValue  The initial value of the client's progress.
     * @param targetValue The target value the client wants to achieve.
     */
    public Progress(String goalType, double startValue, double targetValue) {
        this.goalType = goalType;
        this.startValue = startValue;
        this.targetValue = targetValue;
        this.currentValue = startValue;
    }

    /**
     * Updates the current progress value for the goal.
     *
     * @param newValue The new value of the client's progress.
     */
    public void updateProgress(double newValue) {
        this.currentValue = newValue;
    }

    /**
     * Returns a summary of the client's progress, including the goal type, current value, target value,
     * and the percentage of progress made.
     *
     * @return A string summarizing the client's progress.
     */
    public String getProgressSummary() {
        double progressPercentage = ((currentValue - startValue) / (targetValue - startValue)) * 100;
        return String.format("Goal: %s, Current Value: %.2f, Target: %.2f, Progress: %.2f%%",
                goalType, currentValue, targetValue, progressPercentage);
    }

    /**
     * Calculates the percentage of progress made towards the target value.
     *
     * @return A string representing the progress percentage formatted as a percentage (e.g., "75.50%").
     */
    public String calcProgress() {
        double progressPercentage = ((currentValue - startValue) / (targetValue - startValue)) * 100;
        return String.format("%.2f%%", progressPercentage);
    }

    /**
     * Gets the type of the goal (e.g., Weight Loss, Muscle Building).
     *
     * @return The type of the goal.
     */
    public String getGoalType() {
        return goalType;
    }
}
