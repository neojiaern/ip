public class Event extends Task {

    protected String at;

    public Event (String taskName, String at) {
        super(taskName);
        this.at = at;
    }

    @Override
    public String toString() {
        return getStatusColor() + "[E]" + super.toString() + " (at: " + at + ")" + ANSI_RESET;
    }
}
