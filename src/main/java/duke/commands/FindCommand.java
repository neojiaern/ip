package duke.commands;

import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Lists tasks related to keyword.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String INDENTATION = "    ";
    public static final String FIND_EXAMPLE = (INDENTATION + "find: Finds tasks containing related keyword."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: KEYWORD"
            + System.lineSeparator() + INDENTATION + "  " + "Example: find book");

    protected String keyword;

    /**
     * Stores keyword.
     *
     * @param keyword related to a task specified by user.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.findTaskByKeyword(keyword);
    }
}
