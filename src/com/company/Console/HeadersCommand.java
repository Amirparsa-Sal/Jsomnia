package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestHeader;

import java.util.ArrayList;

public class HeadersCommand extends Command {

    public HeadersCommand() {
        super("-H", "--headers", CommandType.ARGUMENTAL);
    }

    @Override
    public void execute(String arg, Request request) {
        if (arg == null)
            return;
        if (!Parser.isMatch(arg, "[ ]*([a-zA-Z0-9-]+:[a-zA-Z0-9-]+[ ]*;[ ]*)*([a-zA-Z0-9-]+:[a-zA-Z0-9-]+)[ ]*")) {
            ConsoleUI.getInstance().raiseError("headers are not in correct format[example: \"key1:value1;key2:value2\"]");
            ConsoleUI.getInstance().print("Are you sure you want to send the request?[Y/n]");
            String command = ConsoleUI.getInstance().getCommand();
            if(!command.equals("Y"))
                ConsoleUI.getInstance().exitWithMessage("Sending request canceled!");
        }
        ArrayList<RequestHeader> headers = Parser.splitHeaders(arg);
        request.setHeaders(headers);
    }
}