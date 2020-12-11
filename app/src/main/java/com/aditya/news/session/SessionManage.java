package com.aditya.news.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManage {
    public static final String PREF_NAME = "SAVED_NEWS";
    public static final String LOGIN = "IS_LOGIN";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_EMAIL = "USER_Email";
    public SharedPreferences.Editor editor;
    public Context context;
    SharedPreferences sharedPreferences;
    int PRIVATE_MODE = 0;


    public SessionManage(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createsession(String userid, String username, String email) {
        editor.putString(USER_ID, userid);
        editor.putString(USER_NAME, username);
        editor.putString(USER_EMAIL, email);
        editor.apply();

    }

    public boolean islogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public String isuser() {
        return sharedPreferences.getString(USER_ID, "");
    }

    public String isusername() {
        return sharedPreferences.getString(USER_NAME, "");
    }

    public String isemail() {
        return sharedPreferences.getString(USER_EMAIL, "");
    }

//    public HashMap<String, String> getuser(){
//        HashMap<String, String> user=new HashMap<String, String>();
//        user.put(USER_ID, sharedPreferences.getString(USER_ID,null));
//        user.put(USER_NAME, sharedPreferences.getString(USER_NAME,null));
//        return user;
//    }
}
