package com.company;

import javafx.scene.input.MouseButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Set;

public class SetHeadersPanel extends JPanel {

    private ArrayList<JPanel> panels;
    private Color bgColor;

    public SetHeadersPanel(Color bgColor, Dimension size) {
        //init
        panels = new ArrayList<>();
        this.bgColor = bgColor;
        this.setLayout(null);
        this.setBackground(bgColor);
        this.setBounds(0, 0, size.width, size.height);
    }

    public void addHeader(String leftText, String rightText, boolean checkBoxVisibility, boolean buttonVisibility) {
        System.out.println("***");
        Color color = new Color(bgColor.getRed() - 10, bgColor.getGreen() - 10, bgColor.getBlue() - 10);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JTextField key = new JTextField(leftText);
        key.setBackground(color);
        key.setForeground(Color.WHITE);
        JTextField value = new JTextField(rightText);
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
        key.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()== MouseEvent.BUTTON1) {
                    JPanel panel = ((JPanel) ((JTextField) e.getSource()).getParent());
                    if (!((JCheckBox) panel.getComponent(2)).isVisible()) {
                        ((JCheckBox) panel.getComponent(2)).setVisible(true);
                        ((JButton) panel.getComponent(3)).setVisible(true);
                        SetHeadersPanel.this.addHeader("New header", "New value", false, false);
                        SetHeadersPanel.this.reArrange();
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
                        SetHeadersPanel.this.reArrange();
                    }
                }
            }
        });
        this.add(panel);
        this.reArrange();

    }

    public void reArrange() {
        int i = 0;
        for (JPanel panel : panels) {
            panel.setBounds(10, 10 + getHeight() * i / 20, getWidth() - 20, getHeight() / 20);
            panel.getComponent(0).setBounds(0, 0, panel.getWidth() / 3, panel.getHeight());
            panel.getComponent(1).setBounds(panel.getWidth() / 3, 0, panel.getWidth() / 3, panel.getHeight());
            panel.getComponent(2).setBounds(panel.getWidth() * 2 / 3, 0, panel.getWidth() / 6, panel.getHeight());
            panel.getComponent(3).setBounds(panel.getWidth() * 5 / 6, 0, panel.getWidth() / 6, panel.getHeight());
            i++;
        }

    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = ((JPanel) ((JButton) e.getSource()).getParent());
            panel.requestFocus();
            panels.remove(panel);
            for (Component jPanel : SetHeadersPanel.this.getComponents()) {
                if (jPanel.equals(panel)) {
                    SetHeadersPanel.this.remove(jPanel);
                    break;
                }
            }
            SetHeadersPanel.this.reArrange();
        }
    }
}
