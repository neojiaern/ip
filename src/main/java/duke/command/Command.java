package duke.command;

import duke.ui.Ui;
import duke.task.TaskList;

public abstract class Command {

    /**
     * Method to be implemented by child classes
     */
    public abstract CommandResult execute(TaskList tasks, Ui ui) ;

    public boolean isExit() {
        return false;
    }
}
