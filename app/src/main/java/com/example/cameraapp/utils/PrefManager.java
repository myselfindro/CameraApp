package com.example.cameraapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class PrefManager {
    // Shared preferences file name
    private static final String PREF_NAME = "Infoscreen";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void deletedata() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }

}