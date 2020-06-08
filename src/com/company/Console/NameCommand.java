package com.company.Console;

import com.company.Logic.Request;

public class NameCommand extends Command {

    public NameCommand() {
        super("-N", "--name", CommandType.ARGUMENTAL);
    }

    @Override
    public void execute(String arg, Request request) {
        request.setName(arg);
    }
}
