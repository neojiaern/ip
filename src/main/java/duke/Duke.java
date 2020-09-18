package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.*;
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
    public static final String fileSeparator = System.getProperty("file.separator");
    public static final String dirPath = "." + fileSeparator + "data";
    public static final String filePath =  dirPath + fileSeparator + "duke.txt";

    public static void main(String[] args) throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        int count;
        Scanner in = new Scanner(System.in);

        File dukeFile = checkFile();
        count = processFile(dukeFile, tasks);

        printGreetMsg();
        String userInput = in.nextLine();
        processUserInput(userInput, tasks, count, in);

        saveTasksToTempFile(tasks);
        compareFile(dukeFile);
        printByeMsg();
    }

    /**
     * Checks if duke.txt is present, if not create a new one
     *
     * @return the file duke.txt.
     */
    public static File checkFile() {
        File dataDir = new File(dirPath);
        File dukeFile = new File(filePath);
        boolean dirExists = dataDir.exists();
        boolean fileExists = dukeFile.exists();
        if (fileExists && dirExists) {
            return dukeFile;
        } else if (!dirExists && !fileExists) {
            createDataDir(dataDir);
            createDukeFile(dukeFile);
        } else if (!dirExists) {
            boolean dirCreated = dataDir.mkdir();
            System.out.println(dirCreated);
        } else { // file does not exist
            createDukeFile(dukeFile);
        }
        return dukeFile;
    }

    /**
     * Creates the data directory
     *
     * @param dataDir data directory.
     */
    public static void createDataDir(File dataDir) {
        boolean isCreateDirSuccess = dataDir.mkdir();
        if (isCreateDirSuccess) {
            System.out.println(System.lineSeparator() + INDENTATION + "(Directory ./data created)");
        } else {
            System.out.println(System.lineSeparator() + INDENTATION + "(Directory ./data not created)");
        }
    }

    /**
     * Creates the file duke.txt
     *
     * @param dukeFile duke.txt file.
     */
    public static void createDukeFile(File dukeFile) {
        try {
            boolean isCreateFileSuccess = dukeFile.createNewFile();
            if (isCreateFileSuccess) {
                System.out.println(System.lineSeparator() + INDENTATION + "(File duke.txt created)");
            } else {
                System.out.println(System.lineSeparator() + INDENTATION + "(File duke.txt not created)");
            }
        } catch (IOException e) {
            System.out.println(System.lineSeparator() + INDENTATION + "(File duke.txt could not be created)");
        }
    }

    /**
     * Processes the duke.txt file to save the tasks present in it to ArrayList
     *
     * @param dukeFile duke.txt file.
     * @param tasks an ArrayList to store tasks.
     * @return current number of tasks in the list.
     */
    public static int processFile(File dukeFile, ArrayList<Task> tasks) {
        Scanner reader = null;
        try {
            reader = new Scanner(dukeFile);
        } catch (FileNotFoundException e) {
            System.out.println(System.lineSeparator() + INDENTATION + "(File duke.txt could not be found)");
        }
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String[] dataParts = data.split(" \\| ");
            switch (dataParts[0]) {
            case "T":
                Task todoTask = new Todo(dataParts[2]);
                tasks.add(todoTask);
                break;
            case "D":
                Task deadlineTask = new Deadline(dataParts[2], dataParts[3]);
                tasks.add(deadlineTask);
                break;
            case "E":
                Task eventTask = new Event(dataParts[2], dataParts[3]);
                tasks.add(eventTask);
                break;
            default:
                // ignore invalid task types
                break;
            }
            if (dataParts[1].equals("1")) {
                tasks.get(tasks.size()-1).markAsDone();
            }
        }
        reader.close();
        return tasks.size();
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
                processListCmd(tasks, inputParts);
                break;
            case "done":
                processDoneCmd(tasks, inputParts);
                break;
            case "todo":
            case "deadline":
            case"event":
                processAddCmd(tasks, inputParts, command);
                break;
            case "delete":
                processDelCmd(tasks, inputParts);
                break;
            default:
                printExampleInput();
                break;
            }
            userInput = in.nextLine();
        }
    }

    /**
     * Process delete command
     *
     * @param tasks an ArrayList to store tasks.
     * @param inputParts String array containing delete command and index of task.
     * @return
     */
    public static void processDelCmd(ArrayList<Task> tasks, String[] inputParts) {
        try {
            deleteTask(inputParts[1], tasks);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "Oh no! The index for a task to delete cannot be missing.");
            System.out.println(DELETE_EXAMPLE);
            System.out.println(LINE + "\n");
        }
    }

    /**
     * Process adding of todo, deadline and event tasks
     *
     * @param tasks an ArrayList to store tasks.
     * @param inputParts String array containing type of task and it's description.
     * @param command contains either "todo"/"deadline"/"event".
     * @return the number of tasks in list after addition of new task.
     */
    public static void processAddCmd(ArrayList<Task> tasks, String[] inputParts, String command) {
        try {
            addTask(inputParts[0], inputParts[1], tasks);
        } catch (ArrayIndexOutOfBoundsException e) {
            printEmptyDescriptionMessage(command);
            System.out.println(LINE + "\n");
        }
    }

    /**
     * Process done command
     *
     * @param tasks an ArrayList to store tasks.
     * @param inputParts String array containing done command and index of task.
     */
    public static void processDoneCmd(ArrayList<Task> tasks, String[] inputParts) {
        try {
            doneTask(inputParts[1], tasks);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "Oh no! The index for a completed task cannot be missing.");
            System.out.println(DONE_EXAMPLE);
            System.out.println(LINE + "\n");
        }
    }

    /**
     * Process list command
     *
     * @param tasks an ArrayList to store tasks.
     * @param inputParts String array containing list command.
     */
    public static void processListCmd(ArrayList<Task> tasks, String[] inputParts) {
        if (inputParts.length > 1) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "Oh no! There should be no description after list.");
            System.out.println(LIST_EXAMPLE);
            System.out.println(LINE + "\n");
        } else {
            listTasks(tasks);
        }
    }

    /**
     * Adds task user specified and prints output msg
     *
     * @param taskType specifies type of task.
     * @param description of task.
     * @param tasks an ArrayList to store tasks.
     */
    public static void addTask(String taskType, String description, ArrayList<Task> tasks) {
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

        System.out.println(LINE);
        System.out.println(INDENTATION + "Got it. I've added this task:");
        System.out.println(INDENTATION + "  " + tasks.get(tasks.size()-1));
        System.out.println(INDENTATION + "Now you have " + tasks.size() + " task(s) in the list.");
        System.out.println(LINE + "\n");
    }

    public static void deleteTask(String description, ArrayList<Task> tasks) {
        try {
            int deleteIndex = Integer.parseInt(description);
            String message = INDENTATION + " " + tasks.get(deleteIndex-1).toString();
            tasks.remove(deleteIndex-1);
            System.out.println(LINE);
            System.out.println(INDENTATION + "Noted. I've removed this task:");
            System.out.println(message);
            System.out.println(INDENTATION + "Now you have " + tasks.size() + " task(s) in the list.");
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
    }

    /**
     * prints tasks present in list
     *
     * @param tasks an ArrayList to store tasks.
     */
    public static void listTasks(ArrayList<Task> tasks) {
        if (tasks.size() == 0) {
            System.out.println(LINE);
            System.out.println(INDENTATION + "There is currently no task.");
            System.out.println(LINE + "\n");
        } else {
            System.out.println(LINE);
            System.out.println(INDENTATION + "Here are the tasks in your list:");
            for (int i = 1; i <= tasks.size(); i++) {
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

    /**
     * Saves the current tasks in the list to a temporary file
     *
     * @param tasks an ArrayList to store tasks.
     */
    public static void saveTasksToTempFile(ArrayList<Task> tasks) {
        FileWriter tempFile = null;
        try {
            tempFile = new FileWriter("data/temp.txt");
        } catch (IOException e) {
            System.out.println("Could not create temp.txt");
        }
        for(int i = 1; i <= tasks.size(); i++) {
            String task = tasks.get(i-1).toString();
            String[] taskParts = task.split("\\]\\[", 2);
            String[] descriptionParts= taskParts[1].split(" ", 2);
            String type = "";
            String description = "";
            int done = 0;
            switch (taskParts[0]) {
            case "[T":
                type = "T";
                description = descriptionParts[1];
                break;
            case "[D":
                type = "D";
                String[] dParts =descriptionParts[1].split(" \\(by: ");
                description = dParts[0] + " | " + dParts[1].replace(")", "");
                break;
            case "[E":
                type = "E";
                String[] eParts =descriptionParts[1].split(" \\(at: ");
                description = eParts[0] + " | " + eParts[1].replace(")", "");
                break;
            default:
                break;
            }
            done = checkStatus(descriptionParts[0]);
            String entry = type + " | " + done  + " | " + description + "\n";
            try {
                tempFile.write(entry);
            } catch (IOException e) {
                System.out.println("Could not write to temp.txt");
            }
        }
        try {
            tempFile.close();
        } catch (IOException e) {
            System.out.println("temp.txt could not be closed.");
        }
    }

    /**
     * Compares duke.txt and temp.txt to find differences
     * If different, replace duke.txt with temp.txt to save changes made
     * If same, remove temp.txt
     *
     * @param dukeFile duke.txt file.
     * @throws IOException if files cannot be read.
     */
    private static void compareFile(File dukeFile) throws IOException {
        BufferedReader dukeReader = new BufferedReader(new FileReader("data" + fileSeparator + "duke.txt"));
        BufferedReader tempReader = new BufferedReader(new FileReader("data" + fileSeparator + "temp.txt"));
        String dukeLine = dukeReader.readLine();
        String tempLine = tempReader.readLine();
        boolean isEqual = true;

        while (dukeLine != null || tempLine != null) {
            if(dukeLine == null || tempLine == null) {
                isEqual = false;
                break;
            }  else if (!dukeLine.equalsIgnoreCase(tempLine)) {
                isEqual = false;
                break;
            }

            dukeLine = dukeReader.readLine();
            tempLine = tempReader.readLine();
        }
        dukeReader.close();
        tempReader.close();

        File tempFile = new File(dirPath + fileSeparator + "temp.txt");
        if (!isEqual) {
            boolean isDeleted = dukeFile.delete();
            boolean isRenamed = tempFile.renameTo(dukeFile);
            if (isRenamed && isDeleted) {
                System.out.println(System.lineSeparator() + INDENTATION + "(Changes have been saved to duke.txt)");
            } else {
                System.out.println(System.lineSeparator() + INDENTATION + "(Changes have not been saved to duke.txt)");
            }
        } else {
            boolean isDeleted = tempFile.delete();
            if (isDeleted) {
                System.out.println(System.lineSeparator() + INDENTATION + "(No changes have been made to duke.txt)");
            } else {
                System.out.println(System.lineSeparator() + INDENTATION + "(Failed to remove temp.txt)");
            }
        }
    }

    /**
     * Checks if status of task is completed to assign it the correct icon
     *
     * @param status whether task is done or not done.
     * @return status of task.
     */
    private static int checkStatus(String status) {
        int done = 0;
        if (status.equals("\u2713]")) {
            done = 1;
        }
        return done;
    }

    public static void printByeMsg() {
        System.out.println(LINE);
        System.out.println(INDENTATION + "Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }
}
