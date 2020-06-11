package com.company.Console;

import com.company.Logic.Request;

/**
 * Represents the response visibility command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class ResponseVisibilityCommand extends Command {

    /**
     * Constructor with no parameter
     */
    public ResponseVisibilityCommand() {
        super("-i", null, CommandType.NON_ARGUMENTAL);
    }

    /**
     * Executes the response visibility command.
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        request.setResponseVisibility(true);
    }
}