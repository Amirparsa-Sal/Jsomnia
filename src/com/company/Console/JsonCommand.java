package com.company.Console;

import com.company.Logic.Request;

public class JsonCommand extends Command {

    public JsonCommand() {
        super("-j", "--json", CommandType.ARGUMENTAL);
    }

    @Override
    public void execute(String arg, Request request) {
        if (request.getBodyType() != Request.BodyType.UNKNOWN) {
            System.out.println(request.getBodyType());
            ConsoleUI.getInstance().raiseError("You can not use different body types simultaneously! (json or form data or binary)");
        }
        String save = arg;
        arg = arg.replace('{', '=');
        arg = arg.replace('}', '=');
        if (!Parser.isMatch(arg, "=([ ]*[a-zA-Z0-9-]+:[a-zA-Z0-9-]+[ ]*,[ ]*)*([a-zA-Z0-9-]+:[a-zA-Z0-9-]+)[ ]*="))
            ConsoleUI.getInstance().raiseError("Please right the json in correct form[example: \"{key1:value1,key2:value2\"}]");
        request.setData(save);
        request.setBodyType(Request.BodyType.JSON);
    }
}