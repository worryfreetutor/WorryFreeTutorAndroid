package com.example.kaixuan.worryfreetutor.base;

import android.content.Context;
import android.content.SharedPreferences;

public class GetHeader {
    //Bearer {{access_token}}
    public static String getAccessRefresh(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("login_token",context.MODE_PRIVATE);
        return "Bearer"+" "+sharedPreferences.getString("access_token"," ");
    }
}
