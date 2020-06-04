package com.company.GUI;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a class for viewing web pages.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class WebPreviewPanel extends JPanel {

    //Background color
    private Color bgColor;
    //    private JEditorPane webViewer;
//    private JScrollPane scrollPane;
    //The Browser
    private BrowserView view;

    //    public WebPreviewPanel(Color bgColor, Dimension size) throws IOException {
//        //init
//        this.bgColor = bgColor;
//        this.setBackground(bgColor);
//        this.setLayout(null);
//        this.setBounds(0,0,size.width,size.height);
//        //web viewer
//        webViewer = new JEditorPane();
//        webViewer.setEditable(false);
//        //scroll
//        scrollPane = new JScrollPane(webViewer);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        //add
//        this.add(scrollPane);
//    }

    /**
     * Constructor with 2 parameters
     *
     * @param bgColor Background of the panel
     * @param size    Size of the panel
     */
    public WebPreviewPanel(Color bgColor, Dimension size) {
        this.bgColor = bgColor;
        this.setBackground(bgColor);
        this.setLayout(null);
        this.setBounds(0, 0, size.width, size.height);
        System.setProperty("jxbrowser.license.key", "1BNDHFSC1FVG3WJLZCC7WJXJS7BL5KN1MQVUQOF31KWLVR1JV73ZD9AQ1AK5WUPL8E18Q1");
        EngineOptions options =
                EngineOptions.newBuilder(RenderingMode.HARDWARE_ACCELERATED).licenseKey("1BNDHFSC1FVG3WJLZCC7WJXJS7BL5KN1MQVUQOF31KWLVR1JV73ZD9AQ1AK5WUPL8E18Q1").build();
        Engine engine = Engine.newInstance(options);
        Browser browser = engine.newBrowser();
        view = BrowserView.newInstance(browser);
        view.setFocusable(false);
        this.setFocusable(false);
        this.add(view);
        browser.navigation().loadUrl("https://www.tarafdari.com");
    }

    /**
     * Rearranges the panel
     */
    public void reArrange() {
        int width = getWidth(), height = getHeight();
        view.setBounds(10, 10, width - 20, height - 20);
    }
}
//}
