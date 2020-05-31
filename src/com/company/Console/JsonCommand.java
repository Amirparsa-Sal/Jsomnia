package com.company.Console;

import com.company.Logic.Request;

import java.util.regex.Pattern;

public class JsonCommand extends Command {

    public JsonCommand() {
        super("-j", "--json", CommandType.ARGUMENTAL);
    }

    @Override
    public boolean execute(String arg, Request request) {
        String save = arg.substring(0,arg.length());
        arg.replace('{','=');
        arg.replace('}','=');
        if(Parser.isMatch(arg,"\\\"=([a-zA-Z0-9]+:[a-zA-Z0-9]+,)*([a-zA-Z0-9]+:[a-zA-Z0-9]+)=\\\"")){
            ConsoleUI.getInstance().raiseError("Please right the json in correct form[example: \"{key1:value1,key2:value2\"]");
            return false;
        }
        request.setData(save);
        request.setJson(true);
        return true;
    }
}
