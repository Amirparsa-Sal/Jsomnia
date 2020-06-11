package com.company.Console;

import com.company.Logic.Request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the output command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class OutputCommand extends Command {

    /**
     * Constructor with no parameter
     */
    public OutputCommand() {
        super("-O", "--output", CommandType.MULTI_TYPE);
    }

    /**
     * Executes the output command.
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        request.setOutput(true);
        if (arg == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
            LocalDateTime time = LocalDateTime.now();
            request.setOutputName("output_[" + formatter.format(time) + "]");
        } else {
            request.setOutputName(arg);
        }
    }
}