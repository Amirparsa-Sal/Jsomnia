package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestHeader;

import java.util.ArrayList;

public class HeadersCommand extends Command {

    public HeadersCommand() {
        super("-H", "--headers");
    }

    @Override
    public boolean execute(String arg, Request request) {
        if (arg == null)
            return true;
        ArrayList<RequestHeader> headers = Parser.splitHeaders(arg);
        if (headers == null) {
            ConsoleUI.getInstance().printError("Please right the headers in correct form");
            return false;
        }
        request.setHeaders(headers);
        return true;
    }
}
