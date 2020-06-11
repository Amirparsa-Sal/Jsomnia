package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestMethod;

/**
 * Represents the method command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class MethodCommand extends Command {

    /**
     * Constructor with no parameter.
     */
    public MethodCommand() {
        super("-M", "--method", CommandType.ARGUMENTAL);
    }

    /**
     * Executes the method command.
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        if (!RequestMethod.isMethod(arg))
            ConsoleUI.getInstance().raiseError(arg + " is not a request method!");
        request.setRequestMethod(RequestMethod.getMethod(arg));
    }
}
