package com.company.Console;

import com.company.Logic.Request;

public abstract class Command {

    private String oneLetterSign;
    private String longSign;

    protected Command(String oneLetterSign, String longSign) {
        Commands.getInstance().add(this);
        this.oneLetterSign = oneLetterSign;
        this.longSign = longSign;
    }

    public String getLongSign() {
        return longSign;
    }

    public String getOneLetterSign() {
        return oneLetterSign;
    }

    public abstract boolean execute(String arg, Request request);
}