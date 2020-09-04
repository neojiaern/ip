import java.util.Scanner;

public class Duke {

    public static final String INDENTATION = "    ";
    public static final String LINE = INDENTATION
            + "____________________________________________________________";
    public static final int MAX_NUM = 100;

    public static Task[] tasks = new Task[MAX_NUM];
    public static int count = 0;
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        printGreetMsg();
        String userInput = in.nextLine();
        processUserInput(userInput);
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
     */
    public static void processUserInput(String userInput) {
        while (!userInput.equalsIgnoreCase("bye")) {
            String[] inputParts = userInput.split(" ", 2);
            String command = inputParts[0].toLowerCase();
            switch (command){
            case "list":
                listTasks();
                break;
            case "done":
                doneTask(inputParts[1]);
                break;
            default:
                addTask(command, inputParts[1]);
                break;
            }
            userInput = in.nextLine();
        }
    }

    /**
     * Adds task user specified and prints output msg
     *
     * @param taskType todo, deadline or event.
     * @param description task details.
     */
    public static void addTask(String taskType, String description) {
        switch (taskType) {
        case "todo":
            tasks[count] = new Todo(description);
            break;
        case "deadline":
            String[] deadlineParts = description.split(" /by ");
            tasks[count] = new Deadline(deadlineParts[0], deadlineParts[1]);
            break;
        case "event":
            String[] eventParts = description.split(" /at ");
            tasks[count] = new Event(eventParts[0], eventParts[1]);
            break;
        default:
            System.out.println(LINE);
            System.out.println(INDENTATION + "Sorry, you have keyed in an invalid command, please try again.");
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
    public static void listTasks() {
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
     *
     * @param num task number in the list that user has completed.
     */
    public static void doneTask(String num) {
        int doneIndex = Integer.parseInt(num);
        tasks[doneIndex-1].markAsDone();
        System.out.println(LINE);
        System.out.println(INDENTATION + "Nice! I've marked this task as done:");
        System.out.println(INDENTATION + "  " + tasks[doneIndex-1]);
        System.out.println(LINE + "\n");
    }

    public static void printByeMsg() {
        System.out.println(LINE);
        System.out.println(INDENTATION + "Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }
}
