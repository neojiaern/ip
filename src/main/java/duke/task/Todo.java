package duke.task;

public class Todo extends Task {

    public Todo (String taskName) {
        super(taskName);
        hasDueDate = false;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
