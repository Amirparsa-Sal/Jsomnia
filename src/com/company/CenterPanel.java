package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CenterPanel extends JPanel {

    private Color bgColor;
    private JFrame mother;
    private JComboBox requestMethodBox;
    private JTextField urlField;
    private JButton sendButton;
    private JButton saveButton;
    private JTabbedPane tabs;
    private JTabbedPane secondTabs;
    private BodyPanel bodyPanel;
    private SetHeadersPanel setHeadersPanel;
    private SetHeadersPanel setFormDataPanel;

    public CenterPanel(int x, int y, Dimension size, Color bgColor, JFrame mother) {
        super();
        //init
        this.bgColor = bgColor;
        this.mother = mother;
        this.setLayout(null);
        this.setBorder(new LineBorder(new Color(85,85,85),1));
        this.setBounds(x, y, size.width, size.height);
        this.setBackground(bgColor);
        //request method box
        requestMethodBox = new JComboBox();
        ((JLabel)requestMethodBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        for (RequestMethod requestMethod : RequestMethod.values())
            if (requestMethod != RequestMethod.UNKNOWN)
                requestMethodBox.addItem(requestMethod.toString());
        //url field
        urlField = new JTextField("Enter URL here");
        //send button
        sendButton = new JButton("Send");
        //save button
        saveButton = new JButton("Save");
        //second tabs
        setFormDataPanel = new SetHeadersPanel(bgColor,new Dimension(size.width,size.height-50));
        setFormDataPanel.addHeader("New header", "New value", false, false);
        secondTabs = new JTabbedPane();
        bodyPanel = new BodyPanel(bgColor,new Dimension(size.width,size.height-50),true);
        secondTabs.add("Form data",setFormDataPanel);
        secondTabs.add("JSON",bodyPanel);
        //tabs
        tabs = new JTabbedPane();
        tabs.addTab("Body",secondTabs);
        setHeadersPanel = new SetHeadersPanel(bgColor,new Dimension(size.width,size.height-50));
        setHeadersPanel.addHeader("New header", "New value", false, false);
        tabs.addTab("Headers",setHeadersPanel);
        //add items
        this.add(requestMethodBox);
        this.add(urlField);
        this.add(sendButton);
        this.add(saveButton);
        this.add(tabs);
        this.setVisible(true);
        reArrange();
    }

    public void reArrange(){
        int width=getWidth(),height = getHeight();
        requestMethodBox.setBounds(0,0,width/6,40);
        urlField.setBounds(width/6,0,width*3/6,40);
        sendButton.setBounds(width*4/6,0,width/6,40);
        saveButton.setBounds(width*5/6,0,width/6,40);
        tabs.setBounds(1,50,width-1,height-50);
        setFormDataPanel.setSize(width,height-50);
        bodyPanel.setSize(width,height-120);
        setHeadersPanel.setSize(width,height-50);
        setHeadersPanel.reArrange();
        setFormDataPanel.reArrange();
        bodyPanel.reArrange();
    }
}
