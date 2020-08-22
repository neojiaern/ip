public class Task {
    private String taskName;
    private boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStatusIcon() {
        return (isDone ? "[✓]" : "[✗]"); //return tick or X symbols
    }

    public void markAsDone() {
        isDone = true;
    }
}
