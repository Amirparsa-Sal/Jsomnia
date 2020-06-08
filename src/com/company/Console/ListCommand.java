package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;

import javax.xml.bind.SchemaOutputResolver;

public class ListCommand extends Command {

    public ListCommand() {
        super(null , "list", CommandType.NON_ARGUMENTAL);
    }

    @Override
    public void execute(String arg, Request request) {
        RequestManager.getInstance().showListOfRequests();
        ConsoleUI.getInstance().print("------------------------------------");
    }
}
