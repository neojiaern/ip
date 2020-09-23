package duke.command;

import duke.task.TaskList;
import duke.ui.Ui;

public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    protected String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.findTaskByKeyword(keyword);
    }
}
