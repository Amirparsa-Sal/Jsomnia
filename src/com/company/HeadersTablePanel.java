package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeadersTablePanel extends JPanel{

    private Color bgColor;
    private JTable headersTable;
    private JScrollPane scrollPane;
    private JButton saveToClipButton;
    private int numOfRows;

    public HeadersTablePanel(Color bgColor, Dimension size){
        //init
        numOfRows = 0;
        this.setLayout(null);
        this.bgColor = bgColor;
        this.setBackground(bgColor);
        this.setBounds(0,0,size.width,size.height);
        //table
        headersTable = new JTable(new DefaultTableModel(null, new Object[]{"Name","Value"})){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.addRow("Title","Jsomnia");
        this.addRow("Time","04:25");
        this.addRow("Mode","Plain Text");
        //scroll
        scrollPane = new JScrollPane(headersTable);
        scrollPane.setBackground(bgColor);
        scrollPane.getViewport().setBackground(bgColor);
        scrollPane.getViewport().setFocusable(true);
        //button
        saveToClipButton = new JButton("Save to clipboard");
        saveToClipButton.setOpaque(false);
        saveToClipButton.setBackground(bgColor);
        saveToClipButton.setForeground(Color.WHITE);
        saveToClipButton.setBorder(new LineBorder(Color.BLACK,1));
        saveToClipButton.addActionListener(new ClipboardListener());
        this.reArrange();
        this.add(scrollPane);
        this.add(saveToClipButton);
    }

    public void reArrange(){
        int width = getWidth(), height = getHeight();
        saveToClipButton.setBounds(width/4,height-110,width/2,60);
        scrollPane.setBounds(10,10,width-40,height-120);
    }

    public void addRow(String name, String value){
        DefaultTableModel dtm = (DefaultTableModel)headersTable.getModel();
        dtm.addRow(new Object[]{name,value});
        numOfRows++;
    }

    private class ClipboardListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String data = "";
            for(int i=0;i<numOfRows;i++) {
                data += headersTable.getValueAt(i,0);
                data += ": ";
                data += headersTable.getValueAt(i,1);
            }
            StringSelection stringSelection = new StringSelection(data);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection,null);
        }
    }
}
