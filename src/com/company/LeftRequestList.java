package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class LeftRequestList extends JPanel {

    private boolean isFiltered;
    private Dimension size;
    private ArrayList<JPanel> filteredRequestPanels;
    private ArrayList<JPanel> allRequestPanels;
    private JButton newRequest;
    private JButton filterButton;
    private JButton resetButton;
    private JLabel title;
    private JTextField filterField;
    private Color bgColor;

    public LeftRequestList(int x, int y, Dimension size, Color bgColor) {
        //init panel
        isFiltered = false;
        this.setLayout(null);
        this.setBounds(x, y, size.width, size.height);
        this.setBackground(bgColor);
        this.size = size;
        this.bgColor = bgColor;
        filteredRequestPanels = new ArrayList<>();
        //init title
        title = new JLabel("Jsomnia", SwingConstants.CENTER);
        title.setBackground(Color.BLUE);
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        //init request button
        newRequest = new JButton("New Request");
        newRequest.setBackground(new Color(0,75,0));
        newRequest.setForeground(Color.WHITE);
        newRequest.setOpaque(true);
        newRequest.setFocusable(false);
        allRequestPanels = new ArrayList<>();
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
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFiltered = true;
                filterRequests(filterField.getText());
            }
        });
        //reset button
        resetButton = new JButton("Reset");
        resetButton.setBackground(Color.DARK_GRAY);
        resetButton.setForeground(Color.WHITE);
        resetButton.setOpaque(true);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFiltered = false;
                reArrange();
            }
        });
        //adding components
        this.reArrange();
        this.add(title);
        this.add(newRequest);
        this.add(filterField);
        this.add(filterButton);
        this.add(resetButton);
    }

    public void addToReqList(RequestMethod requestMethod, String requestName) {
        isFiltered = false;
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
        allRequestPanels.add(newRequestPanel);
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
        filterField.setBounds(x, y + 2 * size.height / 25, size.width, size.height / 25);
        filterButton.setBounds(x , y + 3 * size.height / 25, size.width / 2, size.height / 25);
        resetButton.setBounds(x + size.width / 2, y + 3 * size.height / 25, size.width / 2, size.height / 25);
        if(!isFiltered) {
            recoveryPanels();
            for (int i = allRequestPanels.size() - 1; i >= 0; i--) {
                int j = allRequestPanels.size() - 1 - i;
                allRequestPanels.get(i).setBounds(x, y + (4 + j) * size.height / 25, size.width, size.height / 25);
            }
        }
        else{
            for (int i = filteredRequestPanels.size() - 1; i >= 0; i--) {
                int j = filteredRequestPanels.size() - 1 - i;
                filteredRequestPanels.get(i).setBounds(x, y + (4 + j) * size.height / 25, size.width, size.height / 25);
            }
        }
    }

    public void filterRequests(String search){
        removeRequestPanels();
        filteredRequestPanels.clear();
        for(JPanel jPanel : allRequestPanels){
            JLabel methodLabel = (JLabel)jPanel.getComponent(0);
            JLabel nameLabel = (JLabel)jPanel.getComponent(1);
            if(methodLabel.getText().toLowerCase().contains(search.toLowerCase()) || nameLabel.getText().toLowerCase().contains(search.toLowerCase())) {
                filteredRequestPanels.add(jPanel);
                this.add(jPanel);
            }
        }
        reArrange();
    }

    private void removeRequestPanels(){
        for(Component component : this.getComponents())
            if(component instanceof JPanel)
                this.remove(component);
        this.revalidate();
        this.repaint();
    }
    private void recoveryPanels(){
        removeRequestPanels();
        for(JPanel jPanel : allRequestPanels)
            this.add(jPanel);
        this.revalidate();
        this.repaint();
    }
}
