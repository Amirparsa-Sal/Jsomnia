package com.company.GUI;

import com.company.Logic.Request;
import com.company.Logic.RequestMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a class for request dialog
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class RequestDialog extends JFrame {

    //Background color
    private Color bgcolor;
    //Description label
    private JLabel description;
    //Request name text field
    private JTextField requestNameField;
    //Request method combo box
    private JComboBox requestMethodBox;
    //OK Button
    private JButton okButton;
    //Cancel Button
    private JButton cancelButton;
    //Left request list
    private LeftRequestList leftBar;

    /**
     * Constructor with 5 parameter
     *
     * @param x       x of the frame
     * @param y       y of the frame
     * @param size    size of the frame
     * @param bgcolor Background color of the frame
     * @param leftBar Left request list
     */
    public RequestDialog(int x, int y, Dimension size, Color bgcolor, LeftRequestList leftBar) {
        super();
        this.leftBar = leftBar;
        //init window
        this.setLayout(null);
        this.setSize(size.width + 6, size.height + 35);
        this.setResizable(false);
        this.setBackground(bgcolor);
        this.bgcolor = bgcolor;
        //init Description
        this.description = new JLabel("Enter the request name and choose method:");
        this.description.setBounds(0, 0, size.width, size.height / 3);
        this.description.setOpaque(true);
        this.description.setBackground(bgcolor);
        this.description.setForeground(Color.WHITE);
        //init box
        requestMethodBox = new JComboBox();
        ((JLabel) requestMethodBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        for (RequestMethod requestMethod : RequestMethod.values())
            if (requestMethod != RequestMethod.UNKNOWN) {
                requestMethodBox.addItem(requestMethod.toString());
            }
        requestMethodBox.setBackground(bgcolor);
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
        this.setVisible(false);
        this.add(this.description);
        this.add(requestMethodBox);
        this.add(requestNameField);
        this.add(okButton);
        this.add(cancelButton);
    }

    private void clear() {
        requestNameField.setText("Request name");
        requestMethodBox.setSelectedIndex(0);
    }

    private class OkListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String requestName = requestNameField.getText();
            if (!requestName.equals("")) {
                String requestMethod = ((String) requestMethodBox.getSelectedItem());
                leftBar.addToReqList(new Request(requestName, RequestMethod.valueOf(requestMethod)));
                clear();
                setVisible(false);
            }
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
