package duke.command;

import duke.ui.Ui;
import duke.task.TaskList;

/**
 * Deletes a task from TaskList
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    protected int deleteIndex;

    public DeleteCommand(int deleteIndex) {
        this.deleteIndex = deleteIndex;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.deleteTask(deleteIndex);
    }
}
