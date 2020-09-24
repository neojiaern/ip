package duke.commands;

import duke.ui.Ui;
import duke.task.TaskList;

/**
 * Lists tasks in TaskList.
 */
public class ListCommand extends Command {

    public static final String INDENTATION = "    ";

    public static final String LIST_EXAMPLE = (INDENTATION + "list: Display all tasks entered by user."
            + System.lineSeparator() + INDENTATION + "  " + "Example: list");
    public static final String COMMAND_WORD = "list";

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.listTasks();
    }
}
