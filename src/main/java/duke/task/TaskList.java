package duke.task;

import duke.command.AddCommand;
import duke.command.CommandResult;

import java.util.ArrayList;

/**
 * Deals with operations relating to TaskList
 */
public class TaskList {

    public static final String INDENTATION = "    ";
    public static final String DEADLINE_EXAMPLE = (INDENTATION + "deadline: Adds a deadline task."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: TASK_DESCRIPTION /by TIME"
            + System.lineSeparator() + INDENTATION + "  " + "Example: deadline CS2113T assignment /by Monday");
    public static final String EVENT_EXAMPLE = (INDENTATION + "event: Adds an event task."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: TASK_DESCRIPTION /at TIME")
            + System.lineSeparator() + INDENTATION + "  " + "Example: event project meeting /at Tuesday 2pm";

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
                String message = INDENTATION + "Oh no! /by description is missing from deadline\n" + DEADLINE_EXAMPLE;
                return new CommandResult(message);
            }
            break;
        case AddCommand.COMMAND_WORD_E:
            try {
                String[] eventParts = taskDescription.split(" /at ");
                Task eventTask = new Event(eventParts[0], eventParts[1]);
                tasks.add(eventTask);
            } catch (ArrayIndexOutOfBoundsException e) {
                String message = INDENTATION + "Oh no! /at description is missing from event\n" + EVENT_EXAMPLE;
                return new CommandResult(message);
            }
            break;
        default:
            break;
        }

        String result = INDENTATION + "Got it. I've added this task:\n"
                + INDENTATION + "  " + tasks.get(tasks.size()-1) + "\n"
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
            result = INDENTATION + "Noted. I've removed this task:\n" + taskMessage + "\n"
                    + INDENTATION + "Now you have " + tasks.size() + " task(s) in the list.";
        } catch (IndexOutOfBoundsException e) {
            result = INDENTATION + "Oh no! This task is not found.";
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
            result = INDENTATION + "Nice! I've marked this task as done:\n"
                    + INDENTATION + "  " + tasks.get(doneIndex-1).toString();
        } catch (IndexOutOfBoundsException e) {
            result = INDENTATION + "Oh no! This task is not found.";
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
            result = INDENTATION + "There is currently no task.";

        } else {
            result = INDENTATION + "Here are the tasks in your list:";
            for (int i = 1; i <= tasks.size(); i++) {
                result += "\n" + INDENTATION + i + "." + tasks.get(i-1).toString();
            }
        }
        return new CommandResult(result);
    }
}