package com.company.Console;

import com.company.Logic.Request;

public class JsonCommand extends Command {

    public JsonCommand() {
        super("-j", "--json");
    }

    @Override
    public boolean execute(String arg, Request request) {
        request.setData(arg);
        request.setJson(true);
        return true;
    }
}
