package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class LeftRequestList extends JPanel {

    private Dimension size;
    private ArrayList<JPanel> requestPanels;
    private JButton newRequest;
    private JButton filterButton;
    private JLabel title;
    private JTextField filterField;
    private Color bgColor;

    public LeftRequestList(int x, int y, Dimension size, Color bgColor) {
        //init panel
        this.setLayout(null);
        this.setBounds(x, y, size.width, size.height);
        this.setBackground(bgColor);
        this.size = size;
        this.bgColor = bgColor;
        //init title
        title = new JLabel("Jsomnia", SwingConstants.CENTER);
        title.setBackground(Color.BLUE);
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        //init request button
        newRequest = new JButton("New Request");
        newRequest.setBackground(Color.DARK_GRAY);
        newRequest.setForeground(Color.WHITE);
        newRequest.setOpaque(true);
        newRequest.setFocusable(false);
        requestPanels = new ArrayList<>();
        //init filter text field
        filterField = new JTextField("Filter");
        filterField.setBackground(Color.DARK_GRAY);
        filterField.setForeground(Color.WHITE);
        filterField.setBorder(null);
        //init filter button
        filterButton = new JButton("Filter");
        filterButton.setBackground(Color.DARK_GRAY);
        filterButton.setForeground(Color.WHITE);
        filterButton.setOpaque(true);
        //adding components
        this.reArrange();
        this.add(title);
        this.add(newRequest);
        this.add(filterField);
        this.add(filterButton);

    }

    public void addToReqList(RequestMethod requestMethod, String requestName) {
        JPanel newRequestPanel = new JPanel();
        newRequestPanel.setBackground(bgColor);
        JLabel requestMethodLabel = new JLabel(requestMethod.toString());
        JLabel requestNameLabel = new JLabel(requestName);
        requestMethodLabel.setForeground(Color.WHITE);
        requestNameLabel.setForeground(Color.WHITE);
        requestMethodLabel.setAlignmentX(SwingConstants.LEFT);
        requestNameLabel.setAlignmentX(SwingConstants.RIGHT);
        newRequestPanel.add(requestMethodLabel);
        newRequestPanel.add(requestNameLabel);
        requestPanels.add(newRequestPanel);
        this.reArrange();
        this.add(newRequestPanel);
        newRequestPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(bgColor);
            }
        });
    }

    public void reArrange() {
        int x = getX(), y = getY();
        title.setBounds(x, y, size.width, size.height / 25);
        newRequest.setBounds(x, y + size.height / 25, size.width, size.height / 25);
        filterField.setBounds(x, y + 2 * size.height / 25, size.width * 2 / 3, size.height / 25);
        filterButton.setBounds(x + size.width * 2 / 3, y + 2 * size.height / 25, size.width / 3, size.height / 25);
        for (int i = requestPanels.size() - 1; i >= 0; i--){
            int j = requestPanels.size() - 1 - i;
            requestPanels.get(i).setBounds(x, y + (3 + j) * size.height / 25, size.width, size.height / 25);
        }
    }
}
