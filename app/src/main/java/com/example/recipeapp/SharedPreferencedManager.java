package com.example.recipeapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencedManager {

    public static SharedPreferencedManager instance;
    public static Context mCtx;

    private static final String SHARED_PREF_NANE = "Shared1";
    private static final String USER_ID_KEY = "id";
    private static final String USER_NAME_KEY = "username";
    private static final String USER_EMAIL_KEY = "email";
    private static final String USER_PROFILE_IMG_KEY = "image_dir";


    public SharedPreferencedManager(Context context)
    {
        mCtx = context;
    }

    public static synchronized SharedPreferencedManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencedManager(context);
        }
        return instance;
    }

    public void user_login(int id, String username, String email, String image_dir)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NANE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(USER_ID_KEY, id);
        editor.putString(USER_NAME_KEY, username);
        editor.putString(USER_EMAIL_KEY, email);
        editor.putString(USER_PROFILE_IMG_KEY, image_dir);

        editor.apply();


    }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NANE, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(USER_NAME_KEY, null) != null)
            return true;
        return false;
    }

    public void userLogout()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NANE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getUserName()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NANE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME_KEY, null);
    }

    public String getUserProfileImg()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NANE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_PROFILE_IMG_KEY, null);
    }

}
