package duke.command;

import duke.task.TaskList;
import duke.ui.Ui;

import java.time.LocalDate;

/**
 * Lists deadlines and events due on the date specified by user
 */
public class DueCommand extends Command {

    public static final String COMMAND_WORD = "due";

    protected LocalDate date;

    public DueCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.findDueTasks(date);
    }
}
