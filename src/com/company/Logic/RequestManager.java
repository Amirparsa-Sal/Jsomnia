package com.company.Logic;

import com.company.Console.ConsoleUI;
import sun.misc.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

public class RequestManager {

    private final String BOUNDARY = "--JSOMNIA--BOUNDARY";
    public static RequestManager instance = null;

    private boolean saveRequest = false;
    private boolean output = false;
    private String outputName;

    public static RequestManager getInstance() {
        if (instance == null)
            instance = new RequestManager();
        return instance;
    }

    private void resetManager() {
        saveRequest = false;
        output = false;
        outputName = null;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    public boolean isSaveRequest() {
        return saveRequest;
    }

    public void setSaveRequest(boolean saveRequest) {
        this.saveRequest = saveRequest;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public void sendRequest(Request request) {
        HttpURLConnection connection = null;
        if (request.getUrl() == null)
            ConsoleUI.getInstance().raiseError("You have not entered the request url!");
        try {
            URL url = new URL(request.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request.getRequestMethod().toString());
            connection.setInstanceFollowRedirects(request.getFollowRedirection());
            System.out.println(request.getContentType());
            connection.setRequestProperty("Content-Type", request.getContentType() + "; boundary=" + BOUNDARY);
            for (RequestHeader requestHeader : request.getHeaders())
                connection.setRequestProperty(requestHeader.getKey(), requestHeader.getValue());
            if (request.getRequestMethod() != RequestMethod.GET) {
                connection.setDoOutput(true);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
                setOutputStream(request, bufferedOutputStream);
            }
            if (request.getResponseVisibility())
                showResponseHeaders(connection);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());

            if(output) {
                saveOutput(bufferedInputStream);
                System.out.println("Response body saved in " + outputName);
            }
            else{
                byte[] bytes = new byte[1024];
                int n = 0;
                String response = "";
                while ((n = bufferedInputStream.read(bytes)) != -1) {
                    response += new String(bytes, 0, n);
                }
                if (response.equals(""))
                    System.out.println("No body returned!");
                else
                    System.out.println("Body:\n" + response);
            }
        } catch (FileNotFoundException e) {
            try {
                if (connection.getResponseCode() == 404)
                    ConsoleUI.getInstance().raiseError("Cannot " + request.getRequestMethod().toString() + " this page(404 Not Found)!");
                else
                    showResponseHeaders(connection);
            } catch (IOException ex) {
                ConsoleUI.getInstance().raiseError("Cannot " + request.getRequestMethod().toString() + " this page!");
            }
        } catch (UnknownHostException e) {
            ConsoleUI.getInstance().raiseError("Cannot resolve host name! check the website address or your internet connection and try again!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOutputStream(Request request, BufferedOutputStream bufferedOutputStream) throws IOException {
        if (request.getData() == null)
            return;
        if (request.getBodyType() == Request.BodyType.JSON)
            bufferedOutputStream.write(request.getData().getBytes());
        else if (request.getBodyType() == Request.BodyType.FORM_DATA) {
            for (String key : request.getFormDataPairs().keySet()) {
                bufferedOutputStream.write(("--" + BOUNDARY + "\r\n").getBytes());
                if (key.contains("file")) {
                    bufferedOutputStream.write(("Content-Disposition: form-data; filename=\"" + new File(request.getFormDataPairs().get(key)).toString() + "\"\r\nContent-type: Auto\r\n\r\n").getBytes());
                    File file = new File(request.getFormDataPairs().get(key));
                    BufferedInputStream newBuffer = new BufferedInputStream(new FileInputStream(file));
                    byte[] bytes = new byte[(int) file.length()]; //needs to be completed
                    int n = 0;
                    while ((n = newBuffer.read(bytes)) > 0)
                        bufferedOutputStream.write(bytes, 0, n);
                    newBuffer.close();
                    bufferedOutputStream.write(("\r\n").getBytes());
                } else {
                    bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                    bufferedOutputStream.write((request.getFormDataPairs().get(key) + "\r\n").getBytes());
                }
            }
            bufferedOutputStream.write(("--" + BOUNDARY + "--\r\n").getBytes());
        } else if (request.getBodyType() == Request.BodyType.BINARY_FILE){
            File file = new File(request.getData());
            BufferedInputStream newBuffer = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[(int) file.length()]; //needs to be completed
            int n = 0;
            while ((n = newBuffer.read(bytes)) > 0)
                bufferedOutputStream.write(bytes, 0, n);
            newBuffer.close();
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    private void showResponseHeaders(HttpURLConnection connection) {
        HashMap<String, List<String>> headers = new HashMap<>(connection.getHeaderFields());
        for (String key : headers.keySet()) {
            System.out.print(key + ": ");
            for (String value : headers.get(key))
                System.out.print(value + " ");
            System.out.println();
        }
        System.out.println();
    }

    private void saveOutput(BufferedInputStream bufferedInputStream){
        BufferedOutputStream bufferedOutputStream = null;
        File file = new File(outputName);
        try {
            file.createNewFile();
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = new byte[2048];
            int n = 0;
            while ((n = bufferedInputStream.read(bytes)) > 0)
                bufferedOutputStream.write(bytes, 0, n);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (FileNotFoundException e) {
            ConsoleUI.getInstance().raiseError("Cannot create the file :(");
        }
        catch (IOException e) {
            ConsoleUI.getInstance().raiseError("Cannot create the file :(");
        }

    }
}


// files: image/png