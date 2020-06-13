package com.company.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Represnts a class with just one text area
 */
public class BodyPanel extends JPanel {

    //Text area
    private JTextArea textArea;
    //Background color
    private Color bgColor;
    //Scroll
    private JScrollPane scroll;

    /**
     * Constructor with 3 paramters
     *
     * @param bgColor    Background color of the panel
     * @param size       size of the panel
     * @param isEditable is editable or not
     */
    public BodyPanel(Color bgColor, Dimension size, boolean isEditable) {
        //init
        this.bgColor = bgColor;
        this.setLayout(null);
        this.setBackground(bgColor);
        this.setBounds(0, 0, size.width, size.height);
        //text area
        textArea = new JTextArea();
        textArea.setBackground(new Color(bgColor.getRed() - 10, bgColor.getGreen() - 10, bgColor.getBlue() - 10));
        textArea.setForeground(Color.WHITE);
        textArea.setEditable(isEditable);
        scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.setFocusable(false);
        this.add(scroll);
        this.reArrange();
    }

    /**
     * Rearranges the class
     */
    public void reArrange() {
        int width = getWidth(), height = getHeight();
        textArea.setBounds(10, 10, width - 20, height - 20);
        scroll.setBounds(10, 10, width - 20, height - 20);
    }

    /**
     * Gets the text
     *
     * @return thet text
     */
    public String getText() {
        return textArea.getText();
    }

    /**
     * Sets the text
     *
     * @param text the text
     */
    public void setText(String text) {
        textArea.setText(text);
    }

    /**
     * Resets the text
     */
    public void reset() {
        textArea.setText("");
    }
}