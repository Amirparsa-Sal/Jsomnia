package com.company.GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a class for headers panel
 */
public class HeadersTablePanel extends JPanel {

    //Background color
    private Color bgColor;
    //Table of the haeders
    private JTable headersTable;
    //Scroll
    private JScrollPane scrollPane;
    //Save to clipboard button
    private JButton saveToClipButton;
    //Number of rows
    private int numOfRows;

    /**
     * Constrcutor with 2 paramters
     *
     * @param bgColor Background of the panel
     * @param size    Size of the panel
     */
    public HeadersTablePanel(Color bgColor, Dimension size) {
        //init
        numOfRows = 0;
        this.setLayout(null);
        this.bgColor = bgColor;
        this.setBackground(bgColor);
        this.setBounds(0, 0, size.width, size.height);
        //table
        headersTable = new JTable(new DefaultTableModel(null, new Object[]{"Name", "Value"})) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //scroll
        scrollPane = new JScrollPane(headersTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBackground(bgColor);
        scrollPane.getViewport().setBackground(bgColor);
        scrollPane.getViewport().setFocusable(true);
        //button
        saveToClipButton = new JButton("Save to clipboard");
        saveToClipButton.setOpaque(false);
        saveToClipButton.setBackground(bgColor);
        saveToClipButton.setForeground(Color.WHITE);
        saveToClipButton.setBorder(new LineBorder(Color.BLACK, 1));
        saveToClipButton.addActionListener(new ClipboardListener());
        this.reArrange();
        this.add(scrollPane);
        this.add(saveToClipButton);
    }

    /**
     * Rearranges the panel
     */
    public void reArrange() {
        int width = getWidth(), height = getHeight();
        saveToClipButton.setBounds(width / 4, height - 110, width / 2, 60);
        scrollPane.setBounds(10, 10, width - 20, height - 120);
    }

    public void addRow(String name, String value) {
        DefaultTableModel dtm = (DefaultTableModel) headersTable.getModel();
        dtm.addRow(new Object[]{name, value});
        numOfRows++;
    }

    private class ClipboardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String data = "";
            for (int i = 0; i < numOfRows; i++)
                data += headersTable.getValueAt(i, 0) + ": " + headersTable.getValueAt(i, 1) + "\n";
            StringSelection stringSelection = new StringSelection(data);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }
    public void reset(){
        DefaultTableModel dtm = (DefaultTableModel) headersTable.getModel();
        for(int i=numOfRows-1;i>=0;i--)
            dtm.removeRow(i);
        numOfRows = 0;
    }
}
