package duke.command;

import duke.ui.Ui;
import duke.task.TaskList;

/**
 * Lists tasks in TaskList
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.listTasks();
    }
}
