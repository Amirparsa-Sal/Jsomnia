package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class MenuPanel extends JMenuBar{

    private GUIManager manager;
    private JMenu applicationMenu;
    private JMenu viewMenu;
    private JMenu helpMenu;

    public MenuPanel(Dimension size, GUIManager manager){
        this.manager = manager;
        this.setBounds(0,0,size.width,size.height);
        applicationMenu = new JMenu("Application");
        applicationMenu.setMnemonic('a');
        viewMenu = new JMenu("View");
        viewMenu.setMnemonic('v');
        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('h');
        JMenuItem options = new JMenuItem("Options");
        options.setMnemonic(KeyEvent.VK_O);
        options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_MASK));
        options.addActionListener(new OptionsListener());
        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,KeyEvent.ALT_MASK));
        exit.addActionListener(new ExitListener());
        JCheckBoxMenuItem toggleSidebar = new JCheckBoxMenuItem("Toggle Sidebar");
        toggleSidebar.setMnemonic(KeyEvent.VK_T);
        toggleSidebar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,KeyEvent.CTRL_MASK));
        JCheckBoxMenuItem toggleFullScreen = new JCheckBoxMenuItem("Toggle Full Screen");
        toggleFullScreen.setMnemonic(KeyEvent.VK_F);
        toggleFullScreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,KeyEvent.CTRL_MASK));
        toggleFullScreen.addActionListener(new FullScreenListener());
        JMenuItem about = new JMenuItem("About");
        about.setMnemonic(KeyEvent.VK_A);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,KeyEvent.CTRL_MASK));
        JMenuItem help = new JMenuItem("Help");
        help.setMnemonic(KeyEvent.VK_H);
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,KeyEvent.CTRL_MASK));
        applicationMenu.add(options);applicationMenu.addSeparator();applicationMenu.add(exit);
        viewMenu.add(toggleSidebar);
        viewMenu.addSeparator();
        viewMenu.add(toggleFullScreen);
        helpMenu.add(about);
        helpMenu.addSeparator();
        helpMenu.add(help);
        this.add(applicationMenu);
        this.add(viewMenu);
        this.add(helpMenu);
    }

    private class OptionsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            manager.getOptionFrame().setVisible(true);
        }
    }
    private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog (null, "Are you sure you want to close the program?","Are you sure?",JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION){
                System.exit(0);
            }

        }
    }

    private class FullScreenListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Jsomnia");
            JFrame motherFrame = manager.getFrame();
            frame.setLayeredPane(motherFrame.getLayeredPane());
            if(!manager.isFullScreen()){
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setUndecorated(true);
                manager.setFullScreen(true);
            }
            else{
                frame.setSize(1000,800);
                manager.setFullScreen(false);
            }
            manager.setFrame(frame);
            manager.showGUI();
            motherFrame.dispose();
            motherFrame = frame;
            int jOptionPane;
            manager.reArrange();
            if(manager.isFullScreen()) {
                jOptionPane = JOptionPane.showConfirmDialog(null,"Use Ctrl+F to exit fullscreen mode","Attention!",JOptionPane.DEFAULT_OPTION);
            }
        }
    }
}
