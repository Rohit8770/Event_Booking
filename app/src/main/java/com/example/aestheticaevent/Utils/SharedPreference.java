package com.example.aestheticaevent.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.aestheticaevent.ActivityFragments.FirsttActivity;
import com.example.aestheticaevent.User.Activity_SignIn;

public class SharedPreference {


    private static  final String SHARED_PREF_NAME = "MyAppPrefs";
    private static  final String KEY_IS_LOGGED_IN = "isLoggedin";
    private static final String ONBOARDING_COMPLETED = "onboarding_completed";

    private static final String KEY_USER_ID = "userId";



    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;
    private Context context;

    public SharedPreference(Context context) {

        this.context = context;
        sharedPreference = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }
    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    public Boolean getLoggedIn() {
        return sharedPreference.getBoolean(KEY_IS_LOGGED_IN,false);
    }


    public boolean isLoggedIn(){
        return  sharedPreference.getBoolean(KEY_IS_LOGGED_IN, false);
    }


    public void setStringvalue(String key,String value){
        editor.putString(key, value);
        editor.apply();
    }

    public  String getStringvalue(String key){
        return  sharedPreference.getString(key,"");
    }


    public void setUserId(String userId) {
        editor.putString(KEY_USER_ID, userId).apply();
    }


    public void setOnboardingCompleted(Activity_SignIn activitySignIn, boolean completed) {
        editor.putBoolean(ONBOARDING_COMPLETED, completed);
        editor.apply();
    }

    public boolean isOnboardingCompleted(FirsttActivity firsttActivity) {
        return sharedPreference.getBoolean(ONBOARDING_COMPLETED, false);
    }

}
