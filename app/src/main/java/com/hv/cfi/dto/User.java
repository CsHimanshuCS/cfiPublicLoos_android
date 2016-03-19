package com.hv.cfi.dto;

import com.hv.cfi.constants.Constant;
import com.hv.cfi.network.Interface.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Himanshu Kumar Singh
 * Email : Himanshu.singh@tothenew.com
 * Contact. No : 9560962031
 * Skype id : cs_himanshu
 */
public class User implements BaseModel{
    private int id=0;
    private String username="";
    private String password="";
    private String type="";


    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("username",username);
            jsonObject.put("password",password);
            jsonObject.put("type",type);
        } catch (JSONException e) {
            if(Constant.DEBUG)
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJSON(String json) {
        try{
            JSONObject jMain = new JSONObject(json);
            id=jMain.optInt("id");
            username=jMain.optString("username", "");
            password=jMain.optString("password", "");
            type=jMain.optString("type","");
        }catch (Exception e){
            if(Constant.DEBUG)
                e.printStackTrace();
        }
    }
}
