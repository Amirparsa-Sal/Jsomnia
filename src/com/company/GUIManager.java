package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GUIManager{

    public static final Color bgColor= new Color(30,30,30);

    private JFrame frame;
    private LeftRequestList left;
    private CenterPanel center;

    public GUIManager(Dimension size, String name){
        super();
        frame = new JFrame(name);
        frame.setSize(size.width,size.height);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        left = new LeftRequestList(0,0,new Dimension(200,size.height-45),bgColor,frame);
        center = new CenterPanel(200,0,new Dimension((size.width-200)/2,size.height-45),bgColor,frame);
        frame.getLayeredPane().add(left,1);
        frame.getLayeredPane().add(center,2);
    }
    public void showGUI(){
        left.addToReqList(new Request("FirstTry",RequestMethod.GET));
        left.addToReqList(new Request("SecondTry",RequestMethod.POST));
        left.addToReqList(new Request("ThirdTry",RequestMethod.DELETE));
        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                left.setBounds(0,0,200,frame.getHeight()-45);
                center.setBounds(200,0,(frame.getWidth()-200)/2,frame.getHeight());
                left.reArrange();
                center.reArrange();
            }
        });
    }
}
