package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;
import com.company.Logic.Response;

import java.util.Scanner;

/**
 * Represents a Jsomnia console. This class is designed in singleton pattern.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class ConsoleUI {
    //The only instance of the class
    private static ConsoleUI instance = null;
    //Scanner object of the class
    private static Scanner scanner;

    //initializes the commands
    private static void init() {
        FollowRedirectCommand frc = new FollowRedirectCommand();
        FormDataCommand fdc = new FormDataCommand();
        HeadersCommand hc = new HeadersCommand();
        JsonCommand jc = new JsonCommand();
        MethodCommand mc = new MethodCommand();
        OutputCommand oc = new OutputCommand();
        ResponseVisibilityCommand rvc = new ResponseVisibilityCommand();
        SaveCommand sc = new SaveCommand();
        HelpCommand hp = new HelpCommand();
        UploadCommand uc = new UploadCommand();
        NameCommand nc = new NameCommand();
        FireCommand fc = new FireCommand();
        ListCommand lc = new ListCommand();
        RemoveCommand rc = new RemoveCommand();
        FormUrlEncodedCommand fue = new FormUrlEncodedCommand();
    }

    /**
     * Gets the only instance of the class
     *
     * @return The only instance of the class
     */
    public static ConsoleUI getInstance() {
        if (instance == null) {
            instance = new ConsoleUI();
            scanner = new Scanner(System.in);
            init();
        }
        return instance;
    }

    /**
     * Gets a command from user
     *
     * @return Command as string
     */
    public String getCommand() {
        String command = scanner.nextLine();
        return command;
    }

    /**
     * executes the command taken from user
     *
     * @param command The command
     * @return Response of the request (null if request is null or request is empty or request does not have URL)
     */
    public Response ProcessCommand(String[] command) {
        Request request = Parser.commandToRequest(command);
        if (request == null || request.isEmpty())
            return null;
        if (request.getUrl() == null)
            raiseError("You have not entered the request url!");
        return RequestManager.getInstance().sendRequest(request);
    }

    /**
     * Prints in terminal
     *
     * @param response Text of the message
     */
    public void print(String response) {
        System.out.println(response);
    }

    /**
     * Prints an error in terminal
     *
     * @param errorMessage Text of the error
     */
    public void raiseError(String errorMessage) {
        System.err.print("Error! " + errorMessage);
        System.exit(-1);
    }

    /**
     * Prints a message and terminates the program.
     *
     * @param message Text of the message
     */
    public void exitWithMessage(String message) {
        print(message);
        System.exit(-1);
    }
}
