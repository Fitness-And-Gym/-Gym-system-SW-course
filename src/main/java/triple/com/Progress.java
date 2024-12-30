package triple.com;

public class Progress {
    private String goalType; // Weight Loss, Muscle Building, Flexibility....
    private double startValue;
    private double targetValue;
    private double currentValue;

    public Progress(String goalType, double startValue, double targetValue) {
        this.goalType = goalType;
        this.startValue = startValue;
        this.targetValue = targetValue;
        this.currentValue = startValue;
    }

    public void updateProgress(double newValue) {
        this.currentValue = newValue;
    }

    public String getProgressSummary() {
        double progressPercentage = ((currentValue - startValue) / (targetValue - startValue)) * 100;
        return String.format("Goal: %s, Current Value: %.2f, Target: %.2f, Progress: %.2f%%",
                goalType, currentValue, targetValue, progressPercentage);
    }

    public String calcProgress() {
        double progressPercentage = ((currentValue - startValue) / (targetValue - startValue)) * 100;
        return String.format("%.2f%%", progressPercentage);
    }

    public String getGoalType() {
        return goalType;
    }

}
