package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;
import com.company.Logic.Response;

import java.util.Scanner;

public class ConsoleUI {

    private static ConsoleUI instance = null;
    private static Scanner scanner;

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

    public static ConsoleUI getInstance() {
        if (instance == null) {
            instance = new ConsoleUI();
            scanner = new Scanner(System.in);
            init();
        }
        return instance;
    }

    public String getCommand() {
        String command = scanner.nextLine();
        return command;
    }

    public Response ProcessCommand(String[] command) {
        Request request = Parser.commandToRequest(command);
        if (request == null || request.isEmpty())
            return null;
        return RequestManager.getInstance().sendRequest(request);
    }

    public void print(String response) {
        System.out.println(response);
    }

    public void raiseError(String errorMessage) {
        System.err.print("Error! " + errorMessage);
        System.exit(-1);
    }

    public void exitWithMessage(String message) {
        print(message);
        System.exit(-1);
    }
}
