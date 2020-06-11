package com.company.Console;

import com.company.Logic.Request;

/**
 * Represents the help command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class HelpCommand extends Command {

    /**
     * Constructor with no parameter.
     */
    public HelpCommand() {
        super("-h", "--help", Command.CommandType.NON_ARGUMENTAL);
    }

    /**
     * Executes the help command.
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        ConsoleUI.getInstance().print("-d --data <data> : adds multipart/form-data [example: -d \"key1=value1&key2=value2\"]\n" +
                "-e --encoded <data> : adds form urlencoded data [example: -e \"key1=value1&key2=value2\"]\n" +
                "-f : turns on follow redirect for the request\n" +
                "-H --header <headers>: adds headers to the request [example: -H \"key1:value1;key2:value2\"]\n" +
                "-h --help : shows list of commands\n" +
                "-j --json <json data>: adds json data [example: -j \"{'key1':value1','key2':'value2'}\"]\n" +
                "-M --method <method name>: sets the request method [Methods: GET,POST,PUT,DELETE]\n" +
                "-N --name <request name>: sets the request name\n" +
                "-O --output [=file_name]: downloads output of the request\n" +
                "-i : turns on response visibility\n" +
                "-S --save : saves request to the list\n" +
                "list : shows list of the requests\n" +
                "fire <RequestNumber> ... <RequestNumber> : executes the saved requests\n" +
                "remove <RequestNumber> ... <RequestNumber> : removes the requests");
    }
}