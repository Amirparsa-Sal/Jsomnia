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