package com.company.Console;

import com.company.Logic.Request;

public class FormDataCommand extends Command {

    public FormDataCommand() {
        super("-d", "--data", CommandType.ARGUMENTAL);
    }

    @Override
    public boolean execute(String arg, Request request) {
        if(!Parser.isMatch(arg,"\\\"([a-zA-Z0-9]+=[a-zA-Z0-9]+&)*([a-zA-Z0-9]+=[a-zA-Z0-9]+)\\\"")){
            ConsoleUI.getInstance().raiseError("Please right the form data in correct form[example: \"key1=value1&key2=value2\"]");
            return false;
        }
        request.setData(arg);
        request.setJson(false);
        return true;
    }
}
