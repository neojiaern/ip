import java.util.Scanner;

public class Duke {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String SMILEY_FACE = "\uD83D\uDE00";
    public static final String SAD_FACE = "\u2639";
    public static final String THUMBS_UP = "\uD83D\uDC4D";
    public static final String INDENTATION = "    ";
    public static final String LINE = INDENTATION
            + "____________________________________________________________" + ANSI_RESET;
    public static final int MAX_NUM = 100;

    public static Task[] tasks = new Task[MAX_NUM];
    public static int count = 0;

    public static void main(String[] args) {
        printGreetMsg();
        String userInput = takeInUserInput();
        processUserInput(userInput);
        printByeMsg();
    }

    public static void printGreetMsg() {
        String logo = ANSI_BLUE + " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n" + ANSI_RESET;
        System.out.println(logo);

        System.out.println(LINE);
        System.out.println(INDENTATION + "Hello! I'm " + ANSI_BLUE + "Duke " + ANSI_RESET  + SMILEY_FACE);
        System.out.println(INDENTATION + "What can I do for you?");
        System.out.println(LINE + "\n");
    }

    public static String takeInUserInput(){
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public static void processUserInput(String userInput) {
        Scanner in = new Scanner(System.in);

        while (!userInput.equalsIgnoreCase("bye")) {
            if (userInput.equalsIgnoreCase("list")) {
                listTasks();
            } else if (userInput.contains("done")) {
                doneTask(userInput);
            } else {
                addTask(userInput);
            }
            userInput = in.nextLine();
        }
    }

    public static void listTasks() {
        if (count == 0) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "There is currently no task.");
            System.out.println(LINE + "\n");
        } else {
            System.out.println(LINE);
            for(int i = 1; i <= count; i++) {
                if (tasks[i-1].getStatus()) {
                    System.out.println(INDENTATION + " " + i + "." + ANSI_GREEN + tasks[i - 1].getStatusIcon()
                            + " " + tasks[i - 1].getTaskName() + ANSI_RESET);
                } else {
                    System.out.println(INDENTATION + " " + i + "." + ANSI_RED + tasks[i - 1].getStatusIcon()
                            + " " + tasks[i - 1].getTaskName() + ANSI_RESET);
                }

            }
            System.out.println(LINE + "\n");
        }
    }

    public static void doneTask(String userInput) {
        String[] inputArr = userInput.split(" ");
        int doneIndex = Integer.parseInt(inputArr[1]);
        tasks[doneIndex-1].markAsDone();
        System.out.println(LINE);
        System.out.println(INDENTATION + "Nice! " + THUMBS_UP + " I've marked this task as done:");
        System.out.println(INDENTATION + "   " + ANSI_GREEN + tasks[doneIndex-1].getStatusIcon()
                + " " + tasks[doneIndex-1].getTaskName() + ANSI_RESET);
        System.out.println(LINE + "\n");
    }

    public static int addTask(String userInput) {
        tasks[count] = new Task(userInput);
        System.out.println(LINE);
        System.out.println(INDENTATION + " added: " + ANSI_YELLOW + userInput + ANSI_RESET);
        System.out.println(LINE + "\n");
        count++;
        return count;
    }

    public static void printByeMsg() {
        System.out.println(LINE);
        System.out.println(INDENTATION + "Bye " + SAD_FACE + " Hope to see you again soon!");
        System.out.println(LINE);
    }
}
