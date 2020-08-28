import java.util.Scanner;

public class Duke {

    public static final String INDENTATION = "    ";
    public static final String LINE = INDENTATION + "____________________________________________________________";
    public static final int MAX_NUM = 100;

    public static void main(String[] args) {
        printGreetMsg();
        String userInput = takeInUserInput();
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
        System.out.println(INDENTATION + "Hello! I'm Duke");
        System.out.println(INDENTATION + "What can I do for you?");
        System.out.println(LINE + "\n");
    }

    public static String takeInUserInput(){
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public static void processUserInput(String userInput) {
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[MAX_NUM];
        int count = 0;
        while (!userInput.equalsIgnoreCase("bye")) {
            if (userInput.equalsIgnoreCase("list")) {
                listTasks(tasks, count);
            } else if (userInput.contains("done")) {
                doneTask(userInput, tasks);
            } else {
                count = addTask(userInput, tasks, count);
            }
            userInput = in.nextLine();
        }
    }

    public static void listTasks(Task[] tasks, int count) {
        if (count == 0) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "There is currently no task.");
            System.out.println(LINE + "\n");
        } else {
            System.out.println(LINE);
            for(int i = 1; i <= count; i++) {
                System.out.println(INDENTATION + " " + i + "." + tasks[i-1].getStatusIcon()
                        + " " + tasks[i-1].getTaskName());
            }
            System.out.println(LINE + "\n");
        }
    }

    public static void doneTask(String userInput, Task[] tasks) {
        String[] inputArr = userInput.split(" ");
        int doneIndex = Integer.parseInt(inputArr[1]);
        tasks[doneIndex-1].markAsDone();
        System.out.println(LINE);
        System.out.println(INDENTATION + "Nice! I've marked this task as done:");
        System.out.println(INDENTATION + "   " + tasks[doneIndex-1].getStatusIcon()
                + " " + tasks[doneIndex-1].getTaskName());
        System.out.println(LINE + "\n");
    }

    public static int addTask(String userInput, Task[] tasks, int count) {
        tasks[count] = new Task(userInput);
        System.out.println(LINE);
        System.out.println(INDENTATION + " added: " + userInput);
        System.out.println(LINE + "\n");
        count++;
        return count;
    }

    public static void printByeMsg() {
        System.out.println(LINE);
        System.out.println(INDENTATION + "Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }
}
