package com.me.movies;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Home on 11/22/2016.
 */

public class SharedPref  {
    public static final String PREFS_NAME1 = "moviesgragment";
    public static final String PREFS_NAME2= "nowplaying";
    public static final String PREFS_NAME3 = "upcoming";
    public static final String PREFS_NAME4= "popular";
    public static final String  PREFS_KEY ="sign-in";

    public SharedPref() {
        super();
    }

    public void save(Context context, boolean text,String PREFS_NAME) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putBoolean(PREFS_KEY, text); //3

        editor.commit(); //4
    }

    public boolean getValue(Context context,String PREFS_NAME) {
        SharedPreferences settings;
        boolean text;
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        boolean signin=false;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if(settings.getBoolean(PREFS_KEY,true)){
            signin=true;
        };
        return signin;
    }
}
