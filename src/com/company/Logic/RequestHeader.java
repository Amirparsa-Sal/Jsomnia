package com.company.Logic;

import java.io.Serializable;

public class RequestHeader implements Serializable {

    public static final long serialVersionUID = 1L;
    private String key;
    private String value;

    public RequestHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }
}
