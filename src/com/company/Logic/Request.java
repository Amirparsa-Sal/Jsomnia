package com.company.Logic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a class for requests.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class Request {

    public enum BodyType{
        FORM_DATA, JSON, BINARY_FILE, UNKNOWN;
    }
    //Name of the request
    private String name;
    //URL of the request
    private String url;
    //Request method
    private RequestMethod requestMethod;
    //List of headers
    private ArrayList<RequestHeader> headers;
    //Response visibility
    private boolean responseVisibility;
    //Follow redirection
    private boolean followRedirection;
    //body
    private String data;
    //body type
    private BodyType bodyType;

    /**
     * Constructor with no parameter
     */
    public Request() {
        requestMethod = RequestMethod.GET;
        bodyType = BodyType.UNKNOWN;
        headers = new ArrayList<>();
    }

    /**
     * Constructor with 2 parameters
     *
     * @param name          Name of the request
     * @param requestMethod Method of the request
     */
    public Request(String name, RequestMethod requestMethod) {
        this.name = name;
        this.requestMethod = requestMethod;
        bodyType = BodyType.UNKNOWN;
        headers = new ArrayList<>();
    }

    /**
     * Name getter
     *
     * @return Name of the request
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter
     *
     * @param name Name of the Request
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * URL stter
     *
     * @param url URL of the request
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Request method setter
     *
     * @param requestMethod Method of the request
     */
    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    /**
     * Headers setter
     *
     * @param headers list of the headers
     */
    public void setHeaders(ArrayList<RequestHeader> headers) {
        this.headers = headers;
    }

    /**
     * Response visibility setter
     *
     * @param responseVisibility Response visibility
     */
    public void setResponseVisibility(boolean responseVisibility) {
        this.responseVisibility = responseVisibility;
    }

    /**
     * Follow redirection setter
     *
     * @param followRedirection Follow redirection
     */
    public void setFollowRedirection(boolean followRedirection) {
        this.followRedirection = followRedirection;
    }

    /**
     * URL getter
     *
     * @return URL of the request
     */
    public String getUrl() {
        return url;
    }

    /**
     * Request method getter
     *
     * @return Method of the request
     */
    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    /**
     * Headers getter
     *
     * @return list of headers
     */
    public ArrayList<RequestHeader> getHeaders() {
        return headers;
    }

    /**
     * checks if the request response is visible
     *
     * @return true if yes and false if not
     */
    public boolean getResponseVisibility() {
        return responseVisibility;
    }

    /**
     * checks if the request follow redirection
     *
     * @return true if yes and false if not
     */
    public boolean getFollowRedirection() {
        return followRedirection;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    /**
     * Converts the request to a string
     *
     * @return Request string
     */


    @Override
    public String toString() {
        String str = "name: " + name + "\n" +
                "url: " + url + "\n" +
                "Method: " + requestMethod.toString() + "\n" +
                "response visibility: " + responseVisibility + "\n" +
                "follow redirect: " + followRedirection + "\n" +
                "data: " + data + "\n" +
                "body type: " + bodyType.toString() + "\n";
        str += "Headers: ";
        for (RequestHeader requestHeader : headers)
            str += requestHeader.toString() + " ";
        return str;
    }

    public String getContentType(){
        if(bodyType == BodyType.JSON)
            return "application/json";
        else if(bodyType == BodyType.BINARY_FILE)
            return "application/octet-stream";
        return "multipart/form-data";
    }

    public HashMap<String,String> getFormDataPairs(){
        if(bodyType == BodyType.FORM_DATA) {
            HashMap<String, String> dataHashMap = new HashMap<>();
            String[] pairs = data.substring(0, data.length() - 1).split("&");
            for (String pair : pairs) {
                String[] splitedPairs = pair.split("=");
                dataHashMap.put(splitedPairs[0], splitedPairs[1]);
            }
            return dataHashMap;
        }
        return null;
    }
}
