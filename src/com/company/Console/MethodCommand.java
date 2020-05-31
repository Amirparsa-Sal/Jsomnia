package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestMethod;


public class MethodCommand extends Command {

    public MethodCommand() {
        super("-M", "--method",CommandType.ARGUMENTAL);
    }

    @Override
    public boolean execute(String arg, Request request) {
        if (!RequestMethod.isMethod(arg)) {
            ConsoleUI.getInstance().raiseError(arg + " is not a request method!");
            return false;
        }
        request.setRequestMethod(RequestMethod.getMethod(arg));
        return true;
    }
}