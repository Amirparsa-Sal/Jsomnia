package com.company.Logic;

import java.util.HashMap;
import java.util.List;

public class Response {

    private HashMap<String, List<String>> headers;
    private String body;
    private int code;
    private float time;
    private int size;
    private boolean isVisible;
    private RequestMethod requestMethod;

    public Response(){
        headers = new HashMap<>();
        String body = "";
        isVisible = false;
    }

    public HashMap<String, List<String>> getHeaders() {
        return headers;
    }

    public float getTime() {
        return time;
    }

    public int getSize() {
        return size;
    }

    public int getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setHeaders(HashMap<String, List<String>> headers) {
        this.headers = headers;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTime(float time) {
        this.time = time;
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
        if(isVisible) {
            str += "Headers:\n";
            for (String key : headers.keySet()) {
                str += key + " : ";
                for(String value : headers.get(key))
                    str += value;
                str += '\n';
            }
            str += '\n';
        }
        str += body + "\n\n" ;
        str += "Time : " + time + "\n";
        str += "Size : " + size;
        return str;
    }
}
