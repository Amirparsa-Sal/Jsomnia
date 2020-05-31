package com.company.Console;

import com.company.Logic.Request;

public class ResponseVisibilityCommand extends Command {

    public ResponseVisibilityCommand() {
        super("-i", null, CommandType.NON_ARGUMENTAL);
    }

    @Override
    public boolean execute(String arg, Request request) {
        request.setResponseVisibility(true);
        return true;
    }
}