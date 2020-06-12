package com.company.GUI;

import com.company.Logic.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
        urlField = new JTextField("");
        urlField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(GUIManager.getInstance().getLeft().getSelectedRequest()!=null)
                    GUIManager.getInstance().getLeft().getSelectedRequest().setUrl(urlField.getText());
            }
        });
        //send button
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        fillRequestData(true);
                        Request request = GUIManager.getInstance().getLeft().getSelectedRequest();
                        GUIManager.getInstance().getRight().setResponse(RequestManager.getInstance().sendRequest(request));
                        return null;
                    }
                };
                try {
                    worker.execute();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        //save button
        saveButton = new JButton("Save");
        //second tabs
        setFormDataPanel = new SetHeadersPanel("New key", "New value", bgColor, new Dimension(size.width, size.height - 50));
        secondTabs = new JTabbedPane();
        bodyPanel = new BodyPanel(bgColor, new Dimension(size.width, size.height - 50), true);
        fileChooserPanel = new FileChooserPanel(bgColor, new Dimension(size.width, size.height - 50));
        secondTabs.add("Form data", setFormDataPanel);
        secondTabs.add("JSON", bodyPanel);
        secondTabs.addTab("Binary File", fileChooserPanel);
        //tabs
        tabs = new JTabbedPane();
        tabs.addTab("Body", secondTabs);
        setHeadersPanel = new SetHeadersPanel("New header", "New value", bgColor, new Dimension(size.width, size.height - 50));
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

    public void fillRequestData(boolean checkboxEffect){
        Request request = GUIManager.getInstance().getLeft().getSelectedRequest();
        if(request==null)
            return;
        request.setRequestMethod(RequestMethod.getMethod((String)(requestMethodBox.getSelectedItem())));
        request.setUrl(urlField.getText());
        //headers

        for(String key : setHeadersPanel.getMap(checkboxEffect).keySet())
            request.addHeader(new RequestHeader(key,setHeadersPanel.getMap(checkboxEffect).get(key)));
        if(secondTabs.getSelectedIndex()==0){
            //form data
            request.setBodyType(Request.BodyType.FORM_DATA);
            request.setData(Parser.splitFormDataMap(setFormDataPanel.getMap(checkboxEffect)));
        }
        else if(secondTabs.getSelectedIndex()==1){
            request.setBodyType(Request.BodyType.JSON);
            request.setData(bodyPanel.getText());
        }
        else if(secondTabs.getSelectedIndex()==2){
            request.setBodyType(Request.BodyType.BINARY_FILE);
            if(fileChooserPanel.getCurrentFileName()!=null)
                request.setData(fileChooserPanel.getCurrentFileName());
            else
                request.setData("");
        }
        request.setFollowRedirection(GUIManager.getInstance().getOptionFrame().getFollowRedirect());
    }
    public void reset(){
        requestMethodBox.setSelectedItem("GET");
        urlField.setText("");
        bodyPanel.reset();
        setHeadersPanel.reset();
        setFormDataPanel.reset();
        fileChooserPanel.reset();
    }
    public void setUrl(String url){
        urlField.setText(url);
    }
    public String getUrl(){
        return urlField.getText();
    }
    public String getMethod(){
        return (String)(requestMethodBox.getSelectedItem());
    }
    public void setMethod(String method){
        requestMethodBox.setSelectedItem(method);
    }
    public void setSetHeadersPanelContent(LinkedHashMap<String,String> map){
        setHeadersPanel.setFields(map);
    }
    public void setSetFormDataPanelContent(LinkedHashMap<String,String> map){
        setFormDataPanel.setFields(map);
    }
    public void setBodyPanelText(String text){
        bodyPanel.setText(text);
    }

    public void setFileName(String name){
        fileChooserPanel.setFileName(name);
    }
    public JTabbedPane getSecondTabs() {
        return secondTabs;
    }
}
