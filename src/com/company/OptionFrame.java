package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

/**
 * Represents a class for the option page
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class OptionFrame extends JFrame {

    //size of the frame
    public static final Dimension size = new Dimension(300, 100);
    //Panel of checkboxes
    private JPanel checkBoxPanel;
    //Panel of buttons
    private JPanel buttons;

    /**
     * Constructor with no parameter
     */
    public OptionFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle("Options");
        this.setSize(size);
        this.setLocation(screenSize.width / 2 - this.getWidth() / 2, screenSize.height / 2 - this.getHeight() / 2);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.getContentPane().setBackground(GUIManager.bgColor);
        this.setResizable(false);
        checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.LINE_AXIS));
        checkBoxPanel.setBackground(GUIManager.bgColor);
        //follow redirect
        JCheckBox followRedirect = new JCheckBox("Follow redirection");
        followRedirect.setForeground(Color.WHITE);
        followRedirect.setAlignmentX(Component.CENTER_ALIGNMENT);
        //system tray
        JCheckBox systemTray = new JCheckBox("System tray");
        systemTray.setForeground(Color.WHITE);
        systemTray.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkBoxPanel.add(followRedirect);
        checkBoxPanel.add(systemTray);
        //button
        buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        buttons.setMaximumSize(new Dimension(120, 30));
        buttons.setBackground(GUIManager.bgColor);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        buttons.add(okButton);
        buttons.add(cancelButton);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionFrame.this.save();
                OptionFrame.this.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionFrame.this.load();
                OptionFrame.this.dispose();
            }
        });
        this.add(checkBoxPanel);
        this.add(buttons);
        File file = new File(new File(".").getAbsolutePath() + "\\Data\\options.dat");
        if (file.exists())
            this.load();
    }

    private void load() {
        try {
            File file = new File(new File(".").getAbsolutePath() + "\\Data\\options.dat");
            Scanner scanner = new Scanner(file);
            this.setFollowRedirect(scanner.next().equals("true"));
            this.setSystemTray(scanner.next().equals("true"));
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.exit(-1);
        }

    }

    private void save() {
        try {
            String data = getFollowRedirect().toString() + " " + getSystemTray().toString();
            PrintWriter printWriter = new PrintWriter(new File(".").getAbsolutePath() + "\\Data\\options.dat", "UTF-8");
            printWriter.print(data);
            printWriter.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
    }

    /**
     * Checkbox panel getter
     *
     * @return Checkbox panel
     */
    public JPanel getCheckBoxPanel() {
        return checkBoxPanel;
    }

    /**
     * OK button getter
     *
     * @return OK button
     */
    public JButton getOkButton() {
        return (JButton) (buttons.getComponent(0));
    }

    /**
     * Cancel button getter
     *
     * @return Cancel button
     */
    public JButton getCancelButton() {
        return (JButton) (buttons.getComponent(1));
    }

    /**
     * Follow redirect getter
     *
     * @return Follow redirect
     */
    public Boolean getFollowRedirect() {
        return ((JCheckBox) checkBoxPanel.getComponent(0)).isSelected();
    }

    /**
     * System tray getter
     *
     * @return System tray
     */
    public Boolean getSystemTray() {
        return ((JCheckBox) checkBoxPanel.getComponent(1)).isSelected();
    }

    /**
     * FolLow redirect setter
     *
     * @param isSelected Follow redirect mode
     */
    public void setFollowRedirect(boolean isSelected) {
        ((JCheckBox) checkBoxPanel.getComponent(0)).setSelected(isSelected);
    }

    /**
     * System tray getter
     *
     * @param isSelected System tray mode
     */
    public void setSystemTray(boolean isSelected) {
        ((JCheckBox) checkBoxPanel.getComponent(1)).setSelected(isSelected);
    }
}