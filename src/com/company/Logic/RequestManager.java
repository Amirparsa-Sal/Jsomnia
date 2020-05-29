package com.company.Logic;

public class RequestManager {

    public static RequestManager instance = null;
    private boolean saveRequest = false;
    private boolean output = false;
    private String outputName;

    public static RequestManager getInstance(){
        if(instance==null)
            instance = new RequestManager();
        return instance;
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

    public void sendRequest(Request request){

    }
}
