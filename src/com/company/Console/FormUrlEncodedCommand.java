package com.company.Console;

import com.company.Logic.Request;

public class FormUrlEncodedCommand extends Command {

    public FormUrlEncodedCommand() {
        super("-e", "--encoded", CommandType.ARGUMENTAL);
    }

    @Override
    public void execute(String arg, Request request) {
        if (!Parser.isMatch(arg, "[ ]*([a-zA-Z0-9-]+=[a-zA-Z0-9-]+[ ]*&[ ]*)*([a-zA-Z0-9-]+=[a-zA-Z0-9-]+)[ ]*")) {
            ConsoleUI.getInstance().raiseError("form urlencoded data is not in correct form[example: \"key1=value1&key2=value2\"]");
            ConsoleUI.getInstance().print("Are you sure you want to send the request?[Y/n]");
            String command = ConsoleUI.getInstance().getCommand();
            if (!command.equals("Y"))
                ConsoleUI.getInstance().exitWithMessage("Sending request canceled!");
        }
        request.setData(arg);
        request.setBodyType(Request.BodyType.URL_ENCODED);
    }
}
