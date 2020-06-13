package com.company.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Represents a class for setting headers
 *
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
    //left text
    private String leftText;
    //right
    private String rightText;

    /**
     * Constructor with 2 parameters
     *
     * @param leftText  key of the header
     * @param rightText Value of the header
     * @param bgColor   Background color of the panel
     * @param size      Size of the panel
     */
    public SetHeadersPanel(String leftText, String rightText, Color bgColor, Dimension size) {
        //init
        this.leftText = leftText;
        this.rightText = rightText;
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
        addHeader(false, false);
        this.add(scrollPane);
        headersPanel.setBounds(10, 10, size.width - 20, size.height - 90);
        scrollPane.setBounds(10, 10, size.width - 20, size.height - 90);
    }

    /**
     * Adds a header to the list
     *
     * @param checkBoxVisibility Check box visibility
     * @param buttonVisibility   Delete button visibility
     */
    public void addHeader(boolean checkBoxVisibility, boolean buttonVisibility) {
        Color color = new Color(bgColor.getRed() - 10, bgColor.getGreen() - 10, bgColor.getBlue() - 10);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        JTextField key = new JTextField(leftText, 10);
        key.setBackground(color);
        key.setForeground(Color.WHITE);
        JTextField value = new JTextField(rightText, 10);
        value.setBackground(color);
        value.setForeground(Color.WHITE);
        JCheckBox checkBox = new JCheckBox("Use");
        checkBox.setSelected(true);
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
                if (e.getButton() == MouseEvent.BUTTON1) {
                    JPanel panel = ((JPanel) ((JTextField) e.getSource()).getParent());
                    if (!panel.getComponent(2).isVisible()) {
                        panel.getComponent(2).setVisible(true);
                        panel.getComponent(3).setVisible(true);
                        SetHeadersPanel.this.addHeader(false, false);
                    }
                }
            }
        });
        value.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    JPanel panel = ((JPanel) ((JTextField) e.getSource()).getParent());
                    if (!panel.getComponent(2).isVisible()) {
                        panel.getComponent(2).setVisible(true);
                        panel.getComponent(3).setVisible(true);
                        SetHeadersPanel.this.addHeader(false, false);
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
        headersPanel.setBounds(10, 10, width - 20, height - 90);
        scrollPane.setBounds(10, 10, width - 20, height - 90);
        int i = 0;
        for (JPanel panel : panels) {
            panel.setMaximumSize(new Dimension(width, height / 20));
            panel.setMinimumSize(new Dimension(width, height / 20));
            panel.setLocation(0, height * i / 20);
        }
    }

    public LinkedHashMap<String, String> getMap(boolean checkBoxEffect) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (JPanel panel : panels) {
            String leftText = ((JTextField) panel.getComponent(0)).getText();
            String rightText = ((JTextField) panel.getComponent(1)).getText();
            if (checkBoxEffect) {
                if (!this.leftText.equals(leftText) && !this.rightText.equals(rightText) && ((JCheckBox) panel.getComponent(2)).isSelected())
                    map.put(leftText, rightText);
            } else {
                if (!this.leftText.equals(leftText) && !this.rightText.equals(rightText))
                    map.put(leftText, rightText);
            }
        }
        return map;
    }

    public void setFields(LinkedHashMap<String, String> map) {
        reset();
        headersPanel.remove(0);
        panels.remove(0);
        int i = 0;
        for (String key : map.keySet()) {
            addHeader(true, true);
            ((JTextField) panels.get(i).getComponent(0)).setText(key);
            ((JTextField) panels.get(i).getComponent(1)).setText(map.get(key));
            i++;
        }
        addHeader(false, false);
    }

    public void reset() {
        for (Component c : headersPanel.getComponents()) {
            headersPanel.remove(c);
            panels.remove(c);
        }
        addHeader(false, false);
        reArrange();
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
