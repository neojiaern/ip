package duke.commands;

import duke.ui.Ui;
import duke.task.TaskList;

/**
 * Adds a todo, deadline or event task to TaskList.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD_T = "todo";
    public static final String COMMAND_WORD_D = "deadline";
    public static final String COMMAND_WORD_E = "event";

    public static final String INDENTATION = "    ";
    public static final String TODO_EXAMPLE = (INDENTATION + "todo: Adds a todo task."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: TASK_DESCRIPTION"
            + System.lineSeparator() + INDENTATION + "  " + "Example: todo CS2113T revision");
    public static final String DEADLINE_EXAMPLE = (INDENTATION + "deadline: Adds a deadline task."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: TASK_DESCRIPTION /by "
            + "DATE(YYYY-MM-DD) TIME(HH:mm)" + System.lineSeparator() + INDENTATION + "  "
            + "Example: deadline CS2113T assignment /by 2020-10-02 23:59");
    public static final String EVENT_EXAMPLE = (INDENTATION + "event: Adds an event task."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: TASK_DESCRIPTION /at "
            + "DATE(YYYY-MM-DD) TIME(HH:mm)" + System.lineSeparator() + INDENTATION + "  "
            + "Example: event project meeting /at 2020-10-02 17:00");

    protected String taskType;
    protected String taskDescription;

    /**
     * Constructor for creating AddCommand
     *
     * @param taskType todo, deadline or event task.
     * @param taskDescription task details.
     */
    public AddCommand(String taskType, String taskDescription) {
        this.taskType = taskType;
        this.taskDescription = taskDescription;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui) {
        return tasks.addTask(taskType, taskDescription);
    }

}
