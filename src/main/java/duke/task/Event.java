package duke.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {

    protected String at;
    protected LocalTime time;

    public Event (String taskName, String at) {
        super(taskName);
        this.at = at;
        hasDueDate = true;
        parseDateTime();
    }

    public void parseDateTime() throws DateTimeParseException, ArrayIndexOutOfBoundsException {
        String[] dateTime = at.split(" ");
        date = LocalDate.parse(dateTime[0]);
        time = LocalTime.parse(dateTime[1]);

    }

    public String printToFile() {
        return "[E]" + super.toString() + " (at: " + date + " " + time + ")";
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " " + time + ")";
    }
}
