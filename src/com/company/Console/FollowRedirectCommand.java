package com.company.Console;

import com.company.Logic.Request;

public class FollowRedirectCommand extends Command {

    public FollowRedirectCommand() {
        super("-f", null, CommandType.NON_ARGUMENTAL);
    }

    @Override
    public boolean execute(String arg, Request request) {
        request.setFollowRedirection(true);
        return true;
    }
}