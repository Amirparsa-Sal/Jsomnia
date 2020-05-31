package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static Request commandToRequest(String syntax) {
        boolean flag = true;
        Request request = new Request();
        syntax = removeSpaces(syntax);
        String[] splitedCommands = syntax.split(" ");
        ArrayList<String> commandList = strArrToArrayList(splitedCommands);
        commandList.add(null);
        for (int i = 0; i < commandList.size() - 1; i++) {
            String arg = commandList.get(i);
            String nextArg = commandList.get(i+1);
            System.out.println(arg);
            Command command = Commands.getInstance().findCommandBySign(arg);
            if (command != null) {
                if(command.getType() == Command.CommandType.MULTI_TYPE){
                    if(nextArg==null || nextArg.charAt(0)=='-')
                        command.execute(null, request);
                    else{
                        command.execute(nextArg, request);
                        i++;
                    }
                }
                else if(command.getType()== Command.CommandType.ARGUMENTAL){
                    if(nextArg==null || nextArg.charAt(0)=='-')
                        ConsoleUI.getInstance().raiseError("Invalid syntax: " + arg + " must take parameters");
                    else{
                        command.execute(nextArg, request);
                        i++;
                    }
                }
                else
                    command.execute(null, request);
            }
            else if (isMatch(arg,"((ftp://|http://|https://))?((W|w){3}.)?[a-zA-Z0-9.]+[.][a-zA-Z]+(/[a-zA-Z0-9]+)*"))
                request.setUrl(commandList.get(i));
            else
                ConsoleUI.getInstance().raiseError("Invalid syntax: " + arg + " is not defined");
        }
        return request;
    }

    public static ArrayList<RequestHeader> splitHeaders(String headers) {
        ArrayList<RequestHeader> list = new ArrayList<>();
        String[] headersList = headers.substring(1, headers.length() - 1).split(";");
        for (String str : headersList) {
            int index = str.indexOf(':');
            list.add(new RequestHeader(str.substring(0, index), str.substring(index + 1, str.length())));
            System.out.println(str.substring(0, index) + " " + str.substring(index + 1, str.length()));
        }
        return list;
    }

    public static String removeSpaces(String command) {
        String newStr = "";
        boolean check = true;
        for (int i = 0; i < command.length(); i++) {
            char c = command.charAt(i);
            if (c == '"')
                check = !check;
            if (c != ' ')
                newStr += c;
            else if (check)
                newStr += c;
        }
        return newStr;
    }

    public static boolean isMatch(String str, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static ArrayList<String> strArrToArrayList(String[] strArr){
        ArrayList<String> strings = new ArrayList<>();
        Collections.addAll(strings, strArr);
        return strings;
    }

    public static String strArrToString(String[] strArr){
        String newStr = "";
        for(String str : strArr)
            newStr += str;
        return newStr;
    }
}
