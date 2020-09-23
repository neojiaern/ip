package duke.command;

import duke.ui.Ui;
import duke.task.TaskList;

/**
 * Deletes a task from TaskList
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String INDENTATION = "    ";
    public static final String DELETE_EXAMPLE = (INDENTATION + "delete: Deletes a task from the list."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: INDEX_OF_TASK_TO_DELETE"
            + System.lineSeparator() + INDENTATION + "  " + "Example: delete 2");

    protected int deleteIndex;

    public DeleteCommand(int deleteIndex) {
        this.deleteIndex = deleteIndex;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.deleteTask(deleteIndex);
    }
}
