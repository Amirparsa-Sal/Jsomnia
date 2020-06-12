package com.company.Logic;

import com.company.Console.Command;
import com.company.Console.Commands;
import com.company.Console.ConsoleUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a class for parsing data.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class Parser {

    //url regex
    public static final String URL_REGEX = "((http://|https://))?((W|w){3}.)?[a-zA-Z0-9.\\-_]+[.][a-zA-Z]+(/[a-zA-Z0-9.\\-_]+)*\\??(?:&?[^=&]*=[^=&]*)*";
    //header regex
    public static final String HEADER_REGEX = "[ ]*([a-zA-Z0-9-\\\\:.\\[\\]]+:[a-zA-Z0-9-\\\\:.\\[\\]]+[ ]*;[ ]*)*([a-zA-Z0-9-\\\\:.\\[\\]]+:[a-zA-Z0-9-\\\\:.\\[\\]]+)[ ]*";
    //form data regex
    public static final String FORM_DATA_REGEX = "[ ]*([a-zA-Z0-9-\\\\:.\\[\\]]+=[a-zA-Z0-9-\\\\:.\\[\\]]+[ ]*&[ ]*)*([a-zA-Z0-9-\\\\:.\\[\\]]+=[a-zA-Z0-9-\\\\:.\\[\\]]+)[ ]*";
    //json regex

    /**
     * Gets the user command and converts it to a request.
     *
     * @param syntax User command
     * @return Corresponding request
     */
    public static Request commandToRequest(String[] syntax) {
        boolean flag = true;
        Request request = new Request();
        ArrayList<String> commandList = strArrToArrayList(syntax);
        commandList.add(null);
        for (int i = 0; i < commandList.size() - 1; i++) {
            String arg = commandList.get(i);
            //finding next arg
            String nextArg = "";
            int n = 0;
            for (int j = i + 1; j < commandList.size(); j++) {
                if (commandList.get(j) != null && !Commands.getInstance().isCommand(commandList.get(j))) {
                    nextArg += commandList.get(j) + " ";
                    n++;
                } else
                    break;
            }
            i += n;
            if (!nextArg.equals(""))
                nextArg = nextArg.substring(0, nextArg.length() - 1);
            //processing command
            Command command = Commands.getInstance().findCommandBySign(arg);
            if (command != null) {
                if (command.getType() == Command.CommandType.MULTI_TYPE) {
                    if (nextArg.equals("") || Commands.getInstance().isCommand(nextArg))
                        command.execute(null, request);
                    else {
                        command.execute(nextArg, request);
                    }
                } else if (command.getType() == Command.CommandType.ARGUMENTAL) {
                    if (nextArg.equals("") || Commands.getInstance().isCommand(nextArg))
                        ConsoleUI.getInstance().raiseError("Invalid syntax: " + arg + " must take parameters");
                    else {
                        command.execute(nextArg, request);
                    }
                } else {
                    command.execute(null, request);
                    i -= n;
                }
            } else if (isMatch(arg, URL_REGEX))
                request.setUrl(commandList.get(i));
            else
                ConsoleUI.getInstance().raiseError("Invalid syntax: " + arg + " is not defined");
        }
        return request;
    }

    /**
     * Splits the headers and makes an array list of headers.
     *
     * @param headers String of the headers
     * @return Array list of request headers
     */
    public static ArrayList<RequestHeader> splitHeaders(String headers) {
        ArrayList<RequestHeader> list = new ArrayList<>();
        if (headers.equals(""))
            return list;
        String[] headersList = headers.split(";");
        for (String str : headersList) {
            int index = str.indexOf(':');
            list.add(new RequestHeader(str.substring(0, index), str.substring(index + 1)));
        }
        return list;
    }

    /**
     * Checks if an string is match with a pattern.
     *
     * @param str   The string
     * @param regex The pattern
     * @return True if yes and false if not
     */
    public static boolean isMatch(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * Converts an string array to array list of strings.
     *
     * @param strArr The string array
     * @return an array list of strings
     */
    public static ArrayList<String> strArrToArrayList(String[] strArr) {
        ArrayList<String> strings = new ArrayList<>();
        Collections.addAll(strings, strArr);
        return strings;
    }

    /**
     * Converts form data hash map to string
     * @param map form data hash map
     * @return form data string
     */
    public static String splitFormDataMap(LinkedHashMap <String,String> map){
        String data = "";
        if(map.size()==0)
            return data;
        for(String key : map.keySet())
            data += key + "=" + map.get(key) + "&";
        return data.substring(0,data.length()-1);
    }

    /**
     * Converts headers array list to linked hash map
     * @param list headers list
     * @return headers linked hash map
     */
    public static LinkedHashMap<String,String> headersListToMap(ArrayList<RequestHeader> list){
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        for(RequestHeader requestHeader : list)
            map.put(requestHeader.getKey(),requestHeader.getValue());
        return map;
    }

    /**
     * Converts form data string to linked hash map
     * @param data form data string
     * @return form data linked hash map
     */
    public static LinkedHashMap<String,String> formDataToMap(String data){
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        if(data=="")
            return map;
        String[] pairs = data.split("&");
        for(String pair : pairs)
            map.put(pair.split("=")[0],pair.split("=")[1]);
        return map;
    }
}