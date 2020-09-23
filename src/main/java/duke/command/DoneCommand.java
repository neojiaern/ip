package duke.command;

import duke.ui.Ui;
import duke.task.TaskList;

/**
 * Marks a task as done
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    protected int doneIndex;

    public DoneCommand(int doneIndex) {
        this.doneIndex = doneIndex;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.doneTask(doneIndex);
    }
}
