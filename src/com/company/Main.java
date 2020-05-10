package com.company;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    /*
    javax.swing.UIManager$LookAndFeelInfo[Metal javax.swing.plaf.metal.MetalLookAndFeel]
    javax.swing.UIManager$LookAndFeelInfo[Nimbus javax.swing.plaf.nimbus.NimbusLookAndFeel]
    javax.swing.UIManager$LookAndFeelInfo[CDE/Motif com.sun.java.swing.plaf.motif.MotifLookAndFeel]
    javax.swing.UIManager$LookAndFeelInfo[Windows com.sun.java.swing.plaf.windows.WindowsLookAndFeel]
    javax.swing.UIManager$LookAndFeelInfo[Windows Classic com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel]
     */
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        GUIManager guiManager = GUIManager.getInstance();
        guiManager.showGUI();
    }
}