package com.company.Logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a class for requests.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class Request implements Serializable {

    public static final long serialVersionUID = 1L;
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
    //has output
    private boolean output = false;
    //output name
    private String outputName;

    /**
     * Constructor with no parameter
     */
    public Request() {
        requestMethod = RequestMethod.UNKNOWN;
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
     * Checks if request has output
     *
     * @return true if yes and false if not
     */
    public boolean isOutput() {
        return output;
    }

    /**
     * Sets the existence of the output.
     *
     * @param output has output?
     */
    public void setOutput(boolean output) {
        this.output = output;
    }

    /**
     * Gets the output name.
     *
     * @return Output name
     */
    public String getOutputName() {
        return outputName;
    }

    /**
     * Sets the output name.
     *
     * @param outputName
     */
    public void setOutputName(String outputName) {
        this.outputName = outputName;
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
     * URL getter
     *
     * @return URL of the request
     */
    public String getUrl() {
        return url;
    }

    /**
     * URL stter
     *
     * @param url URL of the request
     */
    public void setUrl(String url) {
        if (url.substring(0, 7).equals("http://") || url.substring(0, 8).equals("https://"))
            this.url = url;
        else
            this.url = "http://" + url;
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
     * Request method setter
     *
     * @param requestMethod Method of the request
     */
    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
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
     * Headers setter
     *
     * @param headers list of the headers
     */
    public void setHeaders(ArrayList<RequestHeader> headers) {
        this.headers = headers;
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
     * Response visibility setter
     *
     * @param responseVisibility Response visibility
     */
    public void setResponseVisibility(boolean responseVisibility) {
        this.responseVisibility = responseVisibility;
    }

    /**
     * checks if the request follow redirection
     *
     * @return true if yes and false if not
     */
    public boolean getFollowRedirection() {
        return followRedirection;
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
     * Gets request body data
     *
     * @return Request body data
     */
    public String getData() {
        return data;
    }

    /**
     * Sets request body data
     *
     * @param data Requst body data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Gets request body type
     *
     * @return
     */
    public BodyType getBodyType() {
        return bodyType;
    }

    /**
     * Sets request body type
     *
     * @param bodyType
     */
    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    /**
     * Converts the request to a string
     *
     * @return Request string
     */


    /**
     * Makes an string from with request data.
     *
     * @return an string of request data.
     */
    @Override
    public String toString() {
        String str = "name: " + name + " | " +
                "url: " + url + " | " +
                "Method: " + requestMethod.toString() + " | " +
                "response visibility: " + responseVisibility + " | " +
                "follow redirect: " + followRedirection + " | " +
                "Has output: " + output + " | " +
                "Output name: " + outputName + " | " +
                "data: " + data + " | " +
                "body type: " + bodyType.toString() + " | ";
        str += "Headers: ";
        for (RequestHeader requestHeader : headers)
            str += requestHeader.toString() + " ";
        return str;
    }

    /**
     * Gets the request content type.
     *
     * @return Content type of the request.(example : multipart/form-data)
     */
    public String getContentType() {
        if (bodyType == BodyType.JSON)
            return "application/json";
        else if (bodyType == BodyType.BINARY_FILE)
            return "application/octet-stream";
        else if (bodyType == BodyType.URL_ENCODED)
            return "application/x-www-form-urlencoded";
        return "multipart/form-data";
    }

    /**
     * Makes an hash map from form data pairs.
     *
     * @return a hash map of form data pairs.
     */
    public HashMap<String, String> getFormDataPairs() {
        if (bodyType == BodyType.FORM_DATA) {
            HashMap<String, String> dataHashMap = new HashMap<>();
            String[] pairs = data.split("&");
            for (String pair : pairs) {
                String[] splitedPairs = pair.split("=");
                dataHashMap.put(splitedPairs[0], splitedPairs[1]);
            }
            return dataHashMap;
        }
        return null;
    }

    /**
     * Adds a header to the request.
     *
     * @param header The header
     */
    public void addHeader(RequestHeader header) {
        headers.add(header);
    }

    /**
     * Checks if the request is empty or not
     *
     * @return true if yes and false if not
     */
    public boolean isEmpty() {
        return name == null && url == null && requestMethod == RequestMethod.UNKNOWN && headers.size() == 0 && !responseVisibility && !followRedirection
                && data == null && bodyType == BodyType.UNKNOWN && !output && outputName == null;
    }

    /**
     * Represents a body type for the request
     */
    public enum BodyType {
        FORM_DATA, JSON, BINARY_FILE, URL_ENCODED, UNKNOWN
    }
}
