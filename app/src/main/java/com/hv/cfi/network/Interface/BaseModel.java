package com.hv.cfi.network.Interface;

import org.json.JSONObject;

/**
 * Created by Himanshu Kumar singh
 */

public interface BaseModel {

    /**
     * This method is useful to request with api.
     * It return the JSONObject which is forward to api for the POST request.
     *
     * @return JSONObject
     */

    public JSONObject toJSON();

    /**
     * This method is useful after getting the response from the server.
     * It is working for the GET and POST both.
     *
     * @param json It take the jsonString and assign to each variable of this class.
     */

    public void fromJSON(String json);
}