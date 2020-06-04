package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputCommand extends Command {

    public OutputCommand() {
        super("-O", "--output", CommandType.MULTI_TYPE);
    }

    @Override
    public void execute(String arg, Request request) {
        RequestManager.getInstance().setOutput(true);
        if (arg == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
            LocalDateTime time = LocalDateTime.now();
            RequestManager.getInstance().setOutputName("output_[" + formatter.format(time) + "]");
        } else {
            if (arg.charAt(0) != '\"' || arg.charAt(arg.length() - 1) != '\"')
                ConsoleUI.getInstance().raiseError("Please place the --upload arguments in double quotes.");
            RequestManager.getInstance().setOutputName(arg.substring(1, arg.length() - 1));
        }
    }
}