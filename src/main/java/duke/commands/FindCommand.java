package duke.commands;

import duke.task.TaskList;
import duke.ui.Ui;

import java.util.List;

/**
 * Lists tasks related to keyword.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String INDENTATION = "    ";
    public static final String FIND_EXAMPLE = (INDENTATION + "find: Finds tasks containing related keyword."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: KEYWORD [MORE_KEYWORDS]"
            + System.lineSeparator() + INDENTATION + "  " + "Example: find book");

    protected List<String> keywords;

    /**
     * Stores keywords.
     *
     * @param keywords related to a task specified by user.
     */
    public FindCommand(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.findTaskByKeyword(keywords);
    }
}
