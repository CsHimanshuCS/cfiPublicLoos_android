package com.hv.cfi.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;


import com.hv.cfi.R;
import com.hv.cfi.constants.Constant;
import com.hv.cfi.constants.Logger;
import com.hv.cfi.network.dto.Request;
import com.hv.cfi.network.dto.Response;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Himanshu Kumar singh
 */

public class Utility {

    final int timeoutConnection = 2*60 * 1000; //in Miliseconds
    final int timeoutSocket = 2*60 * 1000;
    private Context context;

    public Utility(Context context) {
        this.context = context;
    }

    public static boolean isNetworkAvailable(Context context) {
        //It doesn't throw any exception here. We are just apply the try catch to be a safe side in case of application context.
        try {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
                    || connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTING) {
                return true;
            } else if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                    || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTING) {
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    private Response doGetPost(Request mRequest, String requestUrl, boolean isPost, JSONObject
            jsonObjectPostData) {
        Response mResponse = new Response(mRequest.getRequestID());
        mResponse.setObject(mRequest.getObject());

        if (!isNetworkAvailable(context)) {
            String error = context.getResources().getString(R.string.message_network_not_available);
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
            mResponse.setError(error);
            return mResponse;
        }

        HttpURLConnection httpURLConnection = null;
        try {
            requestUrl = encodeURL(requestUrl);
            URL url = new URL(requestUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(timeoutSocket);
            httpURLConnection.setConnectTimeout(timeoutConnection);
//            String userCredentials = "username:password";
//            String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes
//                    (),Base64.DEFAULT));
//            httpURLConnection.setRequestProperty ("Authorization", basicAuth);
//            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            httpURLConnection.setRequestProperty("Content-Type", "application/xhtml+xml");
//            httpURLConnection.setRequestProperty("Content-Type", "application/json");
//            httpURLConnection.setRequestProperty("charset", "utf-8");
//            httpURLConnection.setRequestProperty("Accept", "application/json");
//            httpURLConnection.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));
//            httpURLConnection.setRequestProperty("Content-Language", "en-US");
//            httpURLConnection.setRequestProperty("Content-Type", "application/text;charset=utf-8");
//            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:40.0) Gecko/20100101 Firefox/40.0");
//            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

//            httpURLConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//            httpURLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
//            httpURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//            httpURLConnection.setRequestProperty("Connection", "keep-alive");
//            httpURLConnection.setRequestProperty("Cookie", "_cfduid=d24ad4ff578f9e92bdffc2017f15412ff1443864151");
//            httpURLConnection.setRequestProperty("", "");
//            httpURLConnection.setRequestProperty("", "");
//            httpURLConnection.setRequestProperty("", "");


            httpURLConnection.setUseCaches(false);
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setDoInput(true);

            if (isPost) {
                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoInput(true);
//                printRequest("post", requestUrl, "--");
            } else {
//                httpURLConnection.setDoInput(false);
                httpURLConnection.setRequestMethod("GET");
//                if (Constant.DEBUG)
//                    printRequest("get", requestUrl, "--");

            }

            OutputStream os = null;
            if (isPost) {

                //open
                httpURLConnection.connect();

                //setup send
                OutputStream outputStream = httpURLConnection.getOutputStream();
                String postDataUrl = "";
                postDataUrl = jsonObjectPostData.toString();

                os = new BufferedOutputStream(outputStream);
                os.write(postDataUrl.getBytes());
                //clean up

//                outputStream.flush();
//                outputStream.close();
                os.close();

                printRequest("post", requestUrl, postDataUrl);
            } else {
                printRequest("get", requestUrl, "--");
            }

            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK){
//            if(true){
                //do somehting with response
                InputStream inputStream = httpURLConnection.getInputStream();
                String stringResponse = readStream(inputStream);

                mResponse.setStrResponse(stringResponse);
            } else {
                mResponse.setError("Error : Response status code is not Ok. Response status code " +
                        ": " + responseCode);
                String errormessage = readStream(httpURLConnection.getErrorStream());
                Log.e("test","Error 1: " +   httpURLConnection.toString());
                Log.e("test","Error :2 " + errormessage);
            }
        } catch (Exception e) {
            mResponse.setError("Error : " + e.toString());

        } finally {
//            httpURLConnection.disconnect();
        }
        if (Constant.DEBUG)
            printResponse(mResponse.getStrResponse());


        //Parse the object here
        if (mRequest.getBaseModel() != null) {
            mRequest.getBaseModel().fromJSON(mResponse.getStrResponse());
            mResponse.setBaseModel(mRequest.getBaseModel());
        }

        //parse the object here
        return mResponse;
    }


    public Response doGet(Request mRequest, String url) {
        return doGetPost(mRequest, url, false, null);
    }

    public Response doPost(Request mRequest, String url, JSONObject jsonObject) {
        return doGetPost(mRequest, url, true, jsonObject);
    }


    //    public Response doPost(int requestID,String url,String str)
//    {
//        /**
//         * For the future use
//         */
//        return null;
//    }

    void writeStream(OutputStream outputStream, String postData) throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(outputStream, "UTF-8"));
        writer.write(postData);
        writer.flush();
        writer.close();
    }

    /*
    *
    * Below Method is used for the utility of the get and post method.
     */
    String readStream(InputStream inputStream) throws IOException {
//        InputStream in = new FileInputStream(new File("C:/temp/test.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        String readString = out.toString();
        reader.close();
        inputStream.close();
        return readString;
    }

    public String encodeURL(String url) {
        return url.replaceAll(" ", "%20");
    }

    public void printRequest(String type, String url, String request) {
        Logger.i("Utility.java", "Api Hit type : " + type);
        Logger.i("Utility.java", "Api Hit url : " + url);
        Logger.i("Utility.java", "Api Hit request : " + request);
    }

    public void printResponse(String response) {
        Logger.i("Utility.java", "Api Hit Response : " + response);
    }
}
