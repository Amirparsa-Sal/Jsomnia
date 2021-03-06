package com.company.GUI;

import com.company.Logic.Parser;
import com.company.Logic.Request;
import com.company.Logic.RequestManager;
import com.company.Logic.RequestMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Represnts left panel of the GUI
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class LeftRequestList extends JPanel {

    //is filtered or not
    private boolean isFiltered;
    //filtered request panels
    private LinkedHashMap<JPanel, Request> filteredRequestPanels;
    //all request panels
    private LinkedHashMap<JPanel, Request> allRequestPanels;
    //background color
    private Color bgColor;
    //Title label
    private JLabel title;
    //new request button
    private JButton newRequest;
    //Filter text field
    private JTextField filterField;
    //Filter button
    private JButton filterButton;
    //Reset button
    private JButton resetButton;
    //Scroll pane of requests
    private JScrollPane scrollPane;
    //Panel of the panels!
    private JPanel panelsPanel;
    //New request Dialog
    private RequestDialog requestDialog;
    //Selected request
    private Request selectedRequest;

    /**
     * Constructor with 4 parameters
     *
     * @param x       x of the panel
     * @param y       y of the panel
     * @param size    size of the panel
     * @param bgColor Background color of the panel
     */
    public LeftRequestList(int x, int y, Dimension size, Color bgColor) {
        super();
        //init panel
        selectedRequest = null;
        isFiltered = false;
        this.setLayout(null);
        this.setBounds(x, y, size.width, size.height);
        this.setBackground(bgColor);
        this.bgColor = bgColor;
        allRequestPanels = new LinkedHashMap<>();
        filteredRequestPanels = new LinkedHashMap<>();
        requestDialog = new RequestDialog(0, 0, new Dimension(270, 120), bgColor, this);
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
        newRequest.setMnemonic('n');
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
        //adding scroll and panels
        panelsPanel = new JPanel();
        panelsPanel.setLayout(new BoxLayout(panelsPanel, BoxLayout.PAGE_AXIS));
        scrollPane = new JScrollPane(panelsPanel);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(bgColor);
        panelsPanel.setBackground(bgColor);
        //adding components
        this.reArrange();
        this.add(title);
        this.add(newRequest);
        this.add(filterField);
        this.add(filterButton);
        this.add(resetButton);
        this.add(scrollPane);
    }

    /**
     * Adds a request to the list
     *
     * @param request The request
     * @param update  update after add?
     */
    public void addToReqList(Request request, boolean update) {
        isFiltered = false;
        JPanel newRequestPanel = new JPanel();
        newRequestPanel.setLayout(null);
        newRequestPanel.setMaximumSize(new Dimension(getWidth() - 2, getHeight() / 24));
        newRequestPanel.setMinimumSize(new Dimension(getWidth() - 2, getHeight() / 24));
        JLabel requestMethodLabel = new JLabel(request.getRequestMethod().toString());
        JLabel requestNameLabel = new JLabel(request.getName());
        JButton deleteButton = new JButton("×");
        requestMethodLabel.setForeground(Color.WHITE);
        requestMethodLabel.setForeground(RequestMethod.colors.get(request.getRequestMethod().toString()));
        requestNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        requestNameLabel.setForeground(Color.WHITE);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        deleteButton.addActionListener(new DeleteButtonListener());
        deleteButton.setFocusable(false);
        newRequestPanel.add(requestMethodLabel);
        newRequestPanel.add(requestNameLabel);
        newRequestPanel.add(deleteButton);
        allRequestPanels.put(newRequestPanel, request);
        panelsPanel.add(newRequestPanel);
        if (update) {
            this.reArrange();
            GUIManager.getInstance().getFrame().dispatchEvent(new ComponentEvent(GUIManager.getInstance().getFrame(), ComponentEvent.COMPONENT_RESIZED));
        }
        newRequestPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        JPanel panel = findRequestPanel(selectedRequest);
                        changePanelLabel(panel, GUIManager.getInstance().getCenter().getMethod());
                        GUIManager.getInstance().getRight().reset();
                        selectedRequest = allRequestPanels.get(e.getComponent());
                        GUIManager.getInstance().getCenter().reset();
                        GUIManager.getInstance().getCenter().setUrl(selectedRequest.getUrl());
                        GUIManager.getInstance().getCenter().setMethod(selectedRequest.getRequestMethod().toString());
                        GUIManager.getInstance().getCenter().setSetHeadersPanelContent(Parser.headersListToMap(selectedRequest.getHeaders()));
                        if (selectedRequest.getBodyType() == Request.BodyType.FORM_DATA) {
                            GUIManager.getInstance().getCenter().getSecondTabs().setSelectedIndex(0);
                            GUIManager.getInstance().getCenter().setSetFormDataPanelContent(Parser.formDataToMap(selectedRequest.getData()));
                        } else if (selectedRequest.getBodyType() == Request.BodyType.JSON) {
                            GUIManager.getInstance().getCenter().getSecondTabs().setSelectedIndex(1);
                            GUIManager.getInstance().getCenter().setBodyPanelText(selectedRequest.getData());
                        } else if (selectedRequest.getBodyType() == Request.BodyType.BINARY_FILE) {
                            GUIManager.getInstance().getCenter().getSecondTabs().setSelectedIndex(2);
                            GUIManager.getInstance().getCenter().setFileName(selectedRequest.getData());
                        } else if (selectedRequest.getBodyType() == Request.BodyType.URL_ENCODED) {
                            GUIManager.getInstance().getCenter().getSecondTabs().setSelectedIndex(2);
                            GUIManager.getInstance().getCenter().setFormUrlEncodedPanelContent(Parser.formDataToMap(selectedRequest.getData()));
                        }
                        GUIManager.getInstance().getCenter().setOutputPathName(selectedRequest.getOutputName());
                        GUIManager.getInstance().getRight().setResponse(selectedRequest.getResponse());
                        return null;
                    }
                };
                worker.execute();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ArrayList<JPanel> ourPanels;
                if (isFiltered)
                    ourPanels = new ArrayList(filteredRequestPanels.keySet());
                else
                    ourPanels = new ArrayList<>(allRequestPanels.keySet());
                if (ourPanels.indexOf(e.getComponent()) % 2 == 0)
                    e.getComponent().setBackground(bgColor);
                else
                    e.getComponent().setBackground(new Color(bgColor.getRed() + 10, bgColor.getGreen() + 10, bgColor.getBlue() + 10));
            }

        });

    }

    /**
     * Rearranges the panel
     */
    public void reArrange() {
        int width = getWidth(), height = getHeight();
        title.setBounds(0, 0, width, height / 24);
        newRequest.setBounds(0, height / 24, width, height / 24);
        filterField.setBounds(0, 2 * height / 24, width, height / 24);
        filterButton.setBounds(0, 3 * height / 24, width / 2, height / 24);
        resetButton.setBounds(width / 2, 3 * height / 24, width / 2, height / 24);
        scrollPane.setBounds(1, 4 * height / 24, width - 2, height * 5 / 6 - 30);
        panelsPanel.setBounds(1, 4 * height / 24, width - 2, height * 5 / 6 - 30);
        ArrayList<JPanel> keys = new ArrayList<>();
        if (!isFiltered) {
            removeRequestPanels();
            keys = new ArrayList<>(allRequestPanels.keySet());
        } else
            keys = new ArrayList<>(filteredRequestPanels.keySet());
        for (int i = keys.size() - 1; i >= 0; i--) {
            JPanel newRequestPanel = keys.get(i);
            newRequestPanel.setMaximumSize(new Dimension(width - 2, height / 24));
            newRequestPanel.setMinimumSize(new Dimension(width - 2, height / 24));
            newRequestPanel.getComponent(0).setBounds(newRequestPanel.getWidth() / 12, 0, newRequestPanel.getWidth() / 4, newRequestPanel.getHeight());
            newRequestPanel.getComponent(1).setBounds(newRequestPanel.getWidth() * 4 / 12 + 5, 0, newRequestPanel.getWidth() * 5 / 12 - 5, newRequestPanel.getHeight());
            newRequestPanel.getComponent(2).setBounds(newRequestPanel.getWidth() * 9 / 12, 0, newRequestPanel.getWidth() / 5, newRequestPanel.getHeight());
            if (i % 2 == 0)
                newRequestPanel.setBackground(bgColor);
            else
                newRequestPanel.setBackground(new Color(bgColor.getRed() + 10, bgColor.getGreen() + 10, bgColor.getBlue() + 10));

            panelsPanel.add(newRequestPanel);
        }
    }

    /**
     * gets filtered request panels
     *
     * @return map of panels
     */
    public LinkedHashMap<JPanel, Request> getFilteredRequestPanels() {
        return filteredRequestPanels;
    }

    /**
     * gets all request panels
     *
     * @return map of panels
     */
    public LinkedHashMap<JPanel, Request> getAllRequestPanels() {
        return allRequestPanels;
    }

    /**
     * checks if the panel is filtered
     *
     * @return true if yes and false if not
     */
    public boolean isFiltered() {
        return isFiltered;
    }

    /**
     * Filters the request
     *
     * @param search filter string
     */
    public void filterRequests(String search) {
        removeRequestPanels();
        filteredRequestPanels.clear();
        for (JPanel jPanel : allRequestPanels.keySet()) {
            JLabel methodLabel = (JLabel) jPanel.getComponent(0);
            JLabel nameLabel = (JLabel) jPanel.getComponent(1);
            if (methodLabel.getText().toLowerCase().contains(search.toLowerCase()) || nameLabel.getText().toLowerCase().contains(search.toLowerCase())) {
                filteredRequestPanels.put(jPanel, allRequestPanels.get(jPanel));
                this.add(jPanel);
            }
        }
        reArrange();
    }

    /**
     * finds request panel by request
     *
     * @param request the request
     * @return request panel
     */
    public JPanel findRequestPanel(Request request) {
        for (JPanel panel : allRequestPanels.keySet())
            if (allRequestPanels.get(panel) == request)
                return panel;
        return null;
    }

    /**
     * changes method label of panel
     *
     * @param panel   the label
     * @param newText new text of label
     */
    public void changePanelLabel(JPanel panel, String newText) {
        if (panel == null)
            return;
        ((JLabel) panel.getComponent(0)).setText(newText);
        panel.getComponent(0).setForeground(RequestMethod.colors.get(newText));
    }

    /**
     * gets selected request
     *
     * @return selected request
     */
    public Request getSelectedRequest() {
        return selectedRequest;
    }

    /**
     * Sets selected request
     *
     * @param selectedRequest new selected request
     */
    public void setSelectedRequest(Request selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

    /**
     * removes all of request panels from the panel of the panels
     */
    private void removeRequestPanels() {
        for (Component component : panelsPanel.getComponents())
            if (component instanceof JPanel)
                panelsPanel.remove(component);
        this.revalidate();
        this.repaint();
    }

    private class NewRequestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            requestDialog.setLocation(GUIManager.screenSize.width / 2 - requestDialog.getWidth() / 2, GUIManager.screenSize.height / 2 - requestDialog.getHeight() / 2);
            requestDialog.setVisible(true);
        }
    }

    private class FilterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isFiltered = true;
            filterRequests(filterField.getText());
        }
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            filterField.setText("");
            isFiltered = false;
            reArrange();
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the request?", "Are you sure?", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                JPanel panel = (JPanel) ((JButton) e.getSource()).getParent();
                Request request = allRequestPanels.get(panel);
                if (request.getSaveFileName() != null) {
                    String saveName = request.getSaveFileName();
                    int reqNumber = Integer.parseInt(saveName.substring(7));
                    int numOfRequests = RequestManager.getInstance().getNumberOfRequests();
                    RequestManager.getInstance().deleteRequestFromList(reqNumber);
                    HashMap<String, String> changes = RequestManager.getInstance().reArrangeList(numOfRequests);
                    for (JPanel p : allRequestPanels.keySet()) {
                        Request req = allRequestPanels.get(p);
                        if (req.getSaveFileName() != null && changes.get(req.getSaveFileName()) != null) {
                            req.setSaveFileName(changes.get(req.getSaveFileName()));
                        }
                    }
                }
                if (selectedRequest == request) {
                    selectedRequest = null;
                    GUIManager.getInstance().getCenter().reset();
                    GUIManager.getInstance().getRight().reset();
                }
                allRequestPanels.remove(panel);
                for (Component jPanel : LeftRequestList.this.getComponents()) {
                    if (jPanel.equals(panel)) {
                        LeftRequestList.this.remove(jPanel);
                        break;
                    }
                }
                LeftRequestList.this.reArrange();
            }
        }
    }
}