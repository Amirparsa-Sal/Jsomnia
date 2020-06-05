package com.company.Console;

import com.company.Logic.Request;

public class FormDataCommand extends Command {

    public FormDataCommand() {
        super("-d", "--data", CommandType.ARGUMENTAL);
    }

    @Override
    public void execute(String arg, Request request) {
        if (!Parser.isMatch(arg, "[ ]*([a-zA-Z0-9-]+=[a-zA-Z0-9-]+[ ]*&[ ]*)*([a-zA-Z0-9-]+=[a-zA-Z0-9-]+)[ ]*"))
            ConsoleUI.getInstance().raiseError("Please right the form data in correct form[example: \"key1=value1&key2=value2\"]");
        request.setData(arg);
        request.setBodyType(Request.BodyType.FORM_DATA);
    }
}