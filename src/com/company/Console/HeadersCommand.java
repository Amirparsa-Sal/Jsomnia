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
        if (!Parser.isMatch(arg, "[ ]*([a-zA-Z0-9-]+:[a-zA-Z0-9-]+[ ]*;[ ]*)*([a-zA-Z0-9-]+:[a-zA-Z0-9-]+)[ ]*"))
            ConsoleUI.getInstance().raiseError("Please right the headers in correct form[example: \"key1:value1;key2:value2\"]");
        ArrayList<RequestHeader> headers = Parser.splitHeaders(arg);
        request.setHeaders(headers);
    }
}