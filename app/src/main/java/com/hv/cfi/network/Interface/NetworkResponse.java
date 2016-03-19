package com.hv.cfi.network.Interface;


import com.hv.cfi.network.dto.Response;

/**
 * Created by Himanshu Kumar singh
 *
 */
public interface NetworkResponse {
    public void onResponseFromServer(Response response);
}
