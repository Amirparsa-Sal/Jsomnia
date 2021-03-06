package com.company.Console;

import com.company.Logic.Parser;
import com.company.Logic.Request;

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
        String save = arg;
        if (!Parser.isJson(arg)) {
            ConsoleUI.getInstance().print("The json data is not in correct format[example: \"{'key1':'value1','key2':'value2'}\"]");
            ConsoleUI.getInstance().print("Are you sure you want to send the request?[Y/n]");
            String command = ConsoleUI.getInstance().getCommand();
            if (!command.equals("Y"))
                ConsoleUI.getInstance().exitWithMessage("Sending request canceled!");
        }
        request.setData(arg);
        request.setBodyType(Request.BodyType.JSON);
    }
}