import java.util.Scanner;

public class Duke {

    // list the tasks
    public static void listTasks(String[] list, int count){
        if (count == 0){
            System.out.println("    ____________________________________________________________");
            System.out.println("     There is currently no task.");
            System.out.println("    ____________________________________________________________\n");
        }
        else{
            System.out.println("    ____________________________________________________________");
            for(int i = 1; i <= count; i++){
                System.out.println("     " + i + ". " + list[i-1]);
            }
            System.out.println("    ____________________________________________________________\n");
        }
    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo);

        System.out.println("    ____________________________________________________________");
        System.out.println("     Hello! I'm Duke");
        System.out.println("     What can I do for you?");
        System.out.println("    ____________________________________________________________\n");

        // take in input from user
        String userInput;
        Scanner in = new Scanner(System.in);
        userInput = in.nextLine();
        String[] list = new String[100];
        int count = 0;
        while (!userInput.equalsIgnoreCase("bye")) {
            if (userInput.equalsIgnoreCase("list")){
                listTasks(list, count);
            }
            else {
                list[count] = userInput;
                System.out.println("    ____________________________________________________________");
                System.out.println("     added: " + userInput);
                System.out.println("    ____________________________________________________________\n");
                count++;
            }
            userInput = in.nextLine();
        };

        System.out.println("    ____________________________________________________________");
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
    }
}
