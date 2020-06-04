package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestMethod;


public class MethodCommand extends Command {

    public MethodCommand() {
        super("-M", "--method", CommandType.ARGUMENTAL);
    }

    @Override
    public void execute(String arg, Request request) {
        if (!RequestMethod.isMethod(arg))
            ConsoleUI.getInstance().raiseError(arg + " is not a request method!");
        request.setRequestMethod(RequestMethod.getMethod(arg));
    }
}
