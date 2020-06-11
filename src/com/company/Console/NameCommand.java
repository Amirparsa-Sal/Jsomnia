package com.company.Console;

import com.company.Logic.Request;

/**
 * Represents the name command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class NameCommand extends Command {

    /**
     * Constructor with no parameter
     */
    public NameCommand() {
        super("-N", "--name", CommandType.ARGUMENTAL);
    }

    /**
     * Executes the name command.
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        request.setName(arg);
    }
}
