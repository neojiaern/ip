package duke.task;

import duke.commands.AddCommand;
import duke.commands.CommandResult;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static duke.common.Messages.MESSAGE_ADDED_TASK;
import static duke.common.Messages.MESSAGE_DUE_TASK;
import static duke.common.Messages.MESSAGE_FIND_TASK;
import static duke.common.Messages.MESSAGE_LIST_TASK;
import static duke.common.Messages.MESSAGE_MARK_AS_DONE;
import static duke.common.Messages.MESSAGE_MISSING_AT_DESCRIPTION;
import static duke.common.Messages.MESSAGE_MISSING_BY_DESCRIPTION;
import static duke.common.Messages.MESSAGE_NO_DUE_TASK;
import static duke.common.Messages.MESSAGE_NO_TASK;
import static duke.common.Messages.MESSAGE_NO_TASK_FOUND;
import static duke.common.Messages.MESSAGE_REMOVED_TASK;
import static duke.common.Messages.MESSAGE_TASK_NOT_FOUND;
import static duke.common.Messages.MESSAGE_WRONG_DATE_TIME_FORMAT;

/**
 * Deals with operations relating to TaskList
 */
public class TaskList {

    public static final String INDENTATION = "    ";

    protected ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int getCount() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Adds task user specified
     *
     * @param taskType specifies type of task.
     * @param taskDescription description of task.
     * @return result of execution.
     */
    public CommandResult addTask(String taskType, String taskDescription) {
        switch (taskType) {
        case AddCommand.COMMAND_WORD_T:
            Task todoTask = new Todo(taskDescription);
            tasks.add(todoTask);
            break;
        case AddCommand.COMMAND_WORD_D:
            try {
                String[] deadlineParts = taskDescription.split(" /by ");
                Task deadlineTask = new Deadline(deadlineParts[0], deadlineParts[1]);
                tasks.add(deadlineTask);
            } catch (ArrayIndexOutOfBoundsException e) {
                String message = MESSAGE_MISSING_BY_DESCRIPTION + AddCommand.DEADLINE_EXAMPLE;
                return new CommandResult(message);
            } catch (DateTimeParseException e) {
                String message = MESSAGE_WRONG_DATE_TIME_FORMAT + AddCommand.DEADLINE_EXAMPLE;
                return new CommandResult(message);
            }
            break;
        case AddCommand.COMMAND_WORD_E:
            try {
                String[] eventParts = taskDescription.split(" /at ");
                Task eventTask = new Event(eventParts[0], eventParts[1]);
                tasks.add(eventTask);
            } catch (ArrayIndexOutOfBoundsException e) {
                String message = MESSAGE_MISSING_AT_DESCRIPTION + AddCommand.EVENT_EXAMPLE;
                return new CommandResult(message);
            } catch (DateTimeParseException e) {
                String message = MESSAGE_WRONG_DATE_TIME_FORMAT + AddCommand.EVENT_EXAMPLE;
                return new CommandResult(message);
            }
            break;
        default:
            break;
        }

        String result = MESSAGE_ADDED_TASK + tasks.get(tasks.size()-1) + "\n"
                + INDENTATION + "Now you have " + tasks.size() + " task(s) in the list.";

        return new CommandResult(result);
    }

    /**
     * Deletes a task based on index user specified
     *
     * @param deleteIndex index of task to delete.
     * @return result of execution.
     */
    public CommandResult deleteTask(int deleteIndex) {
        String result = "";
        try {
            String taskMessage = INDENTATION + " " + tasks.get(deleteIndex-1).toString();
            tasks.remove(deleteIndex-1);
            result = MESSAGE_REMOVED_TASK + taskMessage + "\n"
                    + INDENTATION + "Now you have " + tasks.size() + " task(s) in the list.";
        } catch (IndexOutOfBoundsException e) {
            result = MESSAGE_TASK_NOT_FOUND;
        }
        return new CommandResult(result);
    }

    /**
     * Marks task specified by user as done based on index
     *
     * @param doneIndex contains index of completed task in list.
     * @return result of execution.
     */
    public CommandResult doneTask(int doneIndex) {
        String result = "";
        try {
            tasks.get(doneIndex-1).markAsDone();
            result = MESSAGE_MARK_AS_DONE + tasks.get(doneIndex-1).toString();
        } catch (IndexOutOfBoundsException e) {
            result = MESSAGE_TASK_NOT_FOUND;
        }
        return new CommandResult(result);
    }

    /**
     * Prints tasks present in list
     *
     * @return result of execution.
     */
    public CommandResult listTasks() {
        String result = "";
        if (tasks.size() == 0) {
            result = MESSAGE_NO_TASK;
        } else {
            result = MESSAGE_LIST_TASK;
            for (int i = 1; i <= tasks.size(); i++) {
                result += "\n" + INDENTATION + i + "." + tasks.get(i-1).toString();
            }
        }
        return new CommandResult(result);
    }

    public CommandResult findTaskByKeyword(String keyword) {
        String result = "";
        int count = 1;
        result = MESSAGE_FIND_TASK;
        for (int i = 1; i <= tasks.size(); i++) {
            Task task = tasks.get(i - 1);
            if (task.taskName.contains(keyword)) {
                result += "\n" + INDENTATION + count + "." + tasks.get(i - 1).toString();
                count++;
            }
        }
        if (count == 1) {
            result = MESSAGE_NO_TASK_FOUND;
        }
        return new CommandResult(result);
    }

    public CommandResult findDueTasks(LocalDate date) {
        String result = "";
        int count = 1;
        result = MESSAGE_DUE_TASK;
        for (int i = 1; i <= tasks.size(); i++) {
            Task task = tasks.get(i-1);
            if (task.hasDueDate && task.date.equals(date)) {
                result += "\n" + INDENTATION + count + "." + tasks.get(i-1).toString();
                count++;
            }
        }
        if (count == 1) {
            result = MESSAGE_NO_DUE_TASK;
        }
        return new CommandResult(result);
    }
}
