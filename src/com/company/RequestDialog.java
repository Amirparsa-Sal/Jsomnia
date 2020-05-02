package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestDialog extends JPanel {

    private Color bgcolor;
    private JLabel description;
    private JTextField requestNameField;
    private JComboBox<String> requestMethodBox;
    private JButton okButton;
    private JButton cancelButton;
    private LeftRequestList leftBar;

    public RequestDialog(int x, int y, Dimension size, Color bgcolor, LeftRequestList leftBar) {
        this.leftBar = leftBar;
        //init window
        this.setBounds(x, y, size.width, size.height);
        this.setLayout(null);
        this.setBackground(bgcolor);
        this.bgcolor = bgcolor;
        //init Description
        this.description = new JLabel("Enter the request name and choose method:");
        this.description.setBounds(0, 0, size.width, size.height / 3);
        this.description.setOpaque(true);
        this.description.setBackground(bgcolor);
        this.description.setForeground(Color.WHITE);
        //init box
        requestMethodBox = new JComboBox<String>();
        for (RequestMethod requestMethod : RequestMethod.values())
            if (requestMethod != RequestMethod.UNKNOWN)
                requestMethodBox.addItem(requestMethod.toString());
        requestMethodBox.setBackground(bgcolor);
        requestMethodBox.setForeground(Color.WHITE);
        requestMethodBox.setBounds(0, size.height / 3, size.width / 3, size.height / 3);
        //init Text Field
        requestNameField = new JTextField("Request name");
        requestNameField.setOpaque(true);
        requestNameField.setBackground(bgcolor);
        requestNameField.setForeground(Color.WHITE);
        requestNameField.setBounds(size.width / 3, size.height / 3, size.width * 2 / 3, size.height / 3);
        //init ok
        okButton = new JButton("OK");
        okButton.setOpaque(true);
        okButton.setBackground(new Color(0, 75, 0));
        okButton.setForeground(Color.WHITE);
        okButton.setBounds(0, size.height * 2 / 3, size.width / 2, size.height / 3);
        okButton.addActionListener(new OkListener());
        //init
        cancelButton = new JButton("Cancel");
        cancelButton.setOpaque(true);
        cancelButton.setBackground(new Color(125, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBounds(size.width / 2, size.height * 2 / 3, size.width / 2, size.height / 3);
        cancelButton.addActionListener(new CancelListener());
        //show
        this.add(this.description);
        this.add(requestNameField);
        this.add(requestMethodBox);
        this.add(okButton);
        this.add(cancelButton);
        this.setVisible(false);
    }

    private void clear() {
        requestNameField.setText("Request name");
        requestMethodBox.setSelectedIndex(0);
    }

    private class OkListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String requestName = requestNameField.getText();
            String requestMethod = ((String) requestMethodBox.getSelectedItem());
            leftBar.addToReqList(RequestMethod.valueOf(requestMethod), requestName);
            clear();
            setVisible(false);
        }
    }

    private class CancelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            clear();
            setVisible(false);
        }
    }
}
