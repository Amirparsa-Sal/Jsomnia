package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;
import com.company.Logic.Response;

public class FireCommand extends Command {

    public FireCommand() {
        super(null, "fire", CommandType.ARGUMENTAL);
    }

    @Override
    public void execute(String arg, Request request) {
        String[] syntax = arg.split(" ");
        for (int i = 0; i < syntax.length; i++) {
            Request ourRequest = RequestManager.getInstance().loadRequestFromList(Integer.parseInt(syntax[i]));
            if (ourRequest == null)
                ConsoleUI.getInstance().raiseError("Request " + syntax[i] + " not found!");
            Response response = RequestManager.getInstance().sendRequest(ourRequest);
            System.out.println("Request " + syntax[i] + ":");
            if (response != null)
                ConsoleUI.getInstance().print(response.toString());
            ConsoleUI.getInstance().print("------------------------------------");
        }
    }
}
