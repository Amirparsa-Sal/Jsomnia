package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;

public class RightResponsePanel extends JPanel{

    private Color bgColor;
    private JLabel statusLabel;
    private JLabel timeLabel;
    private JLabel sizeLabel;
    private JTabbedPane tabs;
    private JTabbedPane secondTabs;
    private WebPreviewPanel webViewer;
    private BodyPanel rawTextPanel;
    private HeadersTablePanel headersTablePanel;

    public RightResponsePanel(int x,int y, Color bgColor, Dimension size) throws IOException {
        this.bgColor = bgColor;
        this.setLayout(null);
        this.setBounds(x,y,size.width,size.height);
        this.setBackground(bgColor);
        this.setBorder(new LineBorder(new Color(85,85,85),1));
        //init labels
        statusLabel = new JLabel("200 OK", SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(0, 75, 0));
        statusLabel.setForeground(Color.WHITE);
        timeLabel = new JLabel("6.51 s",SwingConstants.CENTER);
        timeLabel.setOpaque(true);
        timeLabel.setBackground(Color.GRAY);
        timeLabel.setForeground(Color.WHITE);
        sizeLabel = new JLabel("7.56 KB",SwingConstants.CENTER);
        sizeLabel.setOpaque(true);
        sizeLabel.setBackground(Color.GRAY);
        sizeLabel.setForeground(Color.WHITE);
        //init body
        JPanel body = new JPanel();
        body.setLayout(null);
        rawTextPanel = new BodyPanel(bgColor,new Dimension(size.width,size.height-80),false);
        webViewer = new WebPreviewPanel(bgColor, new Dimension(size.width,size.height-80));
        //init header
        headersTablePanel = new HeadersTablePanel(bgColor, new Dimension(size.width,size.height-50));
        //init tabs
        tabs = new JTabbedPane();
        secondTabs = new JTabbedPane();
        tabs.add("Preview mode",secondTabs);
        tabs.add("Headers",headersTablePanel);
        secondTabs.add("Raw data",rawTextPanel);
        secondTabs.add("Visual preview",webViewer);
        this.add(statusLabel);
        this.add(timeLabel);
        this.add(sizeLabel);
        this.add(tabs);
        this.setVisible(true);
        this.reArrange();
    }

    public void reArrange(){
        int width=getWidth(), height=getHeight();
        rawTextPanel.setBounds(10,10,width-20,height-120);
        statusLabel.setBounds(10,0,width/6,40);
        timeLabel.setBounds(width/6 + 20,0,width/6,40);
        sizeLabel.setBounds(width*2/6 + 30,0,width/6,40);
        tabs.setBounds(10,50,width,height-40);
        rawTextPanel.reArrange();
        webViewer.reArrange();
        headersTablePanel.reArrange();
    }
}
