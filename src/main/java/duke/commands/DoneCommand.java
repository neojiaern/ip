package duke.commands;

import duke.ui.Ui;
import duke.task.TaskList;

/**
 * Marks a task as done.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String INDENTATION = "    ";
    public static final String DONE_EXAMPLE = (INDENTATION + "done: Marks a completed task as done."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: INDEX_OF_COMPLETED_TASK"
            + System.lineSeparator() + INDENTATION + "  " + "Example: done 2");

    protected int doneIndex;

    /**
     * Stores index of task that is completed.
     *
     * @param doneIndex index specified by user.
     */
    public DoneCommand(int doneIndex) {
        this.doneIndex = doneIndex;
    }

    @Override
    public CommandResult execute(TaskList tasks) {
        return tasks.doneTask(doneIndex);
    }
}
