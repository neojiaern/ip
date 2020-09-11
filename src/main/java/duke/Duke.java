package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    public static final String INDENTATION = "    ";
    public static final String LINE = INDENTATION
            + "____________________________________________________________";
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
    public static final String BYE_EXAMPLE = (INDENTATION + "bye: Exits the program."
            + System.lineSeparator() + INDENTATION + "  " + "Example: bye");

    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        int count = 0;
        Scanner in = new Scanner(System.in);


        printGreetMsg();
        String userInput = in.nextLine();
        processUserInput(userInput, tasks, count, in);
        printByeMsg();
    }

    public static void printGreetMsg() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo);
        System.out.println(LINE);
        System.out.println(INDENTATION + "Hello! I'm Duke.");
        System.out.println(INDENTATION + "What can I do for you?");
        System.out.println(LINE + "\n");
    }

    /**
     * Calls the respective methods for the different commands: list, done and add task
     * Unless "bye" is entered, continue to take in user input
     *
     * @param userInput user's input.
     * @param tasks an ArrayList to store tasks.
     * @param count keep a counter for number of tasks currently in list.
     * @param in scanner to take in user input.
     */
    public static void processUserInput(String userInput, ArrayList<Task> tasks, int count, Scanner in) {
        while (!userInput.equalsIgnoreCase("bye")) {
            String[] inputParts = userInput.split(" ", 2);
            String command = inputParts[0].toLowerCase();
            switch (command){
            case "list":
                if (inputParts.length > 1) {
                    System.out.println(LINE);
                    System.out.println(INDENTATION + "Oh no! There should be no description after list.");
                    System.out.println(LIST_EXAMPLE);
                    System.out.println(LINE + "\n");
                } else {
                    listTasks(tasks, count);
                }
                break;
            case "done":
                try {
                    doneTask(inputParts[1], tasks);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(LINE);
                    System.out.println(INDENTATION + "Oh no! The index for a completed task cannot be missing.");
                    System.out.println(DONE_EXAMPLE);
                    System.out.println(LINE + "\n");
                }
                break;
            case "todo":
            case "deadline":
            case"event":
                try {
                    count = addTask(inputParts[0], inputParts[1], tasks, count);
                } catch (ArrayIndexOutOfBoundsException e) {
                    printEmptyDescriptionMessage(command);
                    System.out.println(LINE + "\n");
                }
                break;
            case "delete":
                try {
                    count = deleteTask(inputParts[1], tasks, count);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(LINE);
                    System.out.println(INDENTATION + "Oh no! The index for a task to delete cannot be missing.");
                    System.out.println(DELETE_EXAMPLE);
                    System.out.println(LINE + "\n");
                }
                break;
            default:
                printExampleInput();
                break;
            }
            userInput = in.nextLine();
        }
    }

    /**
     * Adds task user specified and prints output msg
     *
     * @param taskType specifies type of task.
     * @param description of task.
     * @param tasks an ArrayList to store tasks.
     * @param count keep a counter for number of tasks currently in list.
     */
    public static int addTask(String taskType, String description, ArrayList<Task> tasks, int count) {
        switch (taskType) {
        case "todo":
            Task todoTask = new Todo(description);
            tasks.add(todoTask);
            break;
        case "deadline":
            String[] deadlineParts = description.split(" /by ");
            Task deadlineTask = new Deadline(deadlineParts[0], deadlineParts[1]);
            tasks.add(deadlineTask);
            break;
        case "event":
            String[] eventParts = description.split(" /at ");
            Task eventTask = new Event(eventParts[0], eventParts[1]);
            tasks.add(eventTask);
            break;
        default:
            break;
        }

        count++;
        System.out.println(LINE);
        System.out.println(INDENTATION + "Got it. I've added this task:");
        System.out.println(INDENTATION + "  " + tasks.get(count-1));
        System.out.println(INDENTATION + "Now you have " + count + " task(s) in the list.");
        System.out.println(LINE + "\n");

        return count;
    }

    public static int deleteTask(String description, ArrayList<Task> tasks, int count) {
        try {
            int deleteIndex = Integer.parseInt(description);
            String message = INDENTATION + " " + tasks.get(deleteIndex-1).toString();
            tasks.remove(deleteIndex-1);
            count--;
            System.out.println(LINE);
            System.out.println(INDENTATION + "Noted. I've removed this task:");
            System.out.println(message);
            System.out.println(INDENTATION + "Now you have " + count + " task(s) in the list.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE);
            if (description == null) {
                System.out.println(INDENTATION + "Oh no! The index for a task to delete cannot be missing.");
            } else {
                System.out.println(INDENTATION + "Oh no! This task is not found.");
            }
            System.out.println(DELETE_EXAMPLE);
        } catch (NumberFormatException e) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "Oh no! The index for a task to delete must be an integer.");
            System.out.println(DELETE_EXAMPLE);
        }
        System.out.println(LINE + "\n");
        return count;
    }

    /**
     * prints tasks present in list
     *
     * @param tasks an ArrayList to store tasks.
     * @param count keep a counter for number of tasks currently in list.
     */
    public static void listTasks(ArrayList<Task> tasks, int count) {
        if (count == 0) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "There is currently no task.");
            System.out.println(LINE + "\n");
        } else {
            System.out.println(LINE);
            System.out.println(INDENTATION + "Here are the tasks in your list:");
            for (int i = 1; i <= count; i++) {
                System.out.println(INDENTATION + i + "." + tasks.get(i-1));
            }
            System.out.println(LINE + "\n");
        }
    }

    /**
     * Marks task specified by user as done
     * Prints output msg
     * Handles error when user input is incorrect
     *
     * @param description contains index of completed task in list.
     * @param tasks an ArrayList to store tasks.
     */
    public static void doneTask(String description, ArrayList<Task> tasks) {
        try {
            int doneIndex = Integer.parseInt(description);
            tasks.get(doneIndex-1).markAsDone();
            System.out.println(LINE);
            System.out.println(INDENTATION + "Nice! I've marked this task as done:");
            System.out.println(INDENTATION + "  " + tasks.get(doneIndex-1));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE);
            if (description == null) {
                System.out.println(INDENTATION + "Oh no! The index for a completed task cannot be missing.");
            } else {
                System.out.println(INDENTATION + "Oh no! This task is not found.");
            }
            System.out.println(DONE_EXAMPLE);
        } catch (NumberFormatException e) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "Oh no! The index for a completed task must be an integer.");
            System.out.println(DONE_EXAMPLE);
        }
        System.out.println(LINE + "\n");
    }

    /**
     * Prints example of user input when command keyed is invalid
     */
    public static void printExampleInput() {
        System.out.println(LINE);
        System.out.println(INDENTATION + "Oh no! This command is invalid, please try again.");
        System.out.println(LIST_EXAMPLE + System.lineSeparator() + TODO_EXAMPLE + System.lineSeparator()
                + DEADLINE_EXAMPLE + System.lineSeparator() + EVENT_EXAMPLE + System.lineSeparator()
                + DELETE_EXAMPLE + System.lineSeparator() + DONE_EXAMPLE
                + System.lineSeparator() + BYE_EXAMPLE);
        System.out.println(LINE + "\n");
    }

    /**
     * Prints error message for empty description and example during adding of tasks
     *
     * @param taskType specifies the type of task.
     */
    public static void printEmptyDescriptionMessage(String taskType) {
        System.out.println(LINE);
        System.out.println(INDENTATION + "Oh no! The description of " + taskType + " cannot be empty.");
        switch (taskType) {
        case "todo":
            System.out.println(TODO_EXAMPLE);
            break;
        case "deadline":
            System.out.println(DEADLINE_EXAMPLE);
            break;
        case "event":
            System.out.println(EVENT_EXAMPLE);
            break;
        default:
            break;
        }
    }

    public static void printByeMsg() {
        System.out.println(LINE);
        System.out.println(INDENTATION + "Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }
}
