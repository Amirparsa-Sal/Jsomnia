package com.company.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Represents a class for managing GUI classes of the program
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class GUIManager {

    //Size of the screen
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //Background color
    public static Color bgColor = new Color(30, 30, 30);
    //The only instance of GUI Manager
    private static GUIManager instance;
    //is full screen or not
    private boolean isFullScreen;
    //is toggle sidebar or not
    private boolean isToggleSide;
    //Left panel
    private LeftRequestList left;
    //Center panel
    private CenterPanel center;
    //Right panel
    private RightResponsePanel right;
    //Menu bar
    private MenuPanel up;
    //Options page
    private OptionFrame optionFrame;
    //Main frame
    private JFrame frame;
    //Splash screen
    private JFrame splashScreen;

    private GUIManager() {
        super();
        Dimension size = new Dimension(1200, 800);
        isFullScreen = false;
        isToggleSide = false;
        frame = new JFrame("Jsomnia");
        frame.setSize(size.width, size.height);
        frame.setLayout(null);
        frame.setBackground(bgColor);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        up = new MenuPanel(new Dimension(size.width, 20));
        left = new LeftRequestList(0, 20, new Dimension(200, size.height - 25), bgColor);
        center = new CenterPanel(200, 20, new Dimension((size.width - 200) / 2, size.height - 25), bgColor);
        this.showSplashScreen();
        try {
            right = new RightResponsePanel(200 + (size.width - 200) / 2, 20, bgColor, new Dimension((size.width - 200) / 2, size.height - 25));
        } catch (IOException e) {
            e.printStackTrace();
        }
        optionFrame = new OptionFrame();
        frame.getLayeredPane().add(left, 1);
        frame.getLayeredPane().add(center, 2);
        frame.getLayeredPane().add(right, 3);
        frame.getLayeredPane().add(up, 4);
        this.initFrame();
    }

    /**
     * Creates an instance of the GUIManager class
     *
     * @return an instance of the GUIManager class
     */
    public static GUIManager getInstance() {
        if (instance == null)
            instance = new GUIManager();
        return instance;
    }

    /**
     * checks if toggle side mode is on
     *
     * @return true if yes and false if not
     */
    public boolean isToggleSide() {
        return isToggleSide;
    }

    /**
     * toggle side mode setter
     *
     * @param toggleSide true if is enabled and false if not
     */
    public void setToggleSide(boolean toggleSide) {
        isToggleSide = toggleSide;
    }

    /**
     * Shows the GUI
     */
    public void showGUI() {
        splashScreen.dispose();
        frame.setLocation(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2);
        frame.setVisible(true);
    }

    /**
     * ReArranges the GUI
     */
    public void reArrange() {
        up.setBounds(0, 0, frame.getWidth(), 20);
        if (isToggleSide()) {
            left.setVisible(false);
            if (!isFullScreen()) {
                center.setBounds(0, 20, frame.getWidth() / 2, frame.getHeight() - 45);
                right.setBounds(frame.getWidth() / 2, 20, frame.getWidth() / 2 - 20, frame.getHeight() - 45);
            } else {
                center.setBounds(0, 20, frame.getWidth() / 2, frame.getHeight());
                right.setBounds(frame.getWidth() / 2, 20, frame.getWidth() / 2, frame.getHeight());
            }
        } else {
            left.setVisible(true);
            if (!isFullScreen()) {
                left.setBounds(0, 20, 200, frame.getHeight() - 45);
                center.setBounds(200, 20, (frame.getWidth() - 200) / 2, frame.getHeight() - 45);
                right.setBounds(200 + (frame.getWidth() - 200) / 2, 20, (frame.getWidth() - 200) / 2 - 20, frame.getHeight() - 45);
            } else {
                left.setBounds(0, 20, 200, frame.getHeight());
                center.setBounds(200, 20, (frame.getWidth() - 200) / 2, frame.getHeight());
                right.setBounds(200 + (frame.getWidth() - 200) / 2, 20, (frame.getWidth() - 200) / 2, frame.getHeight());
            }
        }
        left.reArrange();
        center.reArrange();
        right.reArrange();
    }

    /**
     * Main frame getter
     *
     * @return Main frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Main frame setter
     */
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    /**
     * checks if the program is in fullscreen mode
     *
     * @return true if yes and false if not
     */
    public boolean isFullScreen() {
        return isFullScreen;
    }

    /**
     * fullscreen mode setter
     */
    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }

    /**
     * Option frame getter
     *
     * @return Option frame
     */
    public OptionFrame getOptionFrame() {
        return optionFrame;
    }

    /**
     * Shows the splash screen
     */
    public void showSplashScreen() {
        splashScreen = new JFrame();
        JLabel img = new JLabel();
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(new File(".").getAbsolutePath() + "\\Assets\\SplashScreen.png"));
            img = new JLabel(new ImageIcon(bufferedImage));
        } catch (IOException e) {
            System.out.println("Resource not found");
            System.exit(-1);
        }
        splashScreen.add(img);
        splashScreen.setLocation(screenSize.width / 2 - 1000 / 2, screenSize.height / 2 - 384 / 2);
        splashScreen.setUndecorated(true);
        splashScreen.pack();
        splashScreen.setVisible(true);
    }

    /**
     * Initilizes the frame listeners
     */
    public void initFrame() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the program?", "Are you sure?", JOptionPane.YES_NO_OPTION);
                System.out.println(result);
                if (result == JOptionPane.YES_NO_OPTION) {
                    if (optionFrame.getSystemTray()) {
                        frame.setVisible(false);
                        TrayIcon icon = null;
                        if (SystemTray.isSupported()) {
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
                            icon = new TrayIcon(image, "Jsomnia", popup);
                            icon.setImageAutoSize(true);
                            try {
                                tray.add(icon);
                            } catch (AWTException ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else
                        System.exit(0);
                } else {
                    System.out.println(".");
                    frame.setVisible(true);
                }
            }
        });
        frame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == JFrame.MAXIMIZED_BOTH) {
                    reArrange();
                }
            }
        });
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reArrange();
            }
        });
    }
}
