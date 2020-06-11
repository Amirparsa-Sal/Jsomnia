package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;

/**
 * Represents the save command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class SaveCommand extends Command {

    /**
     * Constructor with no parameter
     */
    public SaveCommand() {
        super("-S", "--save", CommandType.NON_ARGUMENTAL);
    }

    /**
     * Executes the save command.
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        RequestManager.getInstance().setSaveRequest(true);
    }
}