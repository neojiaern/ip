package duke.command;

import duke.ui.Ui;
import duke.task.TaskList;

/**
 * Adds a todo, deadline or event task to TaskList
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD_T = "todo";
    public static final String COMMAND_WORD_D = "deadline";
    public static final String COMMAND_WORD_E = "event";

    protected String taskType;
    protected String taskDescription;

    public AddCommand(String taskType, String taskDescription) {
        this.taskType = taskType;
        this.taskDescription = taskDescription;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.addTask(taskType, taskDescription);
    }

}
