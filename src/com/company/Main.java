package com.company;

import com.company.Console.*;
import com.company.GUI.GUIManager;
import com.company.Logic.Response;

import javax.swing.*;
import java.io.IOException;


/**
 * A class for running the program
 *
 * @author Amiraprsa Salmankhah
 * @version 1.0.0
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
//        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        GUIManager guiManager = GUIManager.getInstance();
//        guiManager.showGUI();
        if (args.length == 0)
            return;
        ConsoleUI console = ConsoleUI.getInstance();
        Response response = console.ProcessCommand(args);
        if (response != null)
            ConsoleUI.getInstance().print(response.toString());
    }
}