public class Todo extends Task {

    public Todo (String taskName) {
        super(taskName);
    }

    public String toString() {
        return getStatusColor() + "[T]" + super.toString() + ANSI_RESET;
    }
}
