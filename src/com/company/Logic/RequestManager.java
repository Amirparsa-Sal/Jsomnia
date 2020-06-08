package com.company.Logic;

import com.company.Console.ConsoleUI;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class RequestManager {

    private static final String PATH = "C:\\Users\\Adak\\Desktop\\CE AUT\\Term 2\\Advanced Programming\\HW\\Projects\\MidTerm\\Data\\";
    public static RequestManager instance = null;
    private final String BOUNDARY = "--JSOMNIA--BOUNDARY";
    private boolean saveRequest = false;

    public static RequestManager getInstance() {
        if (instance == null)
            instance = new RequestManager();
        return instance;
    }

    public boolean isSaveRequest() {
        return saveRequest;
    }

    public void setSaveRequest(boolean saveRequest) {
        this.saveRequest = saveRequest;
    }

    public Response sendRequest(Request request) {
        if (request.getName() == null)
            request.setName("Request " + (getNumberOfRequests() + 1));
        if (request.getRequestMethod() == RequestMethod.UNKNOWN)
            request.setRequestMethod(RequestMethod.GET);
        HttpURLConnection connection = null;
        Response response = new Response();
        if (request.getUrl() == null)
            ConsoleUI.getInstance().raiseError("You have not entered the request url!");
        try {
            //sending request
            URL url = new URL(request.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request.getRequestMethod().toString());
            connection.setInstanceFollowRedirects(request.getFollowRedirection());
            if (request.getContentType().equals("multipart/form-data"))
                connection.setRequestProperty("Content-Type", "multipart/form-data" + "; boundary=" + BOUNDARY);
            else
                connection.setRequestProperty("Content-Type", request.getContentType());
            for (RequestHeader requestHeader : request.getHeaders())
                connection.setRequestProperty(requestHeader.getKey(), requestHeader.getValue());
            if (request.getRequestMethod() != RequestMethod.GET) {
                connection.setDoOutput(true);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
                setOutputStream(request, bufferedOutputStream);
            }
            //working with response
            response.setVisible(request.getResponseVisibility());
            long startTime = System.currentTimeMillis();
            response.setHeaders(new HashMap<>(connection.getHeaderFields()));
            long finishTime = System.currentTimeMillis();
            response.setTime(finishTime - startTime);
            response.setCode(connection.getResponseCode());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            if (request.isOutput()) {
                saveOutput(bufferedInputStream, request);
                response.setBody("Response body saved in " + request.getOutputName());
            } else {
                byte[] bytes = new byte[1024];
                int n = 0, sum = 0;
                String responseBody = "";
                while ((n = bufferedInputStream.read(bytes)) != -1) {
                    sum += n;
                    responseBody += new String(bytes, 0, n);
                }
                if (responseBody.equals(""))
                    response.setBody("No body returned!");
                else
                    response.setBody("Body:\n" + responseBody);
                response.setSize(sum);
            }
        } catch (FileNotFoundException e) {
            try {
                if (connection.getResponseCode() / 100 == 4)
                    response.setBody("Cannot " + request.getRequestMethod().toString() + " this page!");
            } catch (IOException ex) {
                ConsoleUI.getInstance().raiseError("Cannot " + request.getRequestMethod().toString() + " this page!");
            }
        } catch (UnknownHostException e) {
            response.setBody("Cannot resolve host name! check the website address or your internet connection and try again!");
        } catch (IOException e) {
            try {
                if (connection.getResponseCode() / 100 == 4)
                    response.setBody("Cannot " + request.getRequestMethod().toString() + " this page!");
            } catch (IOException ex) {
                response.setBody("Cannot " + request.getRequestMethod().toString() + " this page!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isSaveRequest())
            saveRequestInList(request);
        if (request.getFollowRedirection() && response.getCode() / 100 == 3) {
            setSaveRequest(false);
            String url = connection.getHeaderField("Location");
            if (url != null) {
                request.setUrl(url);
                response = sendRequest(request);
            }
        }
        return response;
    }

    private void setOutputStream(Request request, BufferedOutputStream bufferedOutputStream) throws IOException {
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
        } else if (request.getBodyType() == Request.BodyType.BINARY_FILE) {
            File file = new File(request.getData());
            BufferedInputStream newBuffer = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[(int) file.length()]; //needs to be completed
            int n = 0;
            while ((n = newBuffer.read(bytes)) > 0)
                bufferedOutputStream.write(bytes, 0, n);
            newBuffer.close();
        } else if (request.getBodyType() == Request.BodyType.URL_ENCODED) {
            bufferedOutputStream.write(request.getData().getBytes());
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    private void saveOutput(BufferedInputStream bufferedInputStream, Request request) {
        BufferedOutputStream bufferedOutputStream = null;
        File file = new File(request.getOutputName());
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
        } catch (IOException e) {
            ConsoleUI.getInstance().raiseError("Cannot create the file :(");
        }
    }

    public int getNumberOfRequests() {
        int n = 0;
        for (int i = 1; ; i++) {
            if (Files.exists(Paths.get(PATH + "request" + i + ".dat")))
                n++;
            else
                break;
        }
        return n;
    }

    public void saveRequestInList(Request request) {
        File file = null;
        if (!Files.exists(Paths.get(PATH + "request" + (getNumberOfRequests() + 1) + ".dat"))) {
            file = new File(PATH + "request" + (getNumberOfRequests() + 1) + ".dat");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Request loadRequestFromList(int requestNumber) {
        if (!Files.exists(Paths.get(PATH + "request" + requestNumber + ".dat")))
            return null;
        File file = new File(PATH + "request" + requestNumber + ".dat");
        FileInputStream fileInputStream = null;
        Request request = null;
        try {
            fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            request = ((Request) objectInputStream.readObject());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return request;
    }

    public void showListOfRequests() {
        for (int i = 1; i <= getNumberOfRequests(); i++)
            System.out.println(i + ") " + loadRequestFromList(i));
    }

    public boolean deleteRequestFromList(int requestNumber) {
        File file = new File(PATH + "request" + requestNumber + ".dat");
        return file.delete();
    }

    public void reArrangeList(int range) {
        for (int i = 1; i <= range; i++) {
            if (!Files.exists(Paths.get(PATH + "request" + i + ".dat"))) {
                //finding nextFile
                int nextRequestNumber = i + 1;
                while (!Files.exists(Paths.get(PATH + "request" + nextRequestNumber + ".dat")) && nextRequestNumber < range)
                    nextRequestNumber++;
                //rename file
                File nextRequestFile = new File(PATH + "request" + nextRequestNumber + ".dat");
                nextRequestFile.renameTo(new File(PATH + "request" + i + ".dat"));
            }
        }
    }
}
// files: image/png