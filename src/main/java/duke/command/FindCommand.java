package duke.command;

import duke.task.TaskList;
import duke.ui.Ui;

public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String INDENTATION = "    ";
    public static final String FIND_EXAMPLE = (INDENTATION + "find: finds tasks containing related keyword."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: KEYWORD"
            + System.lineSeparator() + INDENTATION + "  " + "Example: find book");

    protected String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.findTaskByKeyword(keyword);
    }
}
