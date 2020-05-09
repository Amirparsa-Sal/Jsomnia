package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;

public class GUIManager{

    public static final Color bgColor= new Color(30,30,30);

    private boolean isFullScreen;
    private LeftRequestList left;
    private CenterPanel center;
    private RightResponsePanel right;
    private MenuPanel up;
    private OptionFrame optionFrame;
    private JFrame frame;

    public GUIManager(Dimension size, String name) throws IOException {
        super();
        isFullScreen = false;
        frame = new JFrame(name);
        frame.setSize(size.width,size.height);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        up = new MenuPanel(new Dimension(size.width,20),this);
        left = new LeftRequestList(0,20,new Dimension(200,size.height-25),bgColor);
        center = new CenterPanel(200,20,new Dimension((size.width-200)/2,size.height-25),bgColor,frame);
        right = new RightResponsePanel(200 + (size.width-200)/2,20,bgColor, new Dimension((size.width-200)/2,size.height-25));
        optionFrame = new OptionFrame();
        frame.getLayeredPane().add(left,1);
        frame.getLayeredPane().add(center,2);
        frame.getLayeredPane().add(right,3);
        frame.getLayeredPane().add(up,4);
    }

    public void showGUI(){
        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reArrange();
            }
        });
        frame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == JFrame.MAXIMIZED_BOTH)
                    reArrange();
            }
        });
    }

    public void reArrange(){
        up.setBounds(0,0,frame.getWidth(),20);
        if(!isFullScreen()) {
            left.setBounds(0, 20, 200, frame.getHeight() - 45);
            center.setBounds(200, 20, (frame.getWidth() - 200) / 2, frame.getHeight() - 45);
            right.setBounds(200 + (frame.getWidth() - 200) / 2, 20, (frame.getWidth() - 200) / 2 - 20, frame.getHeight() - 45);
        }
        else{
            left.setBounds(0, 20, 200, frame.getHeight());
            center.setBounds(200, 20, (frame.getWidth() - 200) / 2, frame.getHeight());
            right.setBounds(200 + (frame.getWidth() - 200) / 2, 20, (frame.getWidth() - 200) / 2 , frame.getHeight());
        }
        left.reArrange();
        center.reArrange();
        right.reArrange();
    }
    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }

    public OptionFrame getOptionFrame() {
        return optionFrame;
    }
}
