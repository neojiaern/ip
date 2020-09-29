package duke.commands;

import duke.ui.Ui;
import duke.task.TaskList;

public abstract class Command {

    /**
     * Method to be implemented by child classes
     */
    public abstract CommandResult execute(TaskList tasks) ;

    public boolean isExit() {
        return false;
    }
}
