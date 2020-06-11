package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;

/**
 * Represents the list command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class ListCommand extends Command {

    /**
     * Constructor with no parameter
     */
    public ListCommand() {
        super(null, "list", CommandType.NON_ARGUMENTAL);
    }

    /**
     * Executes the list command.
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        RequestManager.getInstance().showListOfRequests();
    }
}
