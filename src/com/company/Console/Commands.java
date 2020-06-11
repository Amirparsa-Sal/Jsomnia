package com.company.Console;

import java.util.ArrayList;

/**
 * Represents a class for storing commands. This class is designed in singleton designing pattern.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class Commands {

    //Only instance of the class
    private static Commands instance = null;
    //List of the commands
    private ArrayList<Command> commandsList;

    /**
     * Constructor with no parameter
     */
    private Commands() {
        commandsList = new ArrayList<>();
    }

    /**
     * Gets the only instance of the class
     *
     * @return The only instance of the class
     */
    public static Commands getInstance() {
        if (instance == null)
            instance = new Commands();
        return instance;
    }

    /**
     * Adds a command to the list
     *
     * @param command The command to be added
     */
    public void add(Command command) {
        commandsList.add(command);
    }

    /**
     * Removes a command from the list
     *
     * @param command The command to remove
     */
    public void remove(Command command) {
        commandsList.remove(command);
    }

    /**
     * Finds command by its sign
     *
     * @param sign Sign of the command
     * @return The command
     */
    public Command findCommandBySign(String sign) {
        for (Command command : commandsList)
            if ((command.getLongSign() != null && command.getLongSign().equals(sign)) || (command.getOneLetterSign() != null && command.getOneLetterSign().equals(sign)))
                return command;
        return null;
    }

    /**
     * Checks if a command exists with an specific sign
     *
     * @param sign Sign of the command
     * @return True if yes and false if not
     */
    public boolean isCommand(String sign) {
        for (Command command : commandsList)
            if ((command.getLongSign() != null && command.getLongSign().equals(sign)) || (command.getOneLetterSign() != null && command.getOneLetterSign().equals(sign)))
                return true;
        return false;
    }
}