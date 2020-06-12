package com.company.Console;

import com.company.Logic.Parser;
import com.company.Logic.Request;
import com.company.Logic.RequestHeader;

/**
 * Represents the json command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class JsonCommand extends Command {

    /**
     * Constructor with no parameter
     */
    public JsonCommand() {
        super("-j", "--json", CommandType.ARGUMENTAL);
    }

    /**
     * Executes the json command.
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        if (request.getBodyType() != Request.BodyType.UNKNOWN)
            ConsoleUI.getInstance().raiseError("You can not use different body types simultaneously! (json or form data or binary)");
        arg = arg.replace('\'', '\"');
        request.setData(arg);
        request.setBodyType(Request.BodyType.JSON);
    }

}