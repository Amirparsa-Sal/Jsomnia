package com.company.GUI;

import com.company.Logic.Response;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;

/**
 * Represents the right panel of the GUI
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class RightResponsePanel extends JPanel {
    //Background color
    private Color bgColor;
    //Status label
    private JLabel statusLabel;
    //Time label
    private JLabel timeLabel;
    //Size label
    private JLabel sizeLabel;
    //First tabs
    private JTabbedPane tabs;
    //Second tabs
    private JTabbedPane secondTabs;
    //Web viewer
    private WebPreviewPanel webViewer;
    //Raw data panel
    private BodyPanel rawTextPanel;
    //Headers Panel
    private HeadersTablePanel headersTablePanel;

    /**
     * Constructor with 4 parameters
     *
     * @param x       x of the panel
     * @param y       y of the panel
     * @param size    size of the panel
     * @param bgColor Background color of the panel
     */
    public RightResponsePanel(int x, int y, Color bgColor, Dimension size) throws IOException {
        this.bgColor = bgColor;
        this.setLayout(null);
        this.setBounds(x, y, size.width, size.height);
        this.setBackground(bgColor);
        this.setBorder(new LineBorder(new Color(85, 85, 85), 1));
        //init labels
        statusLabel = new JLabel("0 Unknown", SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.GRAY);
        statusLabel.setForeground(Color.WHITE);
        timeLabel = new JLabel("0 ms", SwingConstants.CENTER);
        timeLabel.setOpaque(true);
        timeLabel.setBackground(Color.GRAY);
        timeLabel.setForeground(Color.WHITE);
        sizeLabel = new JLabel("0 B", SwingConstants.CENTER);
        sizeLabel.setOpaque(true);
        sizeLabel.setBackground(Color.GRAY);
        sizeLabel.setForeground(Color.WHITE);
        //init body
        JPanel body = new JPanel();
        body.setLayout(null);
        rawTextPanel = new BodyPanel(bgColor, new Dimension(size.width, size.height - 80), false);
        webViewer = new WebPreviewPanel(bgColor, new Dimension(size.width, size.height - 80));
        //init header
        headersTablePanel = new HeadersTablePanel(bgColor, new Dimension(size.width, size.height - 50));
        //init tabs
        tabs = new JTabbedPane();
        secondTabs = new JTabbedPane();
        tabs.add("Preview mode", secondTabs);
        tabs.add("Headers", headersTablePanel);
        secondTabs.add("Raw data", rawTextPanel);
        secondTabs.add("Visual preview", webViewer);
        tabs.addChangeListener(new TabChangeListener());
        secondTabs.addChangeListener(new TabChangeListener());
        this.add(statusLabel);
        this.add(timeLabel);
        this.add(sizeLabel);
        this.add(tabs);
        this.setVisible(true);
        this.reArrange();
    }

    /**
     * Rearranges the panel
     */
    public void reArrange() {
        int width = getWidth(), height = getHeight();
        rawTextPanel.setBounds(10, 10, width - 20, height - 120);
        statusLabel.setBounds(10, 0, width / 3, 40);
        timeLabel.setBounds(width / 3 + 20, 0, width / 6, 40);
        sizeLabel.setBounds(width * 1 / 2 + 30, 0, width / 6, 40);
        tabs.setBounds(10, 50, width - 20, height - 60);
        rawTextPanel.reArrange();
        webViewer.reArrange();
        headersTablePanel.reArrange();
    }

    public void setResponse(Response response) {
        reset();
        rawTextPanel.setText(response.getBody());
        for (String key : response.getHeaders().keySet()) {
            String values = "";
            for (String value : response.getHeaders().get(key))
                values += value + " ";
            headersTablePanel.addRow(key, values);
        }
        webViewer.setPreview(response);
        statusLabel.setText(response.getCode() + " " + response.getResponseMessage());
        timeLabel.setText(response.getTime() + " ms");
        sizeLabel.setText(response.getSize() + " " + response.getSizeUnit());
        if (response.getCode() / 100 == 2)
            statusLabel.setBackground(new Color(0, 75, 0));
        else if (response.getCode() / 100 == 3)
            statusLabel.setBackground(Color.ORANGE);
        else if (response.getCode() / 100 == 4)
            statusLabel.setBackground(Color.RED);
    }

    public void reset() {
        rawTextPanel.setText("");
        headersTablePanel.reset();
        webViewer.reset();
        statusLabel.setText("0 Unknown");
        timeLabel.setText("0 ms");
        sizeLabel.setText("0 B");
    }

    private class TabChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            GUIManager.getInstance().getFrame().requestFocus();
        }
    }
}
