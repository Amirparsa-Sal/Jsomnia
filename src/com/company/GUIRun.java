package com.company;

import com.company.GUI.GUIManager;

import javax.swing.*;
import java.io.IOException;


/**
 * A class for running the GUI
 *
 * @author Amiraprsa Salmankhah
 * @version 1.0.0
 */
public class GUIRun {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        GUIManager guiManager = GUIManager.getInstance();
        guiManager.showGUI();
    }
}