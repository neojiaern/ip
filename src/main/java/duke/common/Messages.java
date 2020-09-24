package duke.common;

public class Messages {

    public static final String INDENTATION = "    ";
    public static final String MESSAGE_EXTRA_DESCRIPTION = INDENTATION
            + "Oh no! There should be no description after list.\n";
    public static final String MESSAGE_MISSING_KEYWORD = INDENTATION
            + "Oh no! The keyword for find cannot be missing.\n";
    public static final String MESSAGE_INVALID_COMMAND = INDENTATION
            + "Oh no! This command is invalid, please try again.\n";
    public static final String MESSAGE_MISSING_INDEX = INDENTATION
            + "Oh no! The index for a task cannot be missing.\n";
    public static final String MESSAGE_NON_INTEGER =  INDENTATION
            + "Oh no! The index provided must be an integer.\n";
    public static final String MESSAGE_MISSING_DUE_DATE = INDENTATION
            + "Oh no! The due date cannot be missing.\n";
    public static final String MESSAGE_WRONG_DATE_FORMAT = INDENTATION
            + "Oh no! The due date specified is in a wrong format.\n";
    public static final String MESSAGE_MISSING_AT_DESCRIPTION = INDENTATION
            + "Oh no! /at description is missing or incomplete from event.\n";
    public static final String MESSAGE_MISSING_BY_DESCRIPTION = INDENTATION
            + "Oh no! /by description is missing or incomplete from deadline.\n";
    public static final String MESSAGE_WRONG_DATE_TIME_FORMAT = INDENTATION
            + "Oh no! The date or time specified is in a wrong format.\n";
    public static final String MESSAGE_ADDED_TASK = INDENTATION
            + "Got it. I've added this task:\n" + INDENTATION + "  ";
    public static final String MESSAGE_REMOVED_TASK = INDENTATION
            + "Noted. I've removed this task:\n";
    public static final String MESSAGE_TASK_NOT_FOUND = INDENTATION
            + "Oh no! This task is not found.";
    public static final String MESSAGE_MARK_AS_DONE = INDENTATION
            + "Nice! I've marked this task as done:\n" + INDENTATION + "  ";
    public static final String MESSAGE_NO_TASK = INDENTATION + "There is currently no task.";
    public static final String MESSAGE_LIST_TASK = INDENTATION
            + "Here are the task(s) in your list:";
    public static final String MESSAGE_FIND_TASK = INDENTATION
            + "Here are the matching task(s) in your list:";
    public static final String MESSAGE_DUE_TASK = INDENTATION + "Here are the tasks due:";
    public static final String MESSAGE_NO_DUE_TASK = INDENTATION
            + "There are no deadlines or events due on that date.";
    public static final String MESSAGE_NO_TASK_FOUND = INDENTATION
            + "There are no task(s) which match the given keyword.";
    public static final String MESSAGE_ERROR_COMPARING = "(Error comparing files.)";
}
