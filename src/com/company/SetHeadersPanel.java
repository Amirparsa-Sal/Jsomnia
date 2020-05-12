package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.xml.bind.SchemaOutputResolver;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Represents a class for setting headers
 * @author Amirparsa Salmnakhah
 * @version 1.0.0
 */
public class SetHeadersPanel extends JPanel {

    //List OF panels
    private ArrayList<JPanel> panels;
    //Background color
    private Color bgColor;
    //Scroll
    private JScrollPane scrollPane;
    //Panel of panels
    private JPanel headersPanel;

    /**
     * Constructor with 2 parameters
     * @param bgColor Background color of the panel
     * @param size Size of the panel
     */
    public SetHeadersPanel(Color bgColor, Dimension size) {
        //init
        panels = new ArrayList<>();
        this.bgColor = bgColor;
        this.setLayout(null);
        this.setBackground(bgColor);
        this.setBounds(0, 0, size.width, size.height);
        headersPanel = new JPanel();
        headersPanel.setLayout(new BoxLayout(headersPanel, BoxLayout.PAGE_AXIS));
        headersPanel.setBackground(bgColor);
        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(headersPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(bgColor);
        this.add(scrollPane);
        headersPanel.setBounds(10,10,size.width-20,size.height-90);
        scrollPane.setBounds(10,10,size.width-20,size.height-90);
    }

    /**
     * Adds a header to the list
     * @param leftText key of the header
     * @param rightText Value of the header
     * @param checkBoxVisibility Check box visibility
     * @param buttonVisibility Delete button visibility
     */
    public void addHeader(String leftText, String rightText, boolean checkBoxVisibility, boolean buttonVisibility) {
        Color color = new Color(bgColor.getRed() - 10, bgColor.getGreen() - 10, bgColor.getBlue() - 10);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.LINE_AXIS));
        JTextField key = new JTextField(leftText,10);
        key.setBackground(color);
        key.setForeground(Color.WHITE);
        JTextField value = new JTextField(rightText,10);
        value.setBackground(color);
        value.setForeground(Color.WHITE);
        JCheckBox checkBox = new JCheckBox("Use");
        checkBox.setVisible(checkBoxVisibility);
        JButton deleteButton = new JButton("×");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setVisible(buttonVisibility);
        deleteButton.setFocusable(true);
        deleteButton.addActionListener(new DeleteButtonListener());
        panel.add(key);
        panel.add(value);
        panel.add(checkBox);
        panel.add(deleteButton);
        panels.add(panel);
        headersPanel.add(panel);
        key.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()== MouseEvent.BUTTON1) {
                    JPanel panel = ((JPanel) ((JTextField) e.getSource()).getParent());
                    if (!((JCheckBox) panel.getComponent(2)).isVisible()) {
                        ((JCheckBox) panel.getComponent(2)).setVisible(true);
                        ((JButton) panel.getComponent(3)).setVisible(true);
                        SetHeadersPanel.this.addHeader("New header", "New value", false, false);
                    }
                }
            }
        });
        value.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()== MouseEvent.BUTTON1) {
                    JPanel panel = ((JPanel) ((JTextField) e.getSource()).getParent());
                    if (!((JCheckBox) panel.getComponent(2)).isVisible()) {
                        ((JCheckBox) panel.getComponent(2)).setVisible(true);
                        ((JButton) panel.getComponent(3)).setVisible(true);
                        SetHeadersPanel.this.addHeader("New header", "New value", false, false);
                    }
                }
            }
        });
        this.reArrange();

    }

    /**
     * Rearranges the panel
     */
    public void reArrange() {
        int width = getWidth(), height = getHeight();
        headersPanel.setBounds(10,10,width-20,height-90);
        scrollPane.setBounds(10,10,width-20,height-90);
        int i=0;
        for (JPanel panel : panels) {
            panel.setMaximumSize(new Dimension(width,height / 20));
            panel.setMinimumSize(new Dimension(width,height / 20));
            panel.setLocation(0,height * i /20);
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = ((JPanel) ((JButton) e.getSource()).getParent());
            panel.requestFocus();
            panels.remove(panel);
            headersPanel.remove(panel);
            SetHeadersPanel.this.reArrange();
        }
    }
}
