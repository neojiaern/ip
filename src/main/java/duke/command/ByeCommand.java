package duke.command;

import duke.ui.Ui;
import duke.task.TaskList;

/**
 * Prints bye message and allows user to exit program
 */
public class ByeCommand extends Command {

    public static final String COMMAND_WORD = "bye";
    public static final String INDENTATION = "    ";

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return new CommandResult(INDENTATION + "Bye. Hope to see you again soon!");
    }

    /**
     * Changes loop condition for user to exit program
     *
     * @return value of isExit.
     */
    public boolean isExit() {
        return true;
    }
}