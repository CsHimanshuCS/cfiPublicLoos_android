package com.hv.cfi.network.dto;


import com.hv.cfi.network.Interface.BaseModel;

/**
 * Created by Himanshu Kumar singh
 */

public class Response {
    private int responseID = -1;
    private int statusCode = -1;
    private boolean isError = false;
    private String Error = "";
    private String strResponse = "";
    private Object object;
    private BaseModel baseModel = null;

    public Response() {

    }

    public Response(int responseID) {
        setResponseID(responseID);
    }

    public Response(int responseID, String response) {
        setResponseID(responseID);
        setStrResponse(response);
    }

    public int getResponseID() {
        return responseID;
    }

    public void setResponseID(int responseID) {
        this.responseID = responseID;
    }

    public String getError() {
        return Error;
    }

    public String getStrResponse() {
        return strResponse;
    }

    public void setStrResponse(String strResponse) {
        this.strResponse = strResponse;
        isError = false;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(String error) {
        Error = error;
        if (error != null)
            isError = true;
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

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
