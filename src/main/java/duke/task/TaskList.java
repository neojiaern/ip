package duke.task;

import duke.commands.AddCommand;
import duke.commands.CommandResult;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static duke.common.Messages.*;

/**
 * Deals with operations relating to TaskList.
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
     * Adds a new task.
     *
     * @param taskType specifies type of task.
     * @param taskDescription description of task.
     * @return success message and task which has been added, or error message if invalid.
     */
    public CommandResult addTask(String taskType, String taskDescription) {
        boolean isDuplicate = checkDuplicate(taskDescription);
        if (isDuplicate) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
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

    private boolean checkDuplicate(String taskDescription) {
        for (int i = 1; i <= tasks.size(); i++) {
            Task task = tasks.get(i - 1);
            // Checks if a task description is duplicated
            if (task.taskName.equalsIgnoreCase(taskDescription)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a task based on index user specified.
     *
     * @param deleteIndex index of task to delete.
     * @return success message and task which has been deleted, or error message if task is not found.
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
     * Marks task specified by user as done based on index.
     *
     * @param doneIndex index of completed task.
     * @return success message and task which has been marked as done, or error message if task is not found.
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
     * Prints tasks present in list.
     *
     * @return list of tasks present.
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

    /**
     * Finds tasks which match the keyword.
     *
     * @param keywords keyword relating to a task.
     * @return list of tasks which match the keyword.
     */
    public CommandResult findTaskByKeyword(List<String> keywords) {
        String result = "";
        int count = 1;
        result = MESSAGE_FIND_TASK;
        for (int i = 1; i <= tasks.size(); i++) {
            Task task = tasks.get(i - 1);
            // Checks if a task matches any keyword
            if (keywords.stream().anyMatch(s -> task.taskName.toLowerCase().contains(s.toLowerCase()))) {
                result += "\n" + INDENTATION + count + "." + tasks.get(i - 1).toString();
                count++;
            }
        }
        if (count == 1) {
            result = MESSAGE_NO_TASK_FOUND;
        }
        return new CommandResult(result);
    }

    /**
     * Finds the tasks matching the due date.
     *
     * @param date due date of task.
     * @return list of tasks which match the date.
     */
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
