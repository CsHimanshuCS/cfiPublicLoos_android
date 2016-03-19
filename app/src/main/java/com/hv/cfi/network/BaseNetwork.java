package com.hv.cfi.network;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.hv.cfi.R;
import com.hv.cfi.constants.Constant;
import com.hv.cfi.network.Interface.NetworkResponse;
import com.hv.cfi.network.dto.Request;
import com.hv.cfi.network.dto.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Himanshu Kumar singh
 */
public class BaseNetwork implements LoaderManager.LoaderCallbacks<Response> {

    private Request request;
    private NetworkResponse networkResponse;
    private Activity activity;
    /*
    * Networking releated work end
    *
    */
    private ProgressDialog pd;

    public BaseNetwork(Activity activity) {
        this.activity = activity;
    }

    /*
    * Networking releated work start
    *
    */

    public BaseNetwork(Activity activity, NetworkResponse networkResponse) {
        this.activity = activity;
    }

    public final void requestToServer(Request request) {
        requestToServer(request, true, false);
    }

    public final void requestToServer(Request request, boolean isProgressDialogShow) {
        requestToServer(request, isProgressDialogShow, false);
    }

    public final void requestToServer(Request request, boolean isProgressDialogShow, boolean isReset) {
        if (Constant.DEBUG) {
            if (!isValidRequest(request))
                return;
        }
        /**
         * Checking the network Here. That network connection is available or not.
         */
        if (!hasConnectivity(activity)) {
            showMsg(activity.getString(R.string.message_network_connection_error));
            return;
        }


        this.request = request;

        Bundle bundle = new Bundle();
        bundle.putBoolean("isDialogShow", isProgressDialogShow);
        if (isReset)
            activity.getLoaderManager().restartLoader(request.getRequestID(), bundle, this);
        else
            activity.getLoaderManager().initLoader(request.getRequestID(), bundle, this);
    }


    public void onResponseFromServer(Response response) {
        boolean isError = false;
        String errorData = "";
        int statusCode = -1;
        try {
            JSONObject jsonObject = new JSONObject(response.getStrResponse());
//            statusCode = jsonObject.optInt("status", -1);
            //Status Code 203 is not used yet. and 204 is used at the login time when the user is not registered at the mewithyou web.
            //203 is custom error so have to decided for every response and check in response if responseStatusCode==203 then show custom message otherwise success message.
//            if (!((statusCode == 200) || (statusCode == 201) || (statusCode == 203))) {
//                isError = true;
//                errorData = jsonObject.optString("data", "");
//            }
        } catch (JSONException e) {
        }

        response.setStatusCode(statusCode);
        if (isError) {
            response.setError(errorData);
        }

        networkResponse.onResponseFromServer(response);
    }

    @Override
    public Loader<Response> onCreateLoader(int i, Bundle bundle) {
        boolean isDialogShow = bundle.getBoolean("isDialogShow", true);
        if (isDialogShow) {
            showDialog(activity);
        }
        return new CustomLoader(activity, request);
    }

    @Override
    public void onLoadFinished(Loader<Response> responseLoader, Response response) {
        dismissDialog();
        /*
        * Checking the response status code
        * for Ok stats, It should be 200;
        */
//        boolean isError=false;
//        String data= "";
//
//        int status=0;
//        try {
//            JSONObject jsonObject = new JSONObject(response.getStrResponse());
//            status = jsonObject.getInt("status");
//            data = jsonObject.optString("data","");
//            if(status!=200)
//                isError=true;
//        } catch (JSONException e) {
//            isError = true;
//            e.printStackTrace();
//        }
////        JSONObject jData = jsonObject.getJSONObject("data");
//
//        if(isError)
//            response.setError("Api Status code : " + status + '\n' +" , Data = " + data);
//
        onResponseFromServer(response);
    }

    @Override
    public void onLoaderReset(Loader<Response> responseLoader) {
        dismissDialog();
    }

    boolean isValidRequest(Request request) {
        if (request == null) {
            showMsg("Request Object must not be null");
            return false;
        } else if (request.getRequestID() <= 0) {
            showMsg("Request ID must be greater than Zero");
            return false;
        } else if (request.getRequestURL() == null) {
            showMsg("Request URL must not be null");
            return false;
        } else if (request.getRequestType() == null) {
            showMsg("Request Type must not be null. It should be either GET or POST");
            return false;
        } else if (!((request.getRequestType().equalsIgnoreCase("get"))
                || (request.getRequestType().equalsIgnoreCase("post")))) {
            showMsg("Request Type should be either GET or POST");
            return false;
        }

        if ((request.getRequestType().equalsIgnoreCase("post"))) {
            if ((request.getStrRequest() == null) && (request.getJsonRequest() == null)) {
                showMsg("For the Post Request Type, Please Either use setStrRequest() or setJsonRequest()");
                return false;
            }
        }
        return true;

    }

//    void showDialog(Context context) {
//        if (context == null)
//            return;
//        pd = new ProgressDialog(context);
//        pd.setTitle("Processing...");
//        pd.setMessage("Please wait.");
//        pd.setCancelable(false);
//        pd.setIndeterminate(true);
//        pd.show();
//    }

    public void showDialog(Context context) {
        showDialog(context, false);
    }

    public void showDialog(Context context, boolean isCancelable) {
        int progressSpeed = 20;
        try {
            if (pd == null) {
                //TODO : Dialog show code will be here
                pd = new ProgressDialog(activity);
                pd.setMessage("loading");
//                pd = new ProgressDialog(activity);
//                pd.setIndeterminate(false);
//                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                pd=new Dialog();
//                pd.setCanceledOnTouchOutside(false);
//                pd.setCancelable(isCancelable);
            }
            pd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dismissDialog() {
        if (pd != null) {
            pd.dismiss();
        }
    }

    public NetworkResponse getNetworkResponse() {
        return networkResponse;
    }

    public void setNetworkResponse(NetworkResponse networkResponse) {
        this.networkResponse = networkResponse;
    }

    public void showMsg(String msg) {
        Toast.makeText(activity, "" + msg, Toast.LENGTH_SHORT).show();
    }

    public boolean hasConnectivity(Context context) {
        boolean rc = false;
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                    && cm.getActiveNetworkInfo().isConnected()) {
                rc = true;
            }
        }
        return rc;
    }

}