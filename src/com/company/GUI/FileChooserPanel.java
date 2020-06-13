package com.company.GUI;

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

    //description label
    private JLabel label;
    //Address text field
    private JTextField addressField;
    //Background color
    private Color bgColor;
    //Reset button
    private JButton resetButton;
    //Load button
    private JButton loadButton;
    //mode
    private boolean isPath;

    /**
     * Constructor with 2 parameters
     *
     * @param bgColor Background color of the panel
     * @param size    Size of the panel
     * @param isPath  is path or file?
     */
    public FileChooserPanel(Color bgColor, Dimension size, boolean isPath) {
        this.isPath = isPath;
        Color color = new Color(bgColor.getRed() - 10, bgColor.getGreen() - 10, bgColor.getBlue() - 10);
        this.bgColor = bgColor;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(bgColor);
        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.LINE_AXIS));
        addressPanel.setBackground(bgColor);
        if (isPath) {
            label = new JLabel("Selected path: ");
            addressField = new JTextField("No path selected");
            loadButton = new JButton("Choose path");
        } else {
            label = new JLabel("Selected file: ");
            addressField = new JTextField("No file selected");
            loadButton = new JButton("Choose file");
        }
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

    /**
     * resers the file chooser
     */
    public void reset() {
        if (isPath)
            addressField.setText("No path selected");
        else
            addressField.setText("No file selected");
    }

    /**
     * gets current file or path name
     *
     * @return file or path name
     */
    public String getCurrentFileName() {
        if (addressField.getText().equals("No file selected") || addressField.getText().equals("No path selected"))
            return null;
        return addressField.getText();
    }

    /**
     * Sets curernt file or path name
     *
     * @param name file or path name
     */
    public void setFileName(String name) {

        if (name.equals(""))
            reset();
        else
            addressField.setText(name);
    }

    private class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FileChooserPanel.this.reset();
        }
    }

    private class LoadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(new File("."));
            int result;
            System.out.println(isPath);
            if (isPath)
                result = fileChooser.showSaveDialog(null);
            else
                result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                addressField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
    }
}