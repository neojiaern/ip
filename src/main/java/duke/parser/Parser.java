package duke.parser;

import duke.commands.AddCommand;
import duke.commands.ByeCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.DoneCommand;
import duke.commands.FindCommand;
import duke.commands.DueCommand;
import duke.commands.IncorrectCommand;
import duke.commands.ListCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static duke.common.Messages.MESSAGE_EXTRA_DESCRIPTION;
import static duke.common.Messages.MESSAGE_INVALID_COMMAND;
import static duke.common.Messages.MESSAGE_MISSING_DUE_DATE;
import static duke.common.Messages.MESSAGE_MISSING_INDEX;
import static duke.common.Messages.MESSAGE_MISSING_KEYWORD;
import static duke.common.Messages.MESSAGE_NON_INTEGER;
import static duke.common.Messages.MESSAGE_WRONG_DATE_FORMAT;

/**
 * Parses user input.
 */
public class Parser {

    public static final String INDENTATION = "    ";

    /**
     * Calls the respective methods for the different commands: add, list, done, due, find and delete task.
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
            return new IncorrectCommand(MESSAGE_INVALID_COMMAND + ListCommand.LIST_EXAMPLE
                    + System.lineSeparator() + AddCommand.TODO_EXAMPLE + System.lineSeparator()
                    + AddCommand.DEADLINE_EXAMPLE + System.lineSeparator() + AddCommand.EVENT_EXAMPLE
                    + System.lineSeparator() + DeleteCommand.DELETE_EXAMPLE + System.lineSeparator()
                    + DoneCommand.DONE_EXAMPLE + System.lineSeparator() + FindCommand.FIND_EXAMPLE
                    + System.lineSeparator() + DueCommand.DUE_EXAMPLE + System.lineSeparator()
                    + ByeCommand.BYE_EXAMPLE);
        }
    }

    /**
     * Processes adding of todo, deadline and event tasks command.
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
     * Processes delete command.
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
            String message = MESSAGE_MISSING_INDEX + DeleteCommand.DELETE_EXAMPLE;
            return new IncorrectCommand(message);
        } catch (NumberFormatException e) {
            String message = MESSAGE_NON_INTEGER + DeleteCommand.DELETE_EXAMPLE;
            return new IncorrectCommand(message);
        }
    }

    /**
     * Processes done command.
     *
     * @param inputParts String array containing done command and index of task.
     * @return DoneCommand containing index of completed task or IncorrectCommand if invalid.
     */
    public Command processDoneCmd(String[] inputParts) {
        try {
            String doneDescription = inputParts[1];
            int doneIndex = Integer.parseInt(doneDescription);
            return new DoneCommand(doneIndex);
        } catch (ArrayIndexOutOfBoundsException e) {
            String message = MESSAGE_MISSING_INDEX + DoneCommand.DONE_EXAMPLE;
            return new IncorrectCommand(message);
        } catch (NumberFormatException e) {
            String message = MESSAGE_NON_INTEGER + DoneCommand.DONE_EXAMPLE;
            return new IncorrectCommand(message);
        }
    }

    /**
     * Processes list command.
     *
     * @param inputParts String array containing list command.
     * @return ListCommand or IncorrectCommand if invalid.
     */
    public Command processListCmd(String[] inputParts) {
        if (inputParts.length > 1) {
            String message = MESSAGE_EXTRA_DESCRIPTION + ListCommand.LIST_EXAMPLE;
            return new IncorrectCommand(message);
        } else {
            return new ListCommand();
        }
    }

    /**
     * Processes find command.
     *
     * @param inputParts String array containing find command and keyword.
     * @return FindCommand containing keyword or IncorrectCommand if invalid.
     */
    public Command processFindCmd(String[] inputParts) {
        try {
            String keyword = inputParts[1];
            return new FindCommand(keyword);
        } catch (ArrayIndexOutOfBoundsException e) {
            String message = MESSAGE_MISSING_KEYWORD + FindCommand.FIND_EXAMPLE;
            return new IncorrectCommand(message);
        }
    }

    /**
     * Processes due command.
     *
     * @param inputParts String array containing due command and date.
     * @return DueCommand containing date or IncorrectCommand if invalid.
     */
    public Command processDueCmd(String[] inputParts) {
        try {
            String dueDescription = inputParts[1];
            LocalDate dueDate = LocalDate.parse(dueDescription);
            return new DueCommand(dueDate);
        } catch (ArrayIndexOutOfBoundsException e) {
            String message = MESSAGE_MISSING_DUE_DATE + DueCommand.DUE_EXAMPLE;
            return new IncorrectCommand(message);
        } catch (DateTimeParseException e) {
            String message = MESSAGE_WRONG_DATE_FORMAT + DueCommand.DUE_EXAMPLE;
            return new IncorrectCommand(message);
        }
    }
}

