package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class LeftRequestList extends JPanel {

    private ArrayList<JLabel> requests;
    private Color bgColor;

    public LeftRequestList(int width, int height, Color bgcolor){
        this.bgColor = bgcolor;
        requests = new ArrayList<>();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(bgcolor);
        this.setPreferredSize(new Dimension(width, height));
    }

    public void addRequest(RequestMethod requestMethod, String requestName){
        JLabel label = new JLabel(requestMethod.toString() + "   " + requestName);
        label.setForeground(Color.WHITE);
        label.setBackground(bgColor);
        label.setOpaque(true);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(Color.DARK_GRAY);
                System.out.println("Entered");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(bgColor);
                System.out.println("Exited");
            }
        });
        this.add(label);
        requests.add(label);
    }

}
