package com.hv.cfi.utilities;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.hv.cfi.network.dto.Response;

/**
 * Created by Himanshu Kumar Singh on 18/Mar/2016
 * Email : Himanshu.singh@tothenew.com
 * Contact. No : 9560962031
 * Skype id : cs_himanshu
 */
public class AppUtility {

    public static void showToast(View view,String message){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public static boolean hasErrorInResponse(Response response){
        if(response.isError()){
            //show error dialog
            return true;
        }else{
            return false;
        }
    }
}
