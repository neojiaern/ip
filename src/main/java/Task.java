public class Task {

    public static final String TICK = "[✓]";
    public static final String CROSS = "[✗]";

    private String taskName;
    private boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean getStatus() {
        return isDone;
    }

    public String getStatusIcon() {
        return (isDone ? TICK : CROSS); //return tick or X symbols
    }

    public void markAsDone() {
        isDone = true;
    }
}
