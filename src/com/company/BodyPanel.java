package com.company;

import javax.swing.*;
import java.awt.*;

public class BodyPanel extends JPanel {

    private JTextArea textArea;
    private Color bgColor;
    private JScrollPane scroll;

    public BodyPanel(Color bgColor, Dimension size,boolean isEditable){
        //init
        this.bgColor = bgColor;
        this.setLayout(null);
        this.setBackground(bgColor);
        this.setBounds(0,0,size.width,size.height);
        //text area
        textArea = new JTextArea();
        textArea.setBackground(new Color(bgColor.getRed()-10,bgColor.getGreen()-10,bgColor.getBlue()-10));
        textArea.setForeground(Color.WHITE);
        textArea.setEditable(isEditable);
        scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scroll);
        this.reArrange();
    }

    public void reArrange(){
        int width = getWidth(), height = getHeight();
        textArea.setBounds(10,10,width-20,height-20);
        scroll.setBounds(10,10,width-20,height-20);
    }
}