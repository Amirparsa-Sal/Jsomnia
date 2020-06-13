package com.company;

import com.company.Console.ConsoleUI;
import com.company.Logic.Response;

/**
 * A class for running ConsoleUI
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class ConsoleRun {
    public static void main(String[] args) {
        if (args.length == 0)
            return;
        ConsoleUI console = ConsoleUI.getInstance();
        Response response = console.ProcessCommand(args);
        if (response != null)
            ConsoleUI.getInstance().print(response.toString());
    }
}
