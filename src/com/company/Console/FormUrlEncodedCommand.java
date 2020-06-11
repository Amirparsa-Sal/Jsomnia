package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestHeader;

/**
 * Represents the form urlencoded command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class FormUrlEncodedCommand extends Command {

    /**
     * Constructor with no parameter
     */
    public FormUrlEncodedCommand() {
        super("-e", "--encoded", CommandType.ARGUMENTAL);
    }

    /**
     * Executes the form urlencoded command.
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        if (!Parser.isMatch(arg, Parser.FORM_DATA_REGEX)) {
            ConsoleUI.getInstance().raiseError("form urlencoded data is not in correct form[example: \"key1=value1&key2=value2\"]");
            ConsoleUI.getInstance().print("Are you sure you want to send the request?[Y/n]");
            String command = ConsoleUI.getInstance().getCommand();
            if (!command.equals("Y"))
                ConsoleUI.getInstance().exitWithMessage("Sending request canceled!");
        }
        request.setData(arg);
        request.setBodyType(Request.BodyType.URL_ENCODED);
        request.addHeader(new RequestHeader("Content-Type", request.getContentType()));
    }
}
