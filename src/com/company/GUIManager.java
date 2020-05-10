package com.company;

import javax.swing.*;
import javax.swing.plaf.PopupMenuUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GUIManager{

    private static GUIManager instance;
    public static final Color bgColor= new Color(30,30,30);
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private boolean isFullScreen;
    private LeftRequestList left;
    private CenterPanel center;
    private RightResponsePanel right;
    private MenuPanel up;
    private OptionFrame optionFrame;
    private JFrame frame;

    public static GUIManager getInstance(){
        if(instance==null)
            instance = new GUIManager();
        return instance;
    }

    private GUIManager(){
        super();
        Dimension size = new Dimension(1000,800);
        isFullScreen = false;
        frame = new JFrame("Jsomnia");
        frame.setSize(size.width,size.height);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        up = new MenuPanel(new Dimension(size.width,20),this);
        left = new LeftRequestList(0,20,new Dimension(200,size.height-25),bgColor);
        center = new CenterPanel(200,20,new Dimension((size.width-200)/2,size.height-25),bgColor,frame);
        try {
            right = new RightResponsePanel(200 + (size.width-200)/2,20,bgColor, new Dimension((size.width-200)/2,size.height-25));
        } catch (IOException e) {
            e.printStackTrace();
        }
        optionFrame = new OptionFrame();
        frame.getLayeredPane().add(left,1);
        frame.getLayeredPane().add(center,2);
        frame.getLayeredPane().add(right,3);
        frame.getLayeredPane().add(up,4);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog (null, "Are you sure you want to close the program?","Are you sure?",JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    if(optionFrame.getSystemTray()){
                        frame.setVisible(false);
                        TrayIcon icon = null;
                        if(SystemTray.isSupported()){
                            SystemTray tray = SystemTray.getSystemTray();
                            Image image = Toolkit.getDefaultToolkit().getImage(new File(".").getAbsolutePath() + "\\Assets\\icon.png");
                            PopupMenu popup = new PopupMenu();
                            MenuItem open = new MenuItem("Open");
                            open.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    frame.setVisible(true);
                                }
                            });
                            MenuItem close = new MenuItem("Close");
                            close.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.exit(0);
                                }
                            });
                            popup.add(open);
                            popup.add(close);
                            icon = new TrayIcon(image,"Jsomnia",popup);
                            icon.setImageAutoSize(true);
                            try {
                                tray.add(icon);
                            } catch (AWTException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    else
                        System.exit(0);
                }
                else
                    frame.setVisible(true);
            }
        });
    }

    public void showGUI(){
        frame.setLocation(screenSize.width/2 - frame.getWidth()/2,screenSize.height/2 - frame.getHeight()/2);
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
