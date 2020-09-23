package duke.parser;

import duke.command.AddCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.DueCommand;
import duke.command.IncorrectCommand;
import duke.command.ListCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public static final String INDENTATION = "    ";

    /**
     * Calls the respective methods for the different commands: list, done, and delete task
     *
     * @param userInput user's input.
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
        case DueCommand.COMMAND_WORD:
            return processDueCmd(inputParts);
        case ByeCommand.COMMAND_WORD:
            return new ByeCommand();
        default:
            return new IncorrectCommand(INDENTATION + "Oh no! This command is invalid, "
                    + "please try again.\n" + ListCommand.LIST_EXAMPLE + System.lineSeparator()
                    + AddCommand.TODO_EXAMPLE + System.lineSeparator() + AddCommand.DEADLINE_EXAMPLE
                    + System.lineSeparator() + AddCommand.EVENT_EXAMPLE + System.lineSeparator()
                    + DeleteCommand.DELETE_EXAMPLE + System.lineSeparator() + DoneCommand.DONE_EXAMPLE
                    + System.lineSeparator() + FindCommand.FIND_EXAMPLE + System.lineSeparator()
                    + DueCommand.DUE_EXAMPLE + System.lineSeparator() + ByeCommand.BYE_EXAMPLE);
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
                message += AddCommand.TODO_EXAMPLE;
                break;
            case AddCommand.COMMAND_WORD_D:
                message += AddCommand.DEADLINE_EXAMPLE;
                break;
            case AddCommand.COMMAND_WORD_E:
                message += AddCommand.EVENT_EXAMPLE;
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
                    + DeleteCommand.DELETE_EXAMPLE;
            return new IncorrectCommand(message);
        } catch (NumberFormatException e) {
            String message = INDENTATION + "Oh no! The index for a task to delete must be an integer.\n"
                    + DeleteCommand.DELETE_EXAMPLE;
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
                    + DoneCommand.DONE_EXAMPLE;
            return new IncorrectCommand(message);
        } catch (NumberFormatException e) {
            String message = INDENTATION + "Oh no! The index for a task to delete must be an integer.\n"
                    + DoneCommand.DONE_EXAMPLE;
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
                    + ListCommand.LIST_EXAMPLE;
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
                    + FindCommand.FIND_EXAMPLE;
            return new IncorrectCommand(message);
        }
    }
    public Command processDueCmd(String[] inputParts) {
        try {
            String dueDescription = inputParts[1];
            LocalDate dueDate = LocalDate.parse(dueDescription);
            return new DueCommand(dueDate);
        } catch (ArrayIndexOutOfBoundsException e) {
            String message = INDENTATION + "Oh no! The due date cannot be missing.\n"
                    + DueCommand.DUE_EXAMPLE;
            return new IncorrectCommand(message);
        } catch (DateTimeParseException e) {
            String message = INDENTATION + "Oh no! The due date specified is in a wrong format.\n"
                    + DueCommand.DUE_EXAMPLE;
            return new IncorrectCommand(message);
        }
    }
}

