package com.company.GUI;

import com.company.Logic.RequestMethod;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Represents a class for center panel of the GUI
 */
public class CenterPanel extends JPanel {

    //Background color
    private Color bgColor;
    //Request method box
    private JComboBox requestMethodBox;
    //Url text field
    private JTextField urlField;
    //Send button
    private JButton sendButton;
    //Save button
    private JButton saveButton;
    //First tabs
    private JTabbedPane tabs;
    //Second tabs
    private JTabbedPane secondTabs;
    //JSON form
    private BodyPanel bodyPanel;
    //Headers panel
    private SetHeadersPanel setHeadersPanel;
    //form data panel
    private SetHeadersPanel setFormDataPanel;
    //File chooser
    private FileChooserPanel fileChooserPanel;

    /**
     * Constructor with 4 parameters
     *
     * @param x       x of the panel
     * @param y       y of the panel
     * @param size    size of the panel
     * @param bgColor Background color of the panel
     */
    public CenterPanel(int x, int y, Dimension size, Color bgColor) {
        super();
        //init
        this.bgColor = bgColor;
        this.setLayout(null);
        this.setBorder(new LineBorder(new Color(85, 85, 85), 1));
        this.setBounds(x, y, size.width, size.height);
        this.setBackground(bgColor);
        //request method box
        requestMethodBox = new JComboBox();
        ((JLabel) requestMethodBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        for (RequestMethod requestMethod : RequestMethod.values())
            if (requestMethod != RequestMethod.UNKNOWN)
                requestMethodBox.addItem(requestMethod.toString());
        //url field
        urlField = new JTextField("Enter URL here");
        //send button
        sendButton = new JButton("Send");
        //save button
        saveButton = new JButton("Save");
        //second tabs
        setFormDataPanel = new SetHeadersPanel(bgColor, new Dimension(size.width, size.height - 50));
        setFormDataPanel.addHeader("New header", "New value", false, false);
        secondTabs = new JTabbedPane();
        bodyPanel = new BodyPanel(bgColor, new Dimension(size.width, size.height - 50), true);
        fileChooserPanel = new FileChooserPanel(bgColor, new Dimension(size.width, size.height - 50));
        secondTabs.add("Form data", setFormDataPanel);
        secondTabs.add("JSON", bodyPanel);
        secondTabs.addTab("Binary File", fileChooserPanel);
        //tabs
        tabs = new JTabbedPane();
        tabs.addTab("Body", secondTabs);
        setHeadersPanel = new SetHeadersPanel(bgColor, new Dimension(size.width, size.height - 50));
        setHeadersPanel.addHeader("New header", "New value", false, false);
        tabs.addTab("Headers", setHeadersPanel);
        //add items
        this.add(requestMethodBox);
        this.add(urlField);
        this.add(sendButton);
        this.add(saveButton);
        this.add(tabs);
        this.setVisible(true);
        reArrange();
    }

    /**
     * Rearranges the panel
     */
    public void reArrange() {
        int width = getWidth(), height = getHeight();
        requestMethodBox.setBounds(0, 0, width / 6, 40);
        urlField.setBounds(width / 6, 0, width * 3 / 6, 40);
        sendButton.setBounds(width * 4 / 6, 0, width / 6, 40);
        saveButton.setBounds(width * 5 / 6, 0, width / 6, 40);
        tabs.setBounds(1, 50, width - 1, height - 50);
        setFormDataPanel.setSize(width, height - 50);
        bodyPanel.setSize(width, height - 120);
        setHeadersPanel.setSize(width, height - 50);
        setHeadersPanel.reArrange();
        setFormDataPanel.reArrange();
        bodyPanel.reArrange();
        fileChooserPanel.reArrange();
    }

}
