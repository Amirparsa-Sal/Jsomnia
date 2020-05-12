package com.company;

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
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        GUIManager guiManager = GUIManager.getInstance();
        guiManager.showGUI();
    }
}