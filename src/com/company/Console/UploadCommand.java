package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestHeader;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Represents the upload command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class UploadCommand extends Command {

    /**
     * Constructor with no parameter
     */
    public UploadCommand() {
        super("-U", "--upload", CommandType.ARGUMENTAL);
    }

    /**
     * Executes the upload command.
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        if (!Files.exists(Paths.get(arg)))
            ConsoleUI.getInstance().raiseError("File not found! Please enter a valid absolute path.");
        request.setBodyType(Request.BodyType.BINARY_FILE);
        request.setData(arg);
    }
}
