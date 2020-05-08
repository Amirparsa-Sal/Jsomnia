package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LeftRequestList extends JPanel {

    private boolean isFiltered;
    private LinkedHashMap<JPanel,Request> filteredRequestPanels;
    private LinkedHashMap<JPanel,Request> allRequestPanels;
    private JButton newRequest;
    private JButton filterButton;
    private JButton resetButton;
    private JLabel title;
    private JTextField filterField;
    private Color bgColor;
    private RequestDialog requestDialog;
    private JFrame mother;

    public LeftRequestList(int x, int y, Dimension size, Color bgColor, JFrame mother) {
        super();
        //init panel
        isFiltered = false;
        this.setLayout(null);
        this.setBounds(x, y, size.width, size.height);
        this.setBackground(bgColor);
        this.bgColor = bgColor;
        allRequestPanels = new LinkedHashMap<>();
        filteredRequestPanels = new LinkedHashMap<>();
        Color requestDialogColor = new Color(bgColor.getRed() - 20, bgColor.getGreen() - 20, bgColor.getBlue() - 20);
        requestDialog = new RequestDialog(0, 0, new Dimension(270, 120), requestDialogColor, this);
        this.mother = mother;
        //init title
        title = new JLabel("Jsomnia", SwingConstants.CENTER);
        title.setBackground(new Color(0, 0, 95));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        //init request button
        newRequest = new JButton("New Request");
        newRequest.setBackground(new Color(0, 75, 0));
        newRequest.setForeground(Color.WHITE);
        newRequest.setOpaque(true);
        newRequest.setFocusable(false);
        newRequest.addActionListener(new NewRequestListener());
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
        filterButton.addActionListener(new FilterButtonListener());
        //reset button
        resetButton = new JButton("Reset");
        resetButton.setBackground(Color.DARK_GRAY);
        resetButton.setForeground(Color.WHITE);
        resetButton.setOpaque(true);
        resetButton.addActionListener(new ResetButtonListener());
        //adding components
        this.reArrange();
        this.add(title);
        this.add(newRequest);
        this.add(filterField);
        this.add(filterButton);
        this.add(resetButton);
    }

    public void addToReqList(Request request) {
        isFiltered = false;
        JPanel newRequestPanel = new JPanel();
        newRequestPanel.setLayout(null);
        newRequestPanel.setBackground(bgColor);
        JLabel requestMethodLabel = new JLabel(request.getRequestMethod().toString());
        JLabel requestNameLabel = new JLabel(request.getName());
        JButton deleteButton = new JButton("×");
        requestMethodLabel.setForeground(Color.WHITE);
        requestNameLabel.setForeground(Color.WHITE);
        requestNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        deleteButton.addActionListener(new DeleteButtonListener());
        deleteButton.setFocusable(false);
        newRequestPanel.add(requestMethodLabel);
        newRequestPanel.add(requestNameLabel);
        newRequestPanel.add(deleteButton);
        allRequestPanels.put(newRequestPanel,request);
        this.reArrange();
        requestMethodLabel.setBounds(newRequestPanel.getWidth()/12,0,newRequestPanel.getWidth()/3,newRequestPanel.getHeight());
        requestNameLabel.setBounds(newRequestPanel.getWidth()*5/12,0,newRequestPanel.getWidth()/2,newRequestPanel.getHeight());
        deleteButton.setBounds(newRequestPanel.getWidth()*5/6,0,newRequestPanel.getWidth()/5,newRequestPanel.getHeight());
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
        int width = getWidth(), height = getHeight();
        title.setBounds(0, 0, width, height / 24);
        newRequest.setBounds(0, height / 24, width, height / 24);
        filterField.setBounds(0, 2 * height / 24, width, height / 24);
        filterButton.setBounds(0, 3 * height / 24, width / 2, height / 24);
        resetButton.setBounds(width / 2, 3 * height / 24, width / 2, height / 24);
        if (!isFiltered) {
            recoveryPanels();
            ArrayList<JPanel> requestKeys = new ArrayList<>(allRequestPanels.keySet());
            for (int i = requestKeys.size() - 1; i >= 0; i--) {
                int j = requestKeys.size() - 1 - i;
                JPanel newRequestPanel= requestKeys.get(i);
                newRequestPanel.setBounds(0, (4 + j) * height / 24, width, height / 24);
                newRequestPanel.getComponent(0).setBounds(newRequestPanel.getWidth()/12,0,newRequestPanel.getWidth()/3,newRequestPanel.getHeight());
                newRequestPanel.getComponent(1).setBounds(newRequestPanel.getWidth()*5/12,0,newRequestPanel.getWidth()/2,newRequestPanel.getHeight());
                newRequestPanel.getComponent(2).setBounds(newRequestPanel.getWidth()*5/6,0,newRequestPanel.getWidth()/5,newRequestPanel.getHeight());
            }
        } else {
            ArrayList<JPanel> filteredkeys = new ArrayList<>(filteredRequestPanels.keySet());
            for (int i = filteredkeys.size() - 1; i >= 0; i--) {
                int j = filteredkeys.size() - 1 - i;
                JPanel newRequestPanel= filteredkeys.get(i);
                newRequestPanel.setBounds(0, (4 + j) * height / 24, width, height / 24);
                newRequestPanel.getComponent(0).setBounds(newRequestPanel.getWidth()/12,0,newRequestPanel.getWidth()/3,newRequestPanel.getHeight());
                newRequestPanel.getComponent(1).setBounds(newRequestPanel.getWidth()*5/12,0,newRequestPanel.getWidth()/2,newRequestPanel.getHeight());
                newRequestPanel.getComponent(2).setBounds(newRequestPanel.getWidth()*5/6,0,newRequestPanel.getWidth()/5,newRequestPanel.getHeight());
            }
        }
    }

    public void filterRequests(String search) {
        removeRequestPanels();
        filteredRequestPanels.clear();
        for (JPanel jPanel : allRequestPanels.keySet()) {
            JLabel methodLabel = (JLabel) jPanel.getComponent(0);
            JLabel nameLabel = (JLabel) jPanel.getComponent(1);
            if (methodLabel.getText().toLowerCase().contains(search.toLowerCase()) || nameLabel.getText().toLowerCase().contains(search.toLowerCase())) {
                filteredRequestPanels.put(jPanel,allRequestPanels.get(jPanel));
                this.add(jPanel);
            }
        }
        reArrange();
    }

    private void removeRequestPanels() {
        for (Component component : this.getComponents())
            if (component instanceof JPanel)
                this.remove(component);
        this.revalidate();
        this.repaint();
    }

    private void recoveryPanels() {
        removeRequestPanels();
        for (JPanel jPanel : allRequestPanels.keySet())
            this.add(jPanel);
        this.revalidate();
        this.repaint();
    }

    private class NewRequestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            requestDialog.setVisible(true);
        }
    }

    private class FilterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isFiltered = true;
            filterRequests(filterField.getText());
            System.out.println(((JButton)e.getSource()).getText());

        }
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isFiltered = false;
            reArrange();
        }
    }

    private class DeleteButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = (JPanel) ((JButton) e.getSource()).getParent();
            allRequestPanels.remove(panel);
            for (Component jPanel : LeftRequestList.this.getComponents()){
                if (jPanel.equals(panel)) {
                    LeftRequestList.this.remove(jPanel);
                    break;
                }
            }
            LeftRequestList.this.reArrange();
        }
    }
}

