package duke.commands;

/**
 * Stores result of a command after execution.
 */
public class CommandResult {

    public String result;

    public CommandResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return result;
    }
}
