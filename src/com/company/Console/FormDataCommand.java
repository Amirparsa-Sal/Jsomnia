package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestHeader;
import com.company.Logic.RequestManager;

/**
 * Represents the form data command of ConsoleUI.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class FormDataCommand extends Command {

    /**
     * Constructor with no parameter
     */
    public FormDataCommand() {
        super("-d", "--data", CommandType.ARGUMENTAL);
    }

    /**
     * Executes the form data command.
     *
     * @param arg     argument of the command
     * @param request corresponding request
     */
    @Override
    public void execute(String arg, Request request) {
        if (!Parser.isMatch(arg, Parser.FORM_DATA_REGEX)) {
            ConsoleUI.getInstance().raiseError("form data is not in correct form[example: \"key1=value1&key2=value2\"]");
            ConsoleUI.getInstance().print("Are you sure you want to send the request?[Y/n]");
            String command = ConsoleUI.getInstance().getCommand();
            if (!command.equals("Y"))
                ConsoleUI.getInstance().exitWithMessage("Sending request canceled!");
        }
        request.addHeader(new RequestHeader("Content-Type", "multipart/form-data" + "; boundary=" + RequestManager.getInstance().BOUNDARY));
        request.setData(arg);
        request.setBodyType(Request.BodyType.FORM_DATA);
    }
}