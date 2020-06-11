package com.company.Console;

import com.company.Logic.Request;

/**
 * Represents a command in ConsoleUI
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public abstract class Command {
    //one letter sign of the command
    private String oneLetterSign;
    //long sign of the command
    private String longSign;
    //type of the command
    private CommandType type;

    /**
     * Constructor with 3 paramaters.
     *
     * @param oneLetterSign one letter sign of the command
     * @param longSign      long sign of the command
     * @param type          type of the command
     */
    protected Command(String oneLetterSign, String longSign, CommandType type) {
        Commands.getInstance().add(this);
        this.oneLetterSign = oneLetterSign;
        this.longSign = longSign;
        this.type = type;
    }

    /**
     * Long sign getter
     *
     * @return long sign of the command
     */
    public String getLongSign() {
        return longSign;
    }

    /**
     * One letter sign getter
     *
     * @return one letter sign of the command
     */
    public String getOneLetterSign() {
        return oneLetterSign;
    }

    /**
     * Command type getter
     *
     * @return command type
     */
    public CommandType getType() {
        return type;
    }

    /**
     * Executes the command
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    public abstract void execute(String arg, Request request);

    /**
     * Represents an enum for command types
     */
    public enum CommandType {
        ARGUMENTAL, NON_ARGUMENTAL, MULTI_TYPE
    }
}