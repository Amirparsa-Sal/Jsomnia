package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GUIManager{

    public static final Color bgColor= new Color(30,30,30);

    private JFrame frame;
    private Dimension size;
    private LeftRequestList left;

    public GUIManager(Dimension size, String name){
        frame = new JFrame(name);
        frame.setSize(size.width,size.height);
        this.size = size;
        left = new LeftRequestList(0,0,new Dimension(200,size.height),bgColor,frame);
        frame.setLayout(null);
        frame.getLayeredPane().add(left,1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void showGUI(){


        left.addToReqList(RequestMethod.GET,"FirstTry");
        left.addToReqList(RequestMethod.POST,"SecondTry");
        left.addToReqList(RequestMethod.DELETE,"ThirdTry");
        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                left.setBounds(0,0,200,frame.getHeight());
                left.reArrange();
            }
        });
    }
}
