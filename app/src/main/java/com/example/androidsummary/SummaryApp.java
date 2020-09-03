package com.example.androidsummary;

import android.app.Application;


public class SummaryApp extends Application {
    private static SummaryApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initHXSDK();
    }

    private void initHXSDK() {
        //EMClient.getInstance().setDebugMode(true);
    }

    public static SummaryApp getInstance() {
        return instance;
    }
}
