package com.hv.cfi.baseutilities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hv.cfi.network.BaseNetwork;
import com.hv.cfi.network.Interface.NetworkResponse;
import com.hv.cfi.network.dto.Request;
import com.hv.cfi.network.dto.Response;


/**
 * Created by Himanshu Kumar Singh
 * Email : Himanshu.singh@tothenew.com
 * Contact. No : 9560962031
 * Skype id : cs_himanshu
 */
public class BaseActivity extends AppCompatActivity{
    private BaseNetwork baseNetwork = null;
//    private BaseFragment baseFragmentVisibile=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseNetwork = new BaseNetwork(this);
        baseNetwork.setNetworkResponse(new NetworkResponse() {
            @Override
            public void onResponseFromServer(Response response) {
                BaseActivity.this.onResponseFromServer(response);
            }
        });
    }

    public void destroyLoader(int id) {
        getLoaderManager().destroyLoader(id);
    }

    public final void requestToServer(Request request) {
        /**
         * By default, Dilaog will be display on the Screen.
         */
        baseNetwork.requestToServer(request);
    }

/*
    public void sendDataFromActivityToFragment(BaseFragment baseFragment, View view, Object
            sendDataToActivity, int
            id) {
        if(getBaseFragmentVisibile()!=null){
            getBaseFragmentVisibile().onResponseFromActivity(baseFragment,view,
                    sendDataToActivity,id);
        }
    }*/


    public final void requestToServer(Request request, boolean isToShowDialog) {
        baseNetwork.requestToServer(request, isToShowDialog);
    }

    public final void requestToServer(Request request, boolean isToShowDialog, boolean isResetLoader) {
        baseNetwork.requestToServer(request, isToShowDialog, isResetLoader);
    }

    public void onResponseFromServer(Response response) {
    }


  /*
    public BaseNetwork getBaseNetwork() {
        return baseNetwork;
    }

  @Override
    public void onResponseFromFragment(BaseFragment baseFragment, View view, Object sendDataToActivity, int id) {

    }

    public void setBaseNetwork(BaseNetwork baseNetwork) {
        this.baseNetwork = baseNetwork;
    }

    public BaseFragment getBaseFragmentVisibile() {
        return baseFragmentVisibile;
    }

    public void setBaseFragmentVisibile(BaseFragment baseFragmentVisibile) {
        this.baseFragmentVisibile = baseFragmentVisibile;
    }
    */
}
