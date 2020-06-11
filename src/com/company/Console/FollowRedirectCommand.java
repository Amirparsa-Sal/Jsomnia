package com.company.Console;

import com.company.Logic.Request;

/**
 * Represents the follow redirect command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class FollowRedirectCommand extends Command {

    /**
     * Constructor with no parameter
     */
    public FollowRedirectCommand() {
        super("-f", null, CommandType.NON_ARGUMENTAL);
    }

    /**
     * Executes the follow redirect command
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        request.setFollowRedirection(true);
    }
}