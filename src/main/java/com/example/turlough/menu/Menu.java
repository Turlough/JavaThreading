package com.example.turlough.menu;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by turlough on 21/07/16.
 */
public class Menu {


    private Map<Integer, Command> commands = new HashMap<>(5);
    private PrintStream out = System.out;
    private int count = 0;
    Scanner scanner = new Scanner(System.in);

    public Menu() {

    }

    void show() {

        int choice = 0;


        do {

            System.out.println();
            System.out.println("______________________________________________________________________");

            commands.forEach((index, command) -> out.printf(
                    "\t| %d->\t%s\n",
                    index,
                    command.getDescription())
            );

            out.println();
            out.print("Enter choice, or zero to exit:\t");


            choice = scanner.nextInt();
            if (choice == 0) break;
            commands.get(choice).getRunnable().run();
            out.println("Done");

        } while (choice != 0);

        System.out.println("Exiting");
    }

    void add(String description, Runnable runnable) {
        Command command = new Command(description, runnable);
        commands.put(++count, command);
    }

    //<editor-fold desc = "Command: Inner class">
    private class Command {
        String description;
        Runnable runnable;

        public Command(String description, Runnable runnable) {
            this.description = description;
            this.runnable = runnable;
        }

        public String getDescription() {
            return description;
        }


        public Runnable getRunnable() {
            return runnable;
        }

    }
    //</editor-fold>
}
