package com.company.Logic;

import java.io.Serializable;

/**
 * Represents a request header.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class RequestHeader implements Serializable {
    //serialization id
    public static final long serialVersionUID = 1L;
    //header key
    private String key;
    //header value
    private String value;

    /**
     * Constructor with 2 parameters
     *
     * @param key   header key
     * @param value header value
     */
    public RequestHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets key of the header
     *
     * @return key of the header
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets value of the header
     *
     * @return value of the header
     */
    public String getValue() {
        return value;
    }

    /**
     * Makes an string from header data
     *
     * @return an string of header data
     */
    @Override
    public String toString() {
        return key + ":" + value;
    }
}
