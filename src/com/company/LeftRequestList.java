package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
    private RequestDialog requestDialog;
    private JFrame mother;

    public LeftRequestList(int x, int y, Dimension size, Color bgColor, JFrame mother) {
        super();
        //init panel
        isFiltered = false;
        this.setLayout(null);
        this.setBounds(x, y, size.width, size.height);
        this.setBackground(bgColor);
//        this.setBorder(new LineBorder(new Color(85,85,85),1));
        this.size = size;
        this.bgColor = bgColor;
        allRequestPanels = new ArrayList<>();
        filteredRequestPanels = new ArrayList<>();
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
        title.setBounds(0, 0, size.width, size.height / 25);
        newRequest.setBounds(0, size.height / 25, size.width, size.height / 25);
        filterField.setBounds(0, 2 * size.height / 25, size.width, size.height / 25);
        filterButton.setBounds(0, 3 * size.height / 25, size.width / 2, size.height / 25);
        resetButton.setBounds(size.width / 2, 3 * size.height / 25, size.width / 2, size.height / 25);
        if (!isFiltered) {
            recoveryPanels();
            for (int i = allRequestPanels.size() - 1; i >= 0; i--) {
                int j = allRequestPanels.size() - 1 - i;
                allRequestPanels.get(i).setBounds(0, (4 + j) * size.height / 25, size.width, size.height / 25);
            }
        } else {
            for (int i = filteredRequestPanels.size() - 1; i >= 0; i--) {
                int j = filteredRequestPanels.size() - 1 - i;
                filteredRequestPanels.get(i).setBounds(0, (4 + j) * size.height / 25, size.width, size.height / 25);
            }
        }
    }

    public void filterRequests(String search) {
        removeRequestPanels();
        filteredRequestPanels.clear();
        for (JPanel jPanel : allRequestPanels) {
            JLabel methodLabel = (JLabel) jPanel.getComponent(0);
            JLabel nameLabel = (JLabel) jPanel.getComponent(1);
            if (methodLabel.getText().toLowerCase().contains(search.toLowerCase()) || nameLabel.getText().toLowerCase().contains(search.toLowerCase())) {
                filteredRequestPanels.add(jPanel);
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
        for (JPanel jPanel : allRequestPanels)
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
}

