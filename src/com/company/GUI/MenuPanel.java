package com.company.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JMenuBar {

    //Application menu
    private JMenu applicationMenu;
    //View menu
    private JMenu viewMenu;
    //Help menu
    private JMenu helpMenu;

    /**
     * Constructor with a parameter
     *
     * @param size size of the panel
     */
    public MenuPanel(Dimension size) {
        this.setBounds(0, 0, size.width, size.height);
        applicationMenu = new JMenu("Application");
        applicationMenu.setMnemonic('a');
        viewMenu = new JMenu("View");
        viewMenu.setMnemonic('v');
        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('h');
        JMenuItem options = new JMenuItem("Options");
        options.setMnemonic(KeyEvent.VK_O);
        options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        options.addActionListener(new OptionsListener());
        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_MASK));
        exit.addActionListener(new ExitListener());
        JCheckBoxMenuItem toggleSidebar = new JCheckBoxMenuItem("Toggle Sidebar");
        toggleSidebar.setMnemonic(KeyEvent.VK_T);
        toggleSidebar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_MASK));
        toggleSidebar.addActionListener(new ToggleSideListener());
        JCheckBoxMenuItem toggleFullScreen = new JCheckBoxMenuItem("Toggle Full Screen");
        toggleFullScreen.setMnemonic(KeyEvent.VK_F);
        toggleFullScreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));
        toggleFullScreen.addActionListener(new FullScreenListener());
        JMenuItem about = new JMenuItem("About");
        about.setMnemonic(KeyEvent.VK_A);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK));
        about.addActionListener(new AboutListener());
        JMenuItem help = new JMenuItem("Help");
        help.setMnemonic(KeyEvent.VK_H);
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
        help.addActionListener(new HelpListener());
        applicationMenu.add(options);
        applicationMenu.addSeparator();
        applicationMenu.add(exit);
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

    private static class AboutFrame extends JFrame {
        private static AboutFrame instance;

        //Name
        public JLabel name;
        //ID
        public JLabel id;
        //Contact
        public JLabel contact;

        private AboutFrame() {
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
            this.setLocation(GUIManager.screenSize.width / 2 - this.getWidth() / 2, GUIManager.screenSize.height / 2 - this.getHeight() / 2);
        }

        /**
         * Returns the only instance of the class
         *
         * @returnthe only instance of the class
         */
        public static AboutFrame getInstance() {
            if (instance == null)
                instance = new AboutFrame();
            return instance;
        }
    }

    private static class HelpFrame extends JFrame {
        private static HelpFrame instance;
        private JEditorPane help;

        private HelpFrame() {
            super();
            this.setTitle("Help");
            this.setResizable(false);
            this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
            this.getContentPane().setBackground(GUIManager.bgColor);
            help = new JEditorPane();
            help.setText("You can run ConsoleRun class in terminal using this command:\n" +
                    "java -cp out/production/Midterm;lib/* ConsoleRun <Commands> \n\n" +
                    "which the <Commands> are as following: \n" +
                    "-d --data <data> : adds multipart/form-data [example: -d \"key1=value1&key2=value2\"]\n" +
                    "-e --encoded <data> : adds form urlencoded data [example: -e \"key1=value1&key2=value2\"]\n" +
                    "-f : turns on follow redirect for the request\n" +
                    "-H --header <headers>: adds headers to the request [example: -H \"key1:value1;key2:value2\"]\n" +
                    "-h --help : shows list of commands\n" +
                    "-j --json <json data>: adds json data [example: -j \"{'key1':value1','key2':'value2'}\"]\n" +
                    "-M --method <method name>: sets the request method [Methods: GET,POST,PUT,DELETE]\n" +
                    "-N --name <request name>: sets the request name\n" +
                    "-O --output [=file_name]: downloads output of the request\n" +
                    "-i : turns on response visibility\n" +
                    "-S --save : saves request to the list\n" +
                    "list : shows list of the requests\n" +
                    "fire <RequestNumber> ... <RequestNumber> : executes the saved requests\n" +
                    "remove <RequestNumber> ... <RequestNumber> : removes the requests\n\n" +
                    "In GUIRun class you can create a new reqeust with new request button and fill the request data\n" +
                    "in center panel and see the response in right panel.");
            this.add(help);
            this.setSize(600, 400);
        }

        public static HelpFrame getInstance() {
            if (instance == null)
                instance = new HelpFrame();
            return instance;
        }
    }

    private class OptionsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().getOptionFrame().setVisible(true);
        }
    }

    private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = GUIManager.getInstance().getFrame();
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    private class ToggleSideListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = GUIManager.getInstance().getFrame();
            GUIManager.getInstance().setToggleSide(!GUIManager.getInstance().isToggleSide());
            frame.dispatchEvent(new ComponentEvent(frame, ComponentEvent.COMPONENT_RESIZED));
        }
    }

    private class FullScreenListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            GUIManager manager = GUIManager.getInstance();
            JFrame frame = new JFrame("Jsomnia");
            JFrame motherFrame = manager.getFrame();
            frame.setLayeredPane(motherFrame.getLayeredPane());
            manager.setFrame(frame);
            manager.initFrame();
            if (!manager.isFullScreen()) {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                frame.setUndecorated(true);
                manager.setFullScreen(true);
            } else {
                frame.setSize(1000, 800);
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                manager.setFullScreen(false);
            }
            manager.showGUI();
            motherFrame.dispose();
            int jOptionPane;
            manager.reArrange();
            if (manager.isFullScreen()) {
                jOptionPane = JOptionPane.showConfirmDialog(null, "Use Ctrl+F to exit fullscreen mode", "Attention!", JOptionPane.DEFAULT_OPTION);
            }
        }
    }

    private class AboutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MenuPanel.AboutFrame.getInstance().setVisible(true);
        }
    }

    private class HelpListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Dimension size = MenuPanel.HelpFrame.getInstance().getSize();
            MenuPanel.HelpFrame.getInstance().setLocation(GUIManager.screenSize.width / 2 - size.width / 2, GUIManager.screenSize.height / 2 - size.height / 2);
            MenuPanel.HelpFrame.getInstance().setVisible(true);

        }
    }
}

