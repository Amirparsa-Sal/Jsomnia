package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;

import java.io.File;
import java.util.Scanner;

public class ConsoleUI {

    private static ConsoleUI instance = null;
    private Scanner scanner;

    public static ConsoleUI getInstance() {
        if (instance == null)
            instance = new ConsoleUI();
        return instance;
    }

    public String getCommand() {
        String command = scanner.nextLine();
        return command;
    }

    public boolean ProcessCommand(String command) {
        Request request = Parser.commandToRequest(command);
        if (request == null)
            return false;
        RequestManager.getInstance().sendRequest(request);
        return true;
    }

    public void print(String response) {
        System.out.println(response);
    }

    public void raiseError(String errorMessage) {
        System.err.print("Error! " + errorMessage);
        System.exit(-1);
    }

    public void printFile(File file) {

    }
}
