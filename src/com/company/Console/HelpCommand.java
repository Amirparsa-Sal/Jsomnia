package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;

import java.io.Console;

public class HelpCommand extends Command{

    public HelpCommand() {
        super("-h", "--help", Command.CommandType.NON_ARGUMENTAL);
    }

    @Override
    public boolean execute(String arg, Request request) {
        ConsoleUI.getInstance().print("-d --data : adds multipart/form-data [example: -d \"key1=value1&key2=value2\"]\n" +
                "-f : turns on follow redirect for the request\n" +
                "-H --header <headers>: adds headers to the request [example: -H \"key1:value1;key2:value2\"]\n" +
                "-h --help : shows list of commands\n" +
                "-j --json <json data>: adds json data [example: -j \"{key1:value1,key2:value2}\"]\n" +
                "-M -method <method name>: sets the request method [Methods: GET,POST,PUT,PATCH,DELETE]\n" +
                "-O --output [=file_name]: downloads output of the request\n" +
                "-i : turns on response visibility\n" +
                "-S --save : saves request to the list\n");
        return true;
    }
}
