package com.company.Console;

import com.company.Logic.Request;

public abstract class Command {
    public static enum CommandType {
        ARGUMENTAL, NON_ARGUMENTAL, MULTI_TYPE;
    }

    private String oneLetterSign;
    private String longSign;
    private CommandType type;

    protected Command(String oneLetterSign, String longSign, CommandType type) {
        Commands.getInstance().add(this);
        this.oneLetterSign = oneLetterSign;
        this.longSign = longSign;
        this.type = type;
    }

    public String getLongSign() {
        return longSign;
    }

    public String getOneLetterSign() {
        return oneLetterSign;
    }

    public CommandType getType() {
        return type;
    }

    public abstract boolean execute(String arg, Request request);
}