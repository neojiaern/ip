package duke;

import duke.commands.Command;
import duke.commands.CommandResult;
import duke.exception.DukeFileException;
import duke.parser.Parser;
import duke.task.TaskList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;

import static duke.common.Messages.MESSAGE_ERROR_COMPARING;

public class Duke {

    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private Parser parser;

    public static final String fileSeparator = System.getProperty("file.separator");
    public static final String filePath = "." + fileSeparator + "data" + fileSeparator + "duke.txt";

    /**
     * Creates the objects and loads any previously saved TaskList or creates a new one.
     *
     * @param filePath of duke.txt.
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeFileException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public static void main(String[] args) {
        new Duke(filePath).run();
    }

    /**
     * Prints greet message.
     * Loops for user input, processes it and prints result of execution until user exits.
     */
    public void run() {
        ui.printGreetMsg();
        boolean isExit = false;
        while (!isExit) {
            String userInput = ui.getUserInput();
            ui.printLine();
            Command c = parser.parse(userInput);
            CommandResult result = c.execute(tasks, ui);
            ui.printResult(result);
            isExit = c.isExit();
            ui.printLine();
            ui.printBlankLine();
        }
        exit();
    }

    /**
     * Saves changes made to duke.txt.
     * Exits from program.
     */
    private void exit() {
        try {
            storage.saveTasksToTempFile(tasks);
            boolean isEqual = storage.compareFile();
            ui.printResult(storage.save(isEqual));
        } catch (DukeFileException e) {
            ui.printResult(new CommandResult(e.getMessage()));
        } catch (IOException e) {
             ui.printResult(new CommandResult(MESSAGE_ERROR_COMPARING));
        }
        System.exit(0);
    }
}
