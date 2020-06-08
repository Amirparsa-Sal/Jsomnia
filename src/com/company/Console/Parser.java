package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

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
            } else if (isMatch(arg, "((http://|https://))?((W|w){3}.)?[a-zA-Z0-9.\\-_]+[.][a-zA-Z]+(/[a-zA-Z0-9.\\-_]+)*\\??(?:&?[^=&]*=[^=&]*)*"))
                request.setUrl(commandList.get(i));
            else
                ConsoleUI.getInstance().raiseError("Invalid syntax: " + arg + " is not defined");
        }
        return request;
    }

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

    public static boolean isMatch(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static ArrayList<String> strArrToArrayList(String[] strArr) {
        ArrayList<String> strings = new ArrayList<>();
        Collections.addAll(strings, strArr);
        return strings;
    }
}