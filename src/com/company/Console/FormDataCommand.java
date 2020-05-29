package com.company.Console;

import com.company.Logic.Request;

public class FormDataCommand extends Command {

    public FormDataCommand() {
        super("-d", "--data");
    }

    @Override
    public boolean execute(String arg, Request request) {
        request.setData(arg);
        request.setJson(false);
        return true;
    }
}
