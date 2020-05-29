package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;

public class SaveCommand extends Command {

    public SaveCommand() {
        super("-S", "--save");
    }

    @Override
    public boolean execute(String arg, Request request) {
        RequestManager.getInstance().setSaveRequest(true);
        return true;
    }
}