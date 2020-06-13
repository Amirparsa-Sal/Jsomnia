package com.company.Logic;

import com.company.Console.ConsoleUI;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Represents a class for managing the requests.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class RequestManager {

    //Output path
    private static final String PATH = ".\\Data\\";
    //The only instance of the class
    public static RequestManager instance = null;
    //Jsomnia boundary
    public final String BOUNDARY = "--JSOMNIA--BOUNDARY";
    //save request or not
    private boolean saveRequest = false;

    /**
     * Gets the only instance of the request manager.
     *
     * @return the only instance of the request manager
     */
    public static RequestManager getInstance() {
        if (instance == null)
            instance = new RequestManager();
        return instance;
    }

    /**
     * Checks if the manager should save the request.
     *
     * @return true if yes and false if not
     */
    public boolean isSaveRequest() {
        return saveRequest;
    }

    /**
     * Sets the save request property.
     *
     * @param saveRequest save request situation
     */
    public void setSaveRequest(boolean saveRequest) {
        this.saveRequest = saveRequest;
    }

    /**
     * Sends the request
     *
     * @param request The request
     * @return The response
     */
    public Response sendRequest(Request request) {
        if (request.getName() == null)
            request.setName("Request " + (getNumberOfRequests() + 1));
        if (request.getRequestMethod() == RequestMethod.UNKNOWN)
            request.setRequestMethod(RequestMethod.GET);
        HttpURLConnection connection = null;
        Response response = new Response();
        try {
            //sending request
            URL url = new URL(request.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request.getRequestMethod().toString());
            connection.setInstanceFollowRedirects(request.getFollowRedirection());
            if (request.getBodyType() == Request.BodyType.FORM_DATA)
                connection.setRequestProperty("Content-Type", "multipart/form-data" + "; boundary=" + RequestManager.getInstance().BOUNDARY);
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
            response.setResponseMessage(connection.getResponseMessage());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            String responseBody = "";
            byte[] bytes = new byte[1024];
            int n = 0, sum = 0;
            if (request.isOutput()) {
                saveOutput(bufferedInputStream, request);
                response.setBody("Response body saved in " + request.getOutputName());
                File file = new File(request.getOutputName());
                FileInputStream fileInputStream = new FileInputStream(file);
                while ((n = fileInputStream.read(bytes)) != -1) {
                    sum += n;
                    responseBody += new String(bytes, 0, n);
                }
                fileInputStream.close();
            } else {
                while ((n = bufferedInputStream.read(bytes)) != -1) {
                    sum += n;
                    responseBody += new String(bytes, 0, n);
                }
            }
            response.setSize(sum);
            if (responseBody.equals(""))
                response.setBody("No body returned!");
            else
                response.setBody("Body:\n" + responseBody);
        } catch (FileNotFoundException e) {
            try {
                if (connection.getResponseCode() / 100 == 4)
                    response.setBody("Cannot " + request.getRequestMethod().toString() + " this page!");
            } catch (IOException ex) {
                response.setBody("Cannot " + request.getRequestMethod().toString() + " this page!");
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
            String url = connection.getHeaderField("Location");
            if (url != null) {
                request.setUrl(url);
                response = sendRequest(request);
            }
        }
        setSaveRequest(false);

        request.setResponse(response);
        return response;
    }

    /**
     * Sets the request output stream
     *
     * @param request              The request
     * @param bufferedOutputStream Stream of the connection
     * @throws IOException if bad data is written in buffered output stream.
     */
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

    /**
     * Saves the output of the request
     *
     * @param bufferedInputStream input stream of the connection
     * @param request             The request
     */
    public void saveOutput(BufferedInputStream bufferedInputStream, Request request) {
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

    /**
     * Gets number of saved requests
     *
     * @return Number of saved requests
     */
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

    /**
     * Saves the request in list
     *
     * @param request The request
     */
    public void saveRequestInList(Request request) {
        File file = null;
        if (request.getSaveFileName() != null) {
            file = new File(PATH + request.getSaveFileName() + ".dat");
        } else {
            if (!Files.exists(Paths.get(PATH + "request" + (getNumberOfRequests() + 1) + ".dat"))) {
                file = new File(PATH + "request" + (getNumberOfRequests() + 1) + ".dat");
                request.setSaveFileName("request" + (getNumberOfRequests() + 1));
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    /**
     * Loads the request from the list
     *
     * @param requestNumber request number
     * @return The request
     */
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
            fileInputStream.close();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return request;
    }

    /**
     * Shows the list of the requests
     */
    public void showListOfRequests() {
        for (int i = 1; i <= getNumberOfRequests(); i++)
            System.out.println(i + ") " + loadRequestFromList(i));
    }

    /**
     * Deletes a request from the list
     *
     * @param requestNumber request number
     * @return true if deleting was successful and false if not
     */
    public boolean deleteRequestFromList(int requestNumber) {
        File file = new File(PATH + "request" + requestNumber + ".dat");
        return file.delete();
    }

    /**
     * Rearranges the request list
     *
     * @param range max range to rearrange
     * @return hash map of changes
     */
    public HashMap<String, String> reArrangeList(int range) {
        HashMap<String, String> changes = new HashMap<>();
        for (int i = 1; i <= range; i++) {
            if (!Files.exists(Paths.get(PATH + "request" + i + ".dat"))) {
                //finding nextFile
                int nextRequestNumber = i + 1;
                while (!Files.exists(Paths.get(PATH + "request" + nextRequestNumber + ".dat")) && nextRequestNumber < range)
                    nextRequestNumber++;
                //rename file
                Request request = loadRequestFromList(nextRequestNumber);
                if (request != null) {
                    File nextRequestFile = new File(PATH + "request" + nextRequestNumber + ".dat");
                    nextRequestFile.renameTo(new File(PATH + "request" + i + ".dat"));
                    request.setSaveFileName("request" + i);
                    saveRequestInList(request);
                    changes.put("request" + nextRequestNumber, "request" + i);
                }
            }
        }
        return changes;
    }
}