package duke.task;

import java.time.LocalDate;

public class Task {

    public static final String TICK = "[\u2713]";
    public static final String CROSS = "[\u2718]";

    protected String taskName;
    protected boolean isDone;
    public boolean hasDueDate;
    protected LocalDate date;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? TICK : CROSS); //return tick or X symbols
    }

    public void markAsDone() {
        isDone = true;
    }

    public String printToFile() {
        return null;
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + taskName;
    }
}
