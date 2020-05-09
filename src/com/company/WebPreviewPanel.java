package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class WebPreviewPanel extends JPanel {

    private Color bgColor;
    private JEditorPane webViewer;
    private JScrollPane scrollPane;

    public WebPreviewPanel(Color bgColor, Dimension size) throws IOException {
        //init
        this.bgColor = bgColor;
        this.setBackground(bgColor);
        this.setLayout(null);
        this.setBounds(0,0,size.width,size.height);
        //web viewer
        webViewer = new JEditorPane("http://www.google.co.uk");
        webViewer.setEditable(false);
        //scroll
        scrollPane = new JScrollPane(webViewer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //add
        this.add(scrollPane);
    }

    public void reArrange(){
        int width = getWidth(), height = getHeight();
        scrollPane.setBounds(10,10,width-40,height-40);
    }
}
