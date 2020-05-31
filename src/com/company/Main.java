package com.company;

import com.company.Console.*;
import com.company.GUI.GUIManager;
import com.company.Logic.Request;
import com.company.Logic.RequestManager;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;


/**
 * A class for running the program
 *
 * @author Amiraprsa Salmankhah
 * @version 1.0.0
 */
public class Main {
    public static void initCommands() {
        FollowRedirectCommand frc = new FollowRedirectCommand();
        FormDataCommand fdc = new FormDataCommand();
        HeadersCommand hc = new HeadersCommand();
        JsonCommand jc = new JsonCommand();
        MethodCommand mc = new MethodCommand();
        OutputCommand oc = new OutputCommand();
        ResponseVisibilityCommand rvc = new ResponseVisibilityCommand();
        SaveCommand sc = new SaveCommand();
        HelpCommand hp = new HelpCommand();
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
//        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        GUIManager guiManager = GUIManager.getInstance();
//        guiManager.showGUI();
        initCommands();
        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        Request request = Parser.commandToRequest(command);
        RequestManager.getInstance().sendRequest(request);
//        System.out.println(request);

//        initCommands();
//        ConsoleUI console = ConsoleUI.getInstance();
//        console.ProcessCommand(Parser.strArrToString(args))
    }
}