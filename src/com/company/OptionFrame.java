package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class OptionFrame extends JFrame {

    public static final Dimension size = new Dimension(300,150);
    private JPanel checkBoxPanel;
    private JPanel themePanel;
    private JPanel buttons;

    public OptionFrame(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle("Options");
        this.setSize(size);
        this.setLocation(screenSize.width/2 - this.getWidth()/2,screenSize.height/2 - this.getHeight()/2);
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
        buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        buttons.setMaximumSize(new Dimension(120,30));
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
        this.add(themePanel);
        this.add(buttons);
        File file = new File("options.dat");
        if(file.exists())
            this.load();
    }

    public void load(){
        try {
            File file = new File(new File(".").getAbsolutePath() + "\\Data\\options.dat");
            Scanner scanner = new Scanner(file);
            this.setFollowRedirect(scanner.next().equals("true"));
            this.setSystemTray(scanner.next().equals("true"));
            this.setTheme(scanner.next());
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.exit(-1);
        }

    }
    public void save(){
        try {
            String data = getFollowRedirect().toString() + " " + getSystemTray().toString() + " " + getTheme();
            PrintWriter printWriter = new PrintWriter(new File(".").getAbsolutePath() + "\\Data\\options.dat", "UTF-8");
            printWriter.print(data);
            printWriter.close();
        }
        catch (Exception e){
            System.out.println(e);
            System.exit(-1);
        }
    }

    public JPanel getCheckBoxPanel() {
        return checkBoxPanel;
    }

    public JPanel getThemePanel() {
        return themePanel;
    }

    public JButton getOkButton() {
        return (JButton)(buttons.getComponent(0));
    }

    public JButton getCancelButton() {
        return (JButton)(buttons.getComponent(1));
    }

    public Boolean getFollowRedirect(){
        return ((JCheckBox)checkBoxPanel.getComponent(0)).isSelected();
    }

    public Boolean getSystemTray(){
        return ((JCheckBox)checkBoxPanel.getComponent(1)).isSelected();
    }

    public String getTheme(){
        return (String)(((JComboBox)themePanel.getComponent(1)).getSelectedItem());
    }

    public void setFollowRedirect(boolean isSelected){
        ((JCheckBox)checkBoxPanel.getComponent(0)).setSelected(isSelected);
    }

    public void setSystemTray(boolean isSelected){
        ((JCheckBox)checkBoxPanel.getComponent(1)).setSelected(isSelected);
    }

    public void setTheme(String item){
        Object o = (Object) item;
        ((JComboBox)themePanel.getComponent(1)).setSelectedItem(o);
    }
}