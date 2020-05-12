package com.company;

import java.util.ArrayList;

/**
 * Represents a class for requests.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class Request {
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

    /**
     * Constructor with 2 parameters
     *
     * @param name          Name of the request
     * @param requestMethod Method of the request
     */
    public Request(String name, RequestMethod requestMethod) {
        this.name = name;
        this.requestMethod = requestMethod;
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
    public boolean isResponseVisibility() {
        return responseVisibility;
    }

    /**
     * checks if the request follow redirection
     *
     * @return true if yes and false if not
     */
    public boolean isFollowRedirection() {
        return followRedirection;
    }

    /**
     * Converts the request to a string
     *
     * @return Request string
     */
    @Override
    public String toString() {
        String str = "URL: " + url + " | " + "Method: " + requestMethod + " | " + "Headers: ";
        for (RequestHeader requestHeader : headers)
            str += requestHeader.toString();
        return str;
    }
}
