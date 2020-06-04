package com.company.Logic;

import java.awt.*;
import java.util.HashMap;

/**
 * Represents an enum for request methods
 */
public enum RequestMethod {
    GET, POST, PUT, DELETE, UNKNOWN;

    //Method names hash map

    public static HashMap<String, RequestMethod> methodNames;
    //method colors hash map
    public static HashMap<String, Color> colors;

    static {
        methodNames = new HashMap<>();
        for (RequestMethod requestMethod : RequestMethod.values())
            if (requestMethod != UNKNOWN)
                methodNames.put(requestMethod.toString(), requestMethod);
        colors = new HashMap<>();
        colors.put("GET", Color.BLUE);
        colors.put("POST", Color.GREEN);
        colors.put("PUT", Color.ORANGE);
        colors.put("DELETE", Color.RED);
    }

    /**
     * Method getter
     *
     * @param method Method as an string
     * @return The RequestMethod
     */
    public static RequestMethod getMethod(String method) {
        RequestMethod requestMethod = methodNames.get(method);
        if (requestMethod == null)
            return UNKNOWN;
        return requestMethod;
    }

    /**
     * checks that an string is a method or not
     *
     * @param method The string
     * @return true if yes and false if not
     */
    public static boolean isMethod(String method) {
        return methodNames.containsKey(method);
    }
}
