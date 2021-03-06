package com.company.GUI;

import com.company.Logic.Request;
import com.company.Logic.RequestManager;
import com.company.Logic.Response;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Represents a class for viewing web pages.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class WebPreviewPanel extends JPanel {

    //Background color
    private Color bgColor;
    private JEditorPane webViewer;
    private JScrollPane scrollPane;
    private JLabel pic;

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
        pic = new JLabel();
        //web viewer
        webViewer = new JEditorPane();
        webViewer.setEditable(false);
        webViewer.setBackground(new Color(bgColor.getRed() - 10, bgColor.getGreen() - 10, bgColor.getBlue() - 10));
        //scroll
        scrollPane = new JScrollPane(webViewer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //add
        this.add(scrollPane);
    }

    /**
     * Rearranges the panel
     */
    public void reArrange() {
        int width = getWidth(), height = getHeight();
        webViewer.setBounds(10, 10, width - 20, height - 20);
        scrollPane.setBounds(10, 10, width - 20, height - 20);
        pic.setBounds(10, 10, width - 20, height - 20);

    }

    /**
     * Sets response preview
     *
     * @param response the response
     */
    public void setPreview(Response response) {
        if (!response.getBody().substring(0, 5).equals("Body:"))
            return;
        if (response.getContentType().equals("text/html")) {
            scrollPane.getViewport().remove(0);
            scrollPane.getViewport().add(webViewer);
            File file = new File("tmp.html");
            try {
                file.createNewFile();
                FileOutputStream fileoutputStream = new FileOutputStream(file);
                fileoutputStream.write(response.getBody().substring(5).getBytes());
                fileoutputStream.close();
                webViewer.setPage(file.toURI().toURL());
                file.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (response.getContentType().equals("text/plain")) {
            scrollPane.getViewport().remove(0);
            scrollPane.getViewport().add(webViewer);
            File file = new File("tmp.txt");
            try {
                file.createNewFile();
                FileOutputStream fileoutputStream = new FileOutputStream(file);
                fileoutputStream.write(response.getBody().substring(5).getBytes());
                fileoutputStream.close();
                webViewer.setPage(file.toURI().toURL());
                file.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (response.getContentType().split("/")[0].equals("image")) {
            scrollPane.getViewport().remove(0);
            scrollPane.getViewport().add(pic);
            pic.setHorizontalAlignment(SwingConstants.CENTER);
            try {
                Request request = GUIManager.getInstance().getLeft().getSelectedRequest();
                request.setOutputName("pic." + response.getContentType().split("/")[1]);
                request.setOutput(true);
                RequestManager.getInstance().sendRequest(request);
                File file = new File("pic." + response.getContentType().split("/")[1]);
                BufferedImage bufferedImage = ImageIO.read(file);
                pic.setBackground(Color.black);
                pic.setIcon(new ImageIcon(bufferedImage));
                pic.setAlignmentX(SwingConstants.CENTER);
                request.setOutput(false);
                file.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (response.getContentType().equals("application/json")) {
            scrollPane.getViewport().remove(0);
            scrollPane.getViewport().add(webViewer);
            String prettyJson = new JSONObject(response.getBody().substring(5)).toString(4);
            File file = new File("json.txt");
            try {
                file.createNewFile();
                FileOutputStream fileoutputStream = new FileOutputStream(file);
                fileoutputStream.write(prettyJson.getBytes());
                webViewer.setPage(file.toURI().toURL());
                fileoutputStream.close();
                file.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * resets the panel
     */
    public void reset() {
        webViewer = new JEditorPane();
        scrollPane.getViewport().remove(0);
        scrollPane.getViewport().add(webViewer);
        webViewer.setEditable(false);
    }
}
