package duke.commands;

import duke.task.TaskList;
import duke.ui.Ui;

import java.time.LocalDate;

/**
 * Lists deadlines and events due on the date specified by user
 */
public class DueCommand extends Command {

    public static final String COMMAND_WORD = "due";
    public static final String INDENTATION = "    ";
    public static final String DUE_EXAMPLE = (INDENTATION + "due: Lists deadlines and events due."
            + System.lineSeparator() + INDENTATION + " " + "Parameters: DUE_DATE(YYYY-MM-DD)"
            + System.lineSeparator() + INDENTATION + " " + "Example: due 2020-10-02");

    protected LocalDate date;

    public DueCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.findDueTasks(date);
    }
}
