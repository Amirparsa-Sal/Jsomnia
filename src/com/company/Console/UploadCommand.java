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
        if (!Files.exists(Paths.get(arg)))
            ConsoleUI.getInstance().raiseError("File not found! Please enter a valid absolute path.");
        request.setBodyType(Request.BodyType.BINARY_FILE);
        request.setData(arg);
    }
}
