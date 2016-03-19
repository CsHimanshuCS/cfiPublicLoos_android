package com.hv.cfi.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.hv.cfi.R;
import com.hv.cfi.baseutilities.BaseActivity;
import com.hv.cfi.constants.Logger;
import com.hv.cfi.dto.User;
import com.hv.cfi.network.dto.Request;
import com.hv.cfi.network.dto.Response;
import com.hv.cfi.utilities.AppUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AppUtility.showToast(view,"toast");
                sendRequest();
            }
        });
    }

    private void sendRequest(){
        Request request = new Request();
        request.setRequestURL("http://192.168.0.108/adminTest/api/webservices/login.php?operation=fetchall");
        request.setRequestID(5);
        request.setRequestType(Request.RequestType.POST);
        request.setJsonRequest(new JSONObject());
        requestToServer(request, true, true);
    }

    @Override
    public void onResponseFromServer(Response response) {
        super.onResponseFromServer(response);
        if(AppUtility.hasErrorInResponse(response))
            return;
        switch (response.getResponseID()){
            case 5:
                String s = response.getStrResponse();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    ArrayList<User> arrayListUser = new ArrayList<User>();
                    if(jsonArray!=null){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            User user = new User();
                            user.fromJSON(jsonObject1.toString());
                            arrayListUser.add(user);
                        }
                    }
                    Logger.d("Respnose : " , arrayListUser.toString());

                    Logger.d("Respnose Size : " ," "+ arrayListUser.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
