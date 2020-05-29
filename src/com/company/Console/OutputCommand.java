package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputCommand extends Command {

    public OutputCommand() {
        super("-O", "--output");
    }

    @Override
    public boolean execute(String arg, Request request) {
        RequestManager.getInstance().setOutput(true);
        if (arg == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
            LocalDateTime time = LocalDateTime.now();
            RequestManager.getInstance().setOutputName("output_[" + formatter.format(time) + "]");
        } else
            RequestManager.getInstance().setOutputName(arg);
        return true;
    }
}