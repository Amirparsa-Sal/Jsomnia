package com.company;

import com.sun.javaws.util.JfxHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.bind.JAXBPermission;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
        toggleSidebar.addActionListener(new ToggleSideListener());
        JCheckBoxMenuItem toggleFullScreen = new JCheckBoxMenuItem("Toggle Full Screen");
        toggleFullScreen.setMnemonic(KeyEvent.VK_F);
        toggleFullScreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,KeyEvent.CTRL_MASK));
        toggleFullScreen.addActionListener(new FullScreenListener());
        JMenuItem about = new JMenuItem("About");
        about.setMnemonic(KeyEvent.VK_A);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,KeyEvent.CTRL_MASK));
        about.addActionListener(new AboutListener());
        JMenuItem help = new JMenuItem("Help");
        help.setMnemonic(KeyEvent.VK_H);
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,KeyEvent.CTRL_MASK));
        help.addActionListener(new HelpListener());
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
            JFrame frame = GUIManager.getInstance().getFrame();
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    private class ToggleSideListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = GUIManager.getInstance().getFrame();
            GUIManager.getInstance().setToggleSide(!GUIManager.getInstance().isToggleSide());
            GUIManager.getInstance().reArrange();
            frame.dispatchEvent(new ComponentEvent(frame, ComponentEvent.COMPONENT_RESIZED));
        }
    }
    private class FullScreenListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Jsomnia");
            JFrame motherFrame = manager.getFrame();
            frame.setLayeredPane(motherFrame.getLayeredPane());
            manager.setFrame(frame);
            manager.initFrame();
            if(!manager.isFullScreen()){
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setUndecorated(true);
                manager.setFullScreen(true);
            }
            else{
                frame.setSize(1000,800);
                manager.setFullScreen(false);
            }
            manager.showGUI();
            motherFrame.dispose();
            int jOptionPane;
            manager.reArrange();
            if(manager.isFullScreen()) {
                jOptionPane = JOptionPane.showConfirmDialog(null,"Use Ctrl+F to exit fullscreen mode","Attention!",JOptionPane.DEFAULT_OPTION);
            }
        }
    }

    private class AboutListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            MenuPanel.AboutFrame.getInstance().setVisible(true);
        }
    }

    private class HelpListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Dimension size = MenuPanel.HelpFrame.getInstance().getSize();
            MenuPanel.HelpFrame.getInstance().setLocation(GUIManager.screenSize.width/2-size.width/2,GUIManager.screenSize.height/2-size.height/2);
            MenuPanel.HelpFrame.getInstance().setVisible(true);

        }
    }
    public static class AboutFrame extends JFrame {
        private static AboutFrame instance;

        public JLabel name;
        public JLabel id;
        public JLabel contact;

        public static AboutFrame getInstance(){
            if(instance==null)
                instance = new AboutFrame();
            return instance;
        }

        private AboutFrame(){
            super();
            this.setTitle("About");
            this.setResizable(false);
            this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
            this.getContentPane().setBackground(GUIManager.bgColor);
            name = new JLabel("Created by Amirparsa Salmankhah");
            name.setBackground(GUIManager.bgColor);
            name.setForeground(Color.WHITE);
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            id = new JLabel("Student ID: 9831034");
            id.setBackground(GUIManager.bgColor);
            id.setForeground(Color.WHITE);
            id.setAlignmentX(Component.CENTER_ALIGNMENT);
            contact = new JLabel("Email: Amirparsa.s@aut.ac.ir | Telegram: @Amirparsa_s");
            contact.setBackground(GUIManager.bgColor);
            contact.setForeground(Color.WHITE);
            contact.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.getContentPane().add(name);
            this.getContentPane().add(id);
            this.getContentPane().add(contact);
            this.pack();
            this.setLocation(GUIManager.screenSize.width/2 - this.getWidth()/2,GUIManager.screenSize.height/2 - this.getHeight()/2);
        }
    }

    public static class HelpFrame extends JFrame{
        private static HelpFrame instance;

        private JLabel workers;
        private JLabel toBeDone;

        public static HelpFrame getInstance(){
            if(instance==null)
                instance = new HelpFrame();
            return instance;
        }

        private HelpFrame(){
            super();
            this.setTitle("Help");
            this.setResizable(false);
            this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
            this.getContentPane().setBackground(GUIManager.bgColor);
            //workers
            try {
                BufferedImage bufferedImage = ImageIO.read(new File(new File(".").getAbsolutePath() + "\\Assets\\workers.png"));
                workers = new JLabel(new ImageIcon(bufferedImage));
            } catch (IOException e) {
                System.out.println("Resource not found");
                System.exit(-1);
            }
            //label
            workers.setAlignmentX(Component.CENTER_ALIGNMENT);
            toBeDone = new JLabel("To be done in next phase");
            toBeDone.setBackground(GUIManager.bgColor);
            toBeDone.setForeground(Color.WHITE);
            toBeDone.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(workers);
            this.add(toBeDone);
            this.pack();
        }
    }
}

