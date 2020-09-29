package duke.commands;

import duke.ui.Ui;
import duke.task.TaskList;

/**
 * Prints bye message.
 */
public class ByeCommand extends Command {

    public static final String COMMAND_WORD = "bye";
    public static final String INDENTATION = "    ";
    public static final String BYE_EXAMPLE = (INDENTATION + "bye: Exits the program."
            + System.lineSeparator() + INDENTATION + "  " + "Example: bye");

    @Override
    public CommandResult execute(TaskList tasks) {
        return new CommandResult(INDENTATION + "Bye. Hope to see you again soon!");
    }

    /**
     * Changes loop condition for user to exit program.
     *
     * @return value of isExit.
     */
    public boolean isExit() {
        return true;
    }
}
