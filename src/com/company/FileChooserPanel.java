package com.company;

import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Represents a class for choosing file
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class FileChooserPanel extends JPanel {

    //Background color
    private Color bgColor;
    //description label
    private final JLabel label = new JLabel("Selected File: ");
    //Address text field
    private final JTextField addressField = new JTextField("No file selected");
    //Reset button
    private JButton resetButton;
    //Load button
    private JButton loadButton;

    /**
     * Constructor with 2 parameters
     *
     * @param bgColor Background color of the panel
     * @param size    Size of the panel
     */
    public FileChooserPanel(Color bgColor, Dimension size) {
        Color color = new Color(bgColor.getRed() - 10, bgColor.getGreen() - 10, bgColor.getBlue() - 10);
        this.bgColor = bgColor;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(bgColor);
        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.LINE_AXIS));
        addressPanel.setBackground(bgColor);
        label.setBackground(bgColor);
        label.setForeground(Color.WHITE);
        addressField.setBackground(bgColor);
        addressField.setForeground(Color.WHITE);
        addressField.setEditable(false);
        addressField.setFocusable(false);
        addressPanel.add(label);
        addressPanel.add(addressField);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetListener());
        resetButton.setFocusable(false);
        loadButton = new JButton("Choose File");
        loadButton.addActionListener(new LoadListener());
        loadButton.setFocusable(false);
        buttonPanel.add(resetButton);
        buttonPanel.add(loadButton);
        this.add(addressPanel);
        this.add(buttonPanel);
        this.reArrange();
    }

    /**
     * Rearranges the panel
     */
    public void reArrange() {
        int width = getWidth(), height = getHeight();
        addressField.getParent().setMaximumSize(new Dimension(width, height / 10));
        addressField.getParent().setMinimumSize(new Dimension(width, height / 10));
        loadButton.getParent().setMaximumSize(new Dimension(width, height / 10));
        loadButton.getParent().setMinimumSize(new Dimension(width, height / 10));
        addressField.setMaximumSize(new Dimension(addressField.getParent().getWidth(), addressField.getParent().getHeight() / 2));
        addressField.setMinimumSize(new Dimension(addressField.getParent().getWidth(), addressField.getParent().getHeight() / 2));
    }

    private class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addressField.setText("No file selected");
        }
    }

    private class LoadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(new File("."));
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                addressField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
    }
}