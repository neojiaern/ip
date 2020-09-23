package duke.commands;

import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Stores error message for incorrect command
 */
public class IncorrectCommand extends Command {

    protected String message;

    public IncorrectCommand(String message) {
        this.message = message;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return new CommandResult(message);
    }
}
