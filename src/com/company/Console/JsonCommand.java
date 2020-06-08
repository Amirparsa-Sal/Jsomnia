package com.company.Console;

import com.company.Logic.Request;

public class JsonCommand extends Command {

    public JsonCommand() {
        super("-j", "--json", CommandType.ARGUMENTAL);
    }

    @Override
    public void execute(String arg, Request request) {
        if (request.getBodyType() != Request.BodyType.UNKNOWN)
            ConsoleUI.getInstance().raiseError("You can not use different body types simultaneously! (json or form data or binary)");
        arg = arg.replace('\'', '\"');
        String save = arg;
        arg = arg.replace('{', '=');
        arg = arg.replace('}', '=');
        if (!Parser.isMatch(arg, "=[ ]*(\\\"[a-zA-Z0-9-]+\\\":\\\"[a-zA-Z0-9-]+\\\"[ ]*,[ ]*)*(\\\"[a-zA-Z0-9-]+\\\":\\\"[a-zA-Z0-9-]+\\\")[ ]*=")) {
            ConsoleUI.getInstance().print("The json data is not in correct format[example: \"{'key1':'value1','key2':'value2'}\"]");
            ConsoleUI.getInstance().print("Are you sure you want to send the request?[Y/n]");
            String command = ConsoleUI.getInstance().getCommand();
            if(!command.equals("Y"))
                ConsoleUI.getInstance().exitWithMessage("Sending request canceled!");
        }
        request.setData(save);
        request.setBodyType(Request.BodyType.JSON);
    }
}