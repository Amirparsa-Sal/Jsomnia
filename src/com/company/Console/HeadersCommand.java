package com.company.Console;

import com.company.Logic.Parser;
import com.company.Logic.Request;
import com.company.Logic.RequestHeader;

import java.util.ArrayList;

/**
 * Represents the header command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class HeadersCommand extends Command {

    /**
     * Constructor with no parameter.
     */
    public HeadersCommand() {
        super("-H", "--headers", CommandType.ARGUMENTAL);
    }

    /**
     * Executes the header command.
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        if (arg == null)
            return;
        if (!Parser.isMatch(arg, Parser.HEADER_REGEX)) {
            ConsoleUI.getInstance().raiseError("headers are not in correct format[example: \"key1:value1;key2:value2\"]");
            ConsoleUI.getInstance().print("Are you sure you want to send the request?[Y/n]");
            String command = ConsoleUI.getInstance().getCommand();
            if (!command.equals("Y"))
                ConsoleUI.getInstance().exitWithMessage("Sending request canceled!");
        }
        ArrayList<RequestHeader> headers = Parser.splitHeaders(arg);
        for (RequestHeader header : headers)
            request.addHeader(header);
    }
}