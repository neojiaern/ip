package duke.ui;

import duke.commands.CommandResult;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Deals with interactions with the user.
 */
public class Ui {

    private final Scanner in;
    private final PrintStream out;

    public static final String INDENTATION = "    ";
    public static final String LINE = INDENTATION
            + "____________________________________________________________";

    public Ui() {
        this(System.in, System.out);
    }

    public Ui(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    public String getUserInput() {
        return in.nextLine();
    }

    /**
     * Prints error message when loading tasks from duke.txt.
     */
    public void showLoadingError() {
        out.println("(No previously loaded task from duke.txt)");
    }

    public void printLine() {
        out.println(LINE);
    }

    public void printBlankLine() {
        out.println();
    }

    /**
     * Prints result of command execution.
     *
     * @param result contains message to be printed to user.
     */
    public void printResult(CommandResult result) {
        out.println(result.toString());
    }

    public void printGreetMsg() {
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
}
