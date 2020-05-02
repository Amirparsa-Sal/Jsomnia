package com.company;

import java.util.HashMap;

public enum RequestMethod {
    GET, HEAD, POST, PUT, DELETE, UNKNOWN;

    static HashMap<String,RequestMethod> methodNames;

    static {
        methodNames = new HashMap<>();
        for(RequestMethod requestMethod : RequestMethod.values())
            if(requestMethod!=UNKNOWN)
                methodNames.put(requestMethod.toString(),requestMethod);
    }

    public static RequestMethod getMethod(String method){
        RequestMethod requestMethod = methodNames.get(method);
        if(requestMethod==null)
            return UNKNOWN;
        return requestMethod;
    }

    public static boolean isMethod(String method){
        return methodNames.containsKey(method);
    }
}
