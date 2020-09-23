package duke.parser;

import duke.command.AddCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.IncorrectCommand;
import duke.command.ListCommand;

public class Parser {

    public static final String INDENTATION = "    ";
    public static final String LIST_EXAMPLE = (INDENTATION + "list: Display all tasks entered by user."
            + System.lineSeparator() + INDENTATION + "  " + "Example: list");
    public static final String TODO_EXAMPLE = (INDENTATION + "todo: Adds a todo task."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: TASK_DESCRIPTION"
            + System.lineSeparator() + INDENTATION + "  " + "Example: todo study");
    public static final String DEADLINE_EXAMPLE = (INDENTATION + "deadline: Adds a deadline task."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: TASK_DESCRIPTION /by TIME"
            + System.lineSeparator() + INDENTATION + "  " + "Example: deadline CS2113T assignment /by Monday");
    public static final String EVENT_EXAMPLE = (INDENTATION + "event: Adds an event task."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: TASK_DESCRIPTION /at TIME")
            + System.lineSeparator() + INDENTATION + "  " + "Example: event project meeting /at Tuesday 2pm";
    public static final String DELETE_EXAMPLE = (INDENTATION + "delete: Deletes a task from the list."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: INDEX_OF_TASK_TO_DELETE"
            + System.lineSeparator() + INDENTATION + "  " + "Example: delete 2");
    public static final String DONE_EXAMPLE = (INDENTATION + "done: Marks a completed task as done."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: INDEX_OF_COMPLETED_TASK"
            + System.lineSeparator() + INDENTATION + "  " + "Example: done 2");
    public static final String FIND_EXAMPLE = (INDENTATION + "find: finds tasks containing related keyword."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: KEYWORD"
            + System.lineSeparator() + INDENTATION + "  " + "Example: find book");
    public static final String BYE_EXAMPLE = (INDENTATION + "bye: Exits the program."
            + System.lineSeparator() + INDENTATION + "  " + "Example: bye");

    /**
     * Calls the respective methods for the different commands: list, done, and delete task
     *
     * @param userInput
     * @return Command based on its type.
     */
    public Command parse(String userInput) {
        String[] inputParts = userInput.split(" ", 2);
        String command = inputParts[0].toLowerCase();
        switch (command) {
        case AddCommand.COMMAND_WORD_T:
        case AddCommand.COMMAND_WORD_D:
        case AddCommand.COMMAND_WORD_E:
            return processAddCmd(inputParts);
        case ListCommand.COMMAND_WORD:
            return processListCmd(inputParts);
        case DoneCommand.COMMAND_WORD:
            return processDoneCmd(inputParts);
        case DeleteCommand.COMMAND_WORD:
            return processDelCmd(inputParts);
        case FindCommand.COMMAND_WORD:
            return processFindCmd(inputParts);
        case ByeCommand.COMMAND_WORD:
            return new ByeCommand();
        default:
            return new IncorrectCommand(INDENTATION + "Oh no! This command is invalid, "
                    + "please try again.\n" + LIST_EXAMPLE + System.lineSeparator() + TODO_EXAMPLE
                    + System.lineSeparator() + DEADLINE_EXAMPLE + System.lineSeparator()
                    + EVENT_EXAMPLE + System.lineSeparator() + DELETE_EXAMPLE
                    + System.lineSeparator() + DONE_EXAMPLE + System.lineSeparator() + BYE_EXAMPLE);
        }
    }

    /**
     * Process adding of todo, deadline and event tasks
     *
     * @param inputParts String array containing type of task and it's description.
     * @return AddCommand containing type of task and its description or IncorrectCommand if invalid.
     */
    public Command processAddCmd(String[] inputParts) {
        String taskType = inputParts[0].toLowerCase();
        try {
            String taskDescription = inputParts[1];
            return new AddCommand(taskType, taskDescription);
        } catch (ArrayIndexOutOfBoundsException e) {
            String message = INDENTATION + "Oh no! The description of " + taskType + " cannot be empty.\n";
            switch (taskType) {
            case AddCommand.COMMAND_WORD_T:
                message += TODO_EXAMPLE;
                break;
            case AddCommand.COMMAND_WORD_D:
                message += DEADLINE_EXAMPLE;
                break;
            case AddCommand.COMMAND_WORD_E:
                message += EVENT_EXAMPLE;
                break;
            default:
                break;
            }
            return new IncorrectCommand(message);
        }
    }

    /**
     * Process delete command
     *
     * @param inputParts String array containing delete command and index of task.
     * @return DeleteCommand containing index of task to delete or IncorrectCommand if invalid.
     */
    public Command processDelCmd(String[] inputParts) {
        try {
            String deleteDescription = inputParts[1];
            int deleteIndex = Integer.parseInt(deleteDescription);
            return new DeleteCommand(deleteIndex);
        } catch (ArrayIndexOutOfBoundsException e) {
            String message = INDENTATION + "Oh no! The index for a task cannot be missing.\n"
                    + DELETE_EXAMPLE;
            return new IncorrectCommand(message);
        } catch (NumberFormatException e) {
            String message = INDENTATION + "Oh no! The index for a task to delete must be an integer.\n"
                    + DELETE_EXAMPLE;
            return new IncorrectCommand(message);
        }
    }

    /**
     * Process done command
     *
     * @param inputParts String array containing done command and index of task.
     * @return DoneCommand containing index of task done or IncorrectCommand if invalid.
     */
    public Command processDoneCmd(String[] inputParts) {
        try {
            String doneDescription = inputParts[1];
            int doneIndex = Integer.parseInt(doneDescription);
            return new DoneCommand(doneIndex);
        } catch (ArrayIndexOutOfBoundsException e) {
            String message = INDENTATION + "Oh no! The index for a task cannot be missing.\n"
                    + DONE_EXAMPLE;
            return new IncorrectCommand(message);
        } catch (NumberFormatException e) {
            String message = INDENTATION + "Oh no! The index for a task to delete must be an integer.\n"
                    + DONE_EXAMPLE;
            return new IncorrectCommand(message);
        }
    }

    /**
     * Process list command
     *
     * @param inputParts String array containing list command.
     * @return ListCommand or IncorrectCommand if invalid.
     */
    public Command processListCmd(String[] inputParts) {
        if (inputParts.length > 1) {
            String message = INDENTATION + "Oh no! There should be no description after list.\n"
                    + LIST_EXAMPLE;
            return new IncorrectCommand(message);
        } else {
            return new ListCommand();
        }
    }

    public Command processFindCmd(String[] inputParts) {
        try {
            String keyword = inputParts[1];
            return new FindCommand(keyword);
        } catch (ArrayIndexOutOfBoundsException e) {
            String message = INDENTATION + "Oh no! The keyword for find cannot be missing.\n"
                    + FIND_EXAMPLE;
            return new IncorrectCommand(message);
        }
    }
}

