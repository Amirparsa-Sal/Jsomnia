package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionFrame extends JFrame {

    public static final Dimension size = new Dimension(300,150);
    private JPanel checkBoxPanel;
    private JPanel themePanel;
    private JButton okButton;

    public OptionFrame(){
        this.setTitle("Options");
        this.setSize(size);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS));
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
        //theme
        themePanel = new JPanel();
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.LINE_AXIS));
        JLabel label = new JLabel("Theme: ");
        label.setForeground(Color.WHITE);
        themePanel.add(label);
        JComboBox theme = new JComboBox();
        theme.addItem("Dark");
        theme.addItem("Light");
        theme.setMaximumSize(new Dimension(150,30));
        themePanel.add(theme);
        themePanel.setMaximumSize(new Dimension(150,50));
        themePanel.setAlignmentX(SwingConstants.CENTER);
        themePanel.setBackground(GUIManager.bgColor);
        themePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //button
        okButton = new JButton("OK");
        okButton.setMaximumSize(new Dimension(100,30));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionFrame.this.dispose();
            }
        });
        this.add(checkBoxPanel);
        this.add(themePanel);
        this.add(okButton);
    }

    public JPanel getCheckBoxPanel() {
        return checkBoxPanel;
    }

    public JPanel getThemePanel() {
        return themePanel;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public boolean getFollowRedirect(){
        return ((JCheckBox)checkBoxPanel.getComponent(0)).isSelected();
    }

    public boolean getSystemTray(){
        return ((JCheckBox)checkBoxPanel.getComponent(0)).isSelected();
    }

    public String getTheme(){
        return (String)(((JComboBox)themePanel.getComponent(1)).getSelectedItem());
    }

}