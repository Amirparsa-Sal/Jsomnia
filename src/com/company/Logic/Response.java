package com.company.Logic;

import java.util.HashMap;
import java.util.List;

public class Response {

    private HashMap<String, List<String>> headers;
    private String body;
    private int code;
    private long time;
    private double size;
    private boolean isVisible;
    private RequestMethod requestMethod;
    private String sizeUnit;

    public Response() {
        headers = new HashMap<>();
        String body = "";
        isVisible = false;
        sizeUnit = "B";
    }

    public HashMap<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, List<String>> headers) {
        this.headers = headers;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        if (size > 1000) {
            size /= 1000;
            size = Math.floor(size) + (((int) (Math.floor(size * 10))) % 10) * 0.1;
            sizeUnit = "KB";
        } else
            sizeUnit = "B";
        this.size = size;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

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
}