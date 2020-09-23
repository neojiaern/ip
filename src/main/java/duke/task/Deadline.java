package duke.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    protected String by;
    protected LocalTime time;

    public Deadline (String taskName, String by) {
        super(taskName);
        this.by = by;
        hasDueDate = true;
        parseDateTime();
    }

    public void parseDateTime() throws DateTimeParseException, ArrayIndexOutOfBoundsException {
        String[] dateTime = by.split(" ");
        date = LocalDate.parse(dateTime[0]);
        time = LocalTime.parse(dateTime[1]);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " " + time + ")";
    }
}
