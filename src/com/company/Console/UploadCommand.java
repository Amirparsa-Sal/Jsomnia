package com.company.Console;

import com.company.Logic.Request;

import java.nio.file.Files;
import java.nio.file.Paths;

public class UploadCommand extends Command {

    public UploadCommand() {
        super("-U", "--upload", CommandType.ARGUMENTAL);
    }

    @Override
    public void execute(String arg, Request request) {
        System.out.println(arg);
        if (arg.charAt(0) != '\"' || arg.charAt(arg.length() - 1) != '\"')
            ConsoleUI.getInstance().raiseError("Please enter --upload parameter in double quotes.");
        arg = arg.substring(1, arg.length() - 1);
        System.out.println(arg);
        if (!Files.exists(Paths.get(arg)))
            ConsoleUI.getInstance().raiseError("File not found! Please enter a valid absolute path.");
        request.setBodyType(Request.BodyType.BINARY_FILE);
        request.setData(arg);
    }
}
