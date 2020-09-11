package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.util.Scanner;

public class Duke {

    public static final String INDENTATION = "    ";
    public static final String LINE = INDENTATION
            + "____________________________________________________________";
    public static final int MAX_NUM = 100;
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
    public static final String DONE_EXAMPLE = (INDENTATION + "done: Marks a completed task as done."
            + System.lineSeparator() + INDENTATION + "  " + "Parameters: INDEX_OF_COMPLETED_TASK"
            + System.lineSeparator() + INDENTATION + "  " + "Example: done 2");
    public static final String BYE_EXAMPLE = (INDENTATION + "bye: Exits the program."
            + System.lineSeparator() + INDENTATION + "  " + "Example: bye");

    public static void main(String[] args) {
        Task[] tasks = new Task[MAX_NUM];
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
        System.out.println(INDENTATION + "Hello! I'm duke.Duke.");
        System.out.println(INDENTATION + "What can I do for you?");
        System.out.println(LINE + "\n");
    }

    /**
     * Calls the respective methods for the different commands: list, done and add task
     * Unless "bye" is entered, continue to take in user input
     *
     * @param userInput user's input.
     */
    public static void processUserInput(String userInput, Task[] tasks, int count, Scanner in) {
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
                doneTask(userInput, tasks);
                break;
            default:
                addTask(userInput, tasks, count);
                break;
            }
            userInput = in.nextLine();
        }
    }

    /**
     * Adds task user specified and prints output msg
     *
     * @param userInput user input containing taskType and description.
     */
    public static void addTask(String userInput, Task[] tasks, int count) {
        String[] inputParts = userInput.split(" ", 2);
        String taskType = inputParts[0];
        try {
            switch (taskType) {
            case "todo":
                tasks[count] = new Todo(inputParts[1]);
                break;
            case "deadline":
                String[] deadlineParts = inputParts[1].split(" /by ");
                tasks[count] = new Deadline(deadlineParts[0], deadlineParts[1]);
                break;
            case "event":
                String[] eventParts = inputParts[1].split(" /at ");
                tasks[count] = new Event(eventParts[0], eventParts[1]);
                break;
            default:
                printExampleInput();
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
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
            System.out.println(LINE + "\n");
            return;
        }
        count++;

        System.out.println(LINE);
        System.out.println(INDENTATION + "Got it. I've added this task:");
        System.out.println(INDENTATION + "  " + tasks[count-1]);
        System.out.println(INDENTATION + "Now you have " + count + " task(s) in the list.");
        System.out.println(LINE + "\n");
    }

    // prints tasks present in list
    public static void listTasks(Task[] tasks, int count) {
        if (count == 0) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "There is currently no task.");
            System.out.println(LINE + "\n");
        } else {
            System.out.println(LINE);
            System.out.println(INDENTATION + "Here are the tasks in your list:");
            for(int i = 1; i <= count; i++) {
                System.out.println(INDENTATION + i + "." + tasks[i-1]);
            }
            System.out.println(LINE + "\n");
        }
    }

    /**
     * Marks task specified by user as done
     * Prints output msg
     * Handles error when user input is incorrect
     *
     * @param userInput user input containing done command and index of task in list.
     */
    public static void doneTask(String userInput, Task[] tasks) {
        String[] inputParts = userInput.split(" ", 2);
        try {
            int doneIndex = Integer.parseInt(inputParts[1]);
            tasks[doneIndex-1].markAsDone();
            System.out.println(LINE);
            System.out.println(INDENTATION + "Nice! I've marked this task as done:");
            System.out.println(INDENTATION + "  " + tasks[doneIndex-1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "Oh no! The index for a completed task cannot be missing.");
            System.out.println(DONE_EXAMPLE);
        } catch (NumberFormatException e) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "Oh no! The index for a completed task must be an integer.");
            System.out.println(DONE_EXAMPLE);
        } catch (NullPointerException e) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "Oh no! This task is not found.");
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
                + DONE_EXAMPLE + System.lineSeparator() + BYE_EXAMPLE);
        System.out.println(LINE + "\n");
    }

    public static void printByeMsg() {
        System.out.println(LINE);
        System.out.println(INDENTATION + "Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }
}
