package duke.storage;

import duke.command.CommandResult;
import duke.exception.DukeFileException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Deals with loading and saving tasks from duke.txt
 */
public class Storage {

    public static final String INDENTATION = "    ";
    public static final String TICK = "\u2713";
    public static final String fileSeparator = System.getProperty("file.separator");

    protected String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from duke.txt
     *
     * @return tasks in duke.txt.
     * @throws DukeFileException when duke.txt is empty.
     */
    public ArrayList<Task> load() throws DukeFileException {
        File dukeFile = createFile();
        if (dukeFile.length() == 0) {
            throw new DukeFileException(System.lineSeparator() + INDENTATION + "(duke.txt is empty)");
        } else {
            ArrayList<Task> tasks = new ArrayList<>();
            processFile(dukeFile, tasks);
            return tasks;
        }
    }

    /**
     * Checks if duke.txt is present, if not create a new one
     *
     * @return the file duke.txt.
     */
    public File createFile() {
        File dukeFile = new File(filePath);
        try {
            if (dukeFile.exists()) {
                return dukeFile;
            }
            if (!dukeFile.getParentFile().exists()) {
                dukeFile.getParentFile().mkdirs();
            }
            dukeFile.createNewFile();
        } catch (IOException e) {
            System.out.println(System.lineSeparator() + INDENTATION + "(File duke.txt could not be created)");
        }
        return dukeFile;
    }

    /**
     * Processes the duke.txt file to save the tasks present in it to ArrayList
     *
     * @param dukeFile duke.txt file.
     * @param tasks an ArrayList to store tasks.
     */
    public void processFile(File dukeFile, ArrayList<Task> tasks) throws DukeFileException {
        Scanner reader = null;
        try {
            reader = new Scanner(dukeFile);
        } catch (FileNotFoundException e) {
            throw new DukeFileException(System.lineSeparator() + INDENTATION
                    + "(File duke.txt could not be found)");
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
    }

    /**
     * Saves the current tasks in the list to a temporary file
     *
     * @param tasks to be saved into temp.txt after changes have been made.
     */
    public void saveTasksToTempFile(TaskList tasks) throws DukeFileException {
        FileWriter tempFile = null;
        try {
            tempFile = new FileWriter("data/temp.txt");
        } catch (IOException e) {
            throw new DukeFileException(System.lineSeparator() + INDENTATION + "(Could not create temp.txt)");
        }
        for(int i = 1; i <= tasks.getCount(); i++) {
            String task = "";
            if (tasks.getTask(i-1).hasDueDate) {
                task = tasks.getTask(i - 1).printToFile();
            } else {
                task = tasks.getTask(i - 1).toString();
            }
            String[] taskParts = task.split("\\]\\[", 2);
            String[] descriptionParts = taskParts[1].split(" ", 2);
            String type = "";
            String description = "";
            int done = 0;
            switch (taskParts[0].replace("[", "")) {
            case "T":
                type = "T";
                description = descriptionParts[1];
                break;
            case "D":
                type = "D";
                String[] dParts = descriptionParts[1].split(" \\(by: ");
                description = dParts[0] + " | " + dParts[1].replace(")", "");
                break;
            case "E":
                type = "E";
                String[] eParts = descriptionParts[1].split(" \\(at: ");
                description = eParts[0] + " | " + eParts[1].replace(")", "");
                break;
            default:
                break;
            }
            done = checkStatus(descriptionParts[0]);
            String entry = type + " | " + done + " | " + description + "\n";
            try {
                tempFile.write(entry);
            } catch (IOException e) {
                throw new DukeFileException(System.lineSeparator() + INDENTATION + "(Could not write to temp.txt)");
            }
        }
        try {
            tempFile.close();
        } catch (IOException e) {
            throw new DukeFileException(System.lineSeparator() + INDENTATION + "(temp.txt could not be closed)");
        }
    }

    /**
     * Checks if status of task is completed to assign it the correct icon
     *
     * @param status whether task is done or not done.
     * @return status of task.
     */
    private int checkStatus(String status) {
        int done = 0;
        status = status.replace("]", "");
        if (status.equals(TICK)) {
            done = 1;
        }
        return done;
    }

    /**
     * Compares duke.txt and temp.txt to find differences
     *
     * @return result of whether both files are the same.
     * @throws IOException if files cannot be read.
     * @throws DukeFileException if there are errors when comparing files.
     */
    public boolean compareFile() throws DukeFileException, IOException {
        BufferedReader dukeReader = null;
        BufferedReader tempReader = null;
        try {
            dukeReader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new DukeFileException(System.lineSeparator() + INDENTATION
                    + "(Error comparing files, duke.txt not found)");
        }
        try {
            tempReader = new BufferedReader(new FileReader("data" + fileSeparator + "temp.txt"));
        } catch (FileNotFoundException e) {
            throw new DukeFileException(System.lineSeparator() + INDENTATION
                    + "(Error comparing files, temp.txt not found)");
        }
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
            try {
                dukeLine = dukeReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            tempLine = tempReader.readLine();
        }
        dukeReader.close();
        tempReader.close();

        return isEqual;
    }

    /**
     * If files are different, replace duke.txt with temp.txt to save changes made
     * If files are the same, remove temp.txt
     *
     * @param isEqual whether duke.txt and temp.txt are the same.
     * @return result of save
     */
    public CommandResult save(boolean isEqual) {
        File tempFile = new File("." + fileSeparator + "data" + fileSeparator + "temp.txt");
        File dukeFile = new File(filePath);
        if (!isEqual) {
            boolean isDeleted = dukeFile.delete();
            boolean isRenamed = tempFile.renameTo(dukeFile);
            if (isRenamed && isDeleted) {
                return new CommandResult(System.lineSeparator() + INDENTATION
                        + "(Changes have been saved to duke.txt)");
            } else {
                return new CommandResult(System.lineSeparator() + INDENTATION
                        + "(Changes have not been saved to duke.txt)");
            }
        } else {
            boolean isDeleted = tempFile.delete();
            if (isDeleted) {
                return new CommandResult(System.lineSeparator() + INDENTATION
                        + "(No changes have been made to duke.txt)");
            } else {
                return new CommandResult(System.lineSeparator() + INDENTATION
                        + "(Failed to remove temp.txt)");
            }
        }
    }
}
