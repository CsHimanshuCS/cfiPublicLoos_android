package com.hv.cfi.network.dto;


import com.hv.cfi.network.Interface.BaseModel;

import org.json.JSONObject;

/**
 * Created by Himanshu Kumar singh
 */
public class Request {

    private String requestURL;
    private String requestType; //get or post
    private Object object;
    private String strRequest;
    private JSONObject jsonRequest;
    private boolean isFromJson = false;
    private int requestID = -1;
    private BaseModel baseModel = null;

    public Request() {
    }

    public Request(int requestID) {
        setRequestID(requestID);
    }

    public Request(int requestID, String strRequest) {
        setRequestID(requestID);
        setStrRequest(strRequest);
    }

    public Request(int requestID, JSONObject jsonRequest) {
        setRequestID(requestID);
        setJsonRequest(jsonRequest);
    }

    public String getStrRequest() {
        return strRequest;
    }
    /*
    * False = String Request
    * true = Json Request
    */

    public void setStrRequest(String strRequest) {
        this.strRequest = strRequest;
        isFromJson = false;
    }

    public JSONObject getJsonRequest() {
        return jsonRequest;
    }

    public void setJsonRequest(JSONObject jsonRequest) {
        this.jsonRequest = jsonRequest;
        isFromJson = true;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public boolean isfromJson() {
        return isFromJson;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {

        switch (requestType) {
            case GET:
                this.requestType = "get";
                break;
            case POST:
                this.requestType = "post";
                break;
            default:
                this.requestType = "unknown";
        }
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public BaseModel getBaseModel() {
        return baseModel;
    }

    public void setBaseModel(BaseModel baseModel) {
        this.baseModel = baseModel;
    }

    public enum RequestType {GET, POST}
}
