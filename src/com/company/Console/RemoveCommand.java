package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;

public class RemoveCommand extends Command {

    public RemoveCommand() {
        super(null, "remove", CommandType.ARGUMENTAL);
    }

    @Override
    public void execute(String arg, Request request) {
        int numberOfRequests = RequestManager.getInstance().getNumberOfRequests();
        String[] syntax = arg.split(" ");
        for (String str : syntax) {
            boolean success = RequestManager.getInstance().deleteRequestFromList(Integer.parseInt(str));
            if (!success)
                ConsoleUI.getInstance().print("Request" + str + " does not exist!");
            else
                ConsoleUI.getInstance().print("Request" + str + " removed from list.");
        }
        RequestManager.getInstance().reArrangeList(numberOfRequests);
    }
}
