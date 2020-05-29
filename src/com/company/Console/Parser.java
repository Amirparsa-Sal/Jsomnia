package com.company.Console;

import com.company.Logic.Request;
import com.company.Logic.RequestHeader;
import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static Request commandToRequest(String command) {
        Request request = new Request();
        command = removeSpaces(command);
        String[] splitedCommands = command.split(" ");
        ArrayList<String> commandList = new ArrayList<>();
        for (String str : splitedCommands)
            commandList.add(str);
        commandList.add(null);
        for (int i = 0; i < commandList.size() - 1; i++) {
            String arg = commandList.get(i);
            System.out.println(arg);
            Pattern pattern = Pattern.compile("((ftp://|http://|https://))?((W|w){3}.)?[a-zA-Z0-9]+[.][a-zA-Z]+(/[a-zA-Z0-9]+)*");
            Matcher matcher = pattern.matcher(arg);
            Command commandType = Commands.getInstance().findCommandBySign(arg);
            if (commandType != null)
                commandType.execute(commandList.get(i + 1), request);
            else if (matcher.matches())
                request.setUrl(commandList.get(i));
        }
        return request;
    }

    public static ArrayList<RequestHeader> splitHeaders(String headers) {
        if (headers.charAt(0) != '"' || headers.charAt(headers.length() - 1) != '"')
            return null;
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
            else if (c == ' ' && check)
                newStr += c;
        }
        return newStr;
    }
}
