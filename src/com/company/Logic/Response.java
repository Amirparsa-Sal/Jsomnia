package com.company.Logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a class for http response.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class Response implements Serializable {

    public static final long serialVersionUID = 1L;
    //response headers
    private HashMap<String, List<String>> headers;
    //response body
    private String body;
    //response code
    private int code;
    //response time
    private long time;
    //response size
    private double size;
    //response visibility
    private boolean isVisible;
    //response size unit (B or KB)
    private String sizeUnit;
    //response message
    private String responseMessage;

    /**
     * Constructor with no parameter
     */
    public Response() {
        headers = new HashMap<>();
        String body = "";
        isVisible = false;
        sizeUnit = "B";
    }

    /**
     * Gets the response headers
     *
     * @return hash map of the headers
     */
    public HashMap<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * Sets the response headers
     *
     * @param headers hash map of the headers
     */
    public void setHeaders(HashMap<String, List<String>> headers) {
        this.headers = headers;
    }

    /**
     * Gets the response time
     *
     * @return response time
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets the response time
     *
     * @param time response time
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Gets the response size
     *
     * @return response size
     */
    public double getSize() {
        return size;
    }

    /**
     * Sets the response size
     *
     * @param size response size
     */
    public void setSize(double size) {
        if (size > 1000) {
            size /= 1000;
            size = Math.floor(size) + (((int) (Math.floor(size * 10))) % 10) * 0.1;
            sizeUnit = "KB";
        } else
            sizeUnit = "B";
        this.size = size;
    }

    /**
     * Gets the response code
     *
     * @return response code
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the response code
     *
     * @param code response code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets the response body
     *
     * @return response body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the response body
     *
     * @param body response body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Checks if response is visible of not
     *
     * @return true if yes and false if not
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Sets the response visibility
     *
     * @param visible response visibility
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Makes an string of response data
     *
     * @return an string of response data
     */
    @Override
    public String toString() {
        String str = "";
        if (isVisible) {
            if (headers.size() != 0) {
                str += "Headers:\n";
                for (String key : headers.keySet()) {
                    str += key + " : ";
                    for (String value : headers.get(key))
                        str += value;
                    str += '\n';
                }
            } else
                str += "No header returned!\n";
            str += '\n';
        }
        str += body;
        str += "\n\n";
        str += "Time : " + time + " ms\n";
        str += "Size : " + size + " " + sizeUnit;
        return str;
    }

    /**
     * Gets response content type
     * @return response content type
     */
    public String getContentType(){
        ArrayList<String> list = new ArrayList<>(getHeaders().get("Content-Type"));
        return list.get(0).split(";")[0];
    }

    /**
     * gets response message
     * @return response message
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * Sets response message
     * @param responseMessage response message
     */
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    /**
     * gets response size unit
     * @return response size unit
     */
    public String getSizeUnit() {
        return sizeUnit;
    }
}