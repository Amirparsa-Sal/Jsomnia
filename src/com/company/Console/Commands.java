package com.company.Console;

import java.util.ArrayList;

public class Commands {

    private static Commands instance = null;
    private ArrayList<Command> commandsList;

    private Commands() {
        commandsList = new ArrayList<>();
    }

    public static Commands getInstance() {
        if (instance == null)
            instance = new Commands();
        return instance;
    }

    public void add(Command command) {
        commandsList.add(command);
    }

    public void remove(Command command) {
        commandsList.remove(command);
    }

    public Command findCommandBySign(String sign) {
        for (Command command : commandsList)
            if ((command.getLongSign() != null && command.getLongSign().equals(sign)) || command.getOneLetterSign().equals(sign))
                return command;
        return null;
    }
}